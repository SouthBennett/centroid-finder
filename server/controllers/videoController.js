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
import { spawn } from "child_process";
import {v4 as uuidv4} from "uuid";
import chalk from "chalk";

const jobs = {};

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


export function startProcessingJob(req, res){
  try {

    // get your data
    const filename = req.params.filename;
    const {targetColor, threshold} = req.query;
    // console.log(filename);
    // console.log(targetColor);
    // console.log(threshold);

    if(!targetColor || !threshold){
      return res.status(400).json({
        error: "Missing target color and a threshold to find the mander"
      })
    }

    // builds a video path
    const videoPath = getVideoPath(filename);
 
    if(!fs.existsSync(videoPath)){
      return res.status(404).json({
        error: "No Mander videos found"
      })
    }


    // creates a job ID. this is how the job is tracked
    const jobID = uuidv4();
    //  console.log(jobID);

    // build path to write the centroid groups in a csv file
    const outputCSV = getResultPath(filename);
    // console.log(outputCSV);

    // stores the state of the job("record in memory"). puts a job in the jobs with an ID and says hey we are tracking you now 
    // think of it like a server memory database 
    jobs[jobID] = {
      status: "Processing looking for the mander now",
      result: getResultPath(filename)
    }
    
    // spawn your jar
    // detached means to run independently from express
    // stdio means dont pipe java logs into Node

    const child = spawn(
      "java", [
        "-jar", process.env.JAR_PATH,
        videoPath, outputCSV,
        targetColor, threshold
      ],{
        detached: true,
        stdio: "ignore"
      }
    )

    // don't wait for this to finish and keep going 
    child.unref();

    // since we don't wait for the video to be processed just return the status of the job if everything is good.
    return res.status(202).json({ jobID})

  } catch (error) {
    console.log(chalk.redBright("There was a problem starting your job"));

    return res.status(500).json({
      error: "Error starting the job"
    })
  }
}


// checks on the job status
export function getJobStatus(req,res) {
  try {
    // use the params to choose which job you want to check
    const jobID = req.params.jobID

    // get that jobs from your memory of the jobs
    const job = jobs[jobID]

    if(!job){
      return res.status(404).json({
        error: "You have no Job!!"
      })
    }

    // check if the csv file exists. did the jar finish writing the csv file?
    if(fs.existsSync(`.${job.result}`)) {
      job.status = "all done here"
    }

    // if it did then your are all good
    if(job.status === "all done here") {
      return res.status(200).json({
        status: "done",
        result: job.result
      })
    }

    return res.status(200).json({
      status: "Processing"
    })



  } catch (error) {
    console.log(chalk.bgRedBright("you had one job..." + error));

    return res.status(500).json({
      error : "Eroor fetching job status"
    })
  }
}