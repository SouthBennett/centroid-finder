import fs from "fs";
import ffmpeg from "fluent-ffmpeg";
import ffmpegPath from "ffmpeg-static";
import ffprobe from "ffprobe-static";

ffmpeg.setFfmpegPath(ffmpegPath);
ffmpeg.setFfprobePath(ffprobe.path);

export function getVideos(req, res) {
  try {
    const videos = fs.readdirSync("./videos");
    res.status(200).json(videos);
  } catch (error) {
    console.error(error);
    res.status(500).json({
      error: "Error reading video directory"
    });
  }
}

export function getThumbnail(req, res) {
  try {
    const filename = req.params.filename;

    const videoPath = `./videos/${filename}`;

    if (!fs.existsSync(videoPath)) {
      return res.status(404).json({
        error: "Video not found"
      });
    }
    // res.send(`Thumbnail route for ${filename} works`);
    // const thumbnailPath = `./thumbnails/${filename}.jpg`;

    ffmpeg(videoPath).screenshots({
      count: 1,
      folder: "./thumbnails",
      filename:`${filename}.jpg`,
      size: "320x240"
    })
    .on("end", () => {
      res.sendFile(process.cwd() + `/thumbnails/${filename}.jpg`);
    })
    .on("error", (error) => {
      console.error(error);
      res.status(500).json({
        error: "Failed to generate thumbnail"
      });
    });
  } catch (error) {
    console.error(error);
    
    res.status(500).json({
      error: "Internal Server error"
    })
  }
}