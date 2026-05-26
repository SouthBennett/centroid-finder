import fs from "fs";

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