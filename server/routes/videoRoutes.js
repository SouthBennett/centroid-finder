import express from "express";
import fs from "fs";

const router = express.Router();

router.get("/videos", (req, res) => {
  // res.send("Video route works!")
  try {
    const videos = fs.readdirSync("./videos");
    res.status(200).json(videos);
  } catch (error) {
    console.error(error);
    res.status(500).json({
      error: "Error reading video directory"
    });
  }
});

export default router;