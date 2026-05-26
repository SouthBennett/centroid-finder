import express from "express";
import { getVideos, getThumbnail } from "../controllers/videoController.js";

const router = express.Router();

router.get("/videos", getVideos);

router.get("/thumbnail/:filename", getThumbnail);

export default router;