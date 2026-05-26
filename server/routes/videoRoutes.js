import express from "express";
import { getVideos, getThumbnail, startProcessingJob, getJobStatus } from "../controllers/videoController.js";

const router = express.Router();

router.get("/videos", getVideos);

router.get("/thumbnail/:filename", getThumbnail);

router.post("/process/:filename", startProcessingJob);

router.get("/process/:jobID/status", getJobStatus)

export default router;