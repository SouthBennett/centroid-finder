import fs from "fs";
import ffmpeg from "fluent-ffmpeg";
import ffmpegPath from "ffmpeg-static";
import ffprobe from "ffprobe-static";

import {
  getVideoPath,
  getResultPath,
  getThumbnailPath
} from "../utils/pathUtils.js";

ffmpeg.setFfmpegPath(ffmpegPath);
ffmpeg.setFfprobePath(ffprobe.path);


import path from "path";

export function getVideos(req, res) {
  try {
    // read the contents of the videos directory and store the list in videos 
    const videos = fs.readdirSync("./videos");
    // return a 200 status response and a list of videos in json format
    res.status(200).json(videos);
  } catch (error) {
    // log the error
    console.error(error);
    // return a 500 error status 
    res.status(500).json({
      error: "Error reading video directory"
    });
  }
}

export function getThumbnail(req, res) {
  try {
    // extract the video filename from the URL path parameter
    const filename = req.params.filename;
    // build the full path to the requested video file by combining the video directory and the file name parameter
    const videoPath = getVideoPath(filename);
    // check if the video file exists, if it doesn't return a 404 error
    if (!fs.existsSync(videoPath)) {
      return res.status(404).json({
        error: "Video not found"
      });
    }
    // res.send(`Thumbnail route for ${filename} works`);
    // const thumbnailPath = `./thumbnails/${filename}.jpg`;

    // ready the video file for processing using ffmpeg
    ffmpeg(videoPath).screenshots({
      // take one frame
      count: 1,
      // save screenshot to thumbnails folder
      folder: "./thumbnails",
      // save name of screenshot as the original file name
      filename:`${filename}.jpg`,
      // size the image
      size: "320x240"
    })
    // event listener (when ffmpeg finishes (or ends) do the next thing)
    .on("end", () => {
      // send the generated thumbnail image file back as the response.
      // the path is the current directory, plus the thumbail's location
      res.sendFile(process.cwd() + "/" + getThumbnailPath(filename));
    })
    // if screenshot generation fails, log error and send a 500 response
    .on("error", (error) => {
      console.error(error);
      res.status(500).json({
        error: "Failed to generate thumbnail"
      });
    });
    // catch any unexpected errors not handled earlier and send a 500 response
  } catch (error) {
    console.error(error);
    
    res.status(500).json({
      error: "Internal Server error"
    })
  }
}

// creates a new video procesing task
// runs when you call this : POST /process/ensantina.mp4?targetColor=115938&threshold=115

// Job-related routes moved to server/controllers/jobController.js