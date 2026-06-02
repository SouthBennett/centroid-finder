import express from "express";
import { getVideos, getThumbnail } from "../controllers/videoController.js";
import { startProcessingJob, getJobStatus } from "../controllers/jobController.js";

const router = express.Router();

router.get("/videos", getVideos);

router.get("/thumbnail/:filename", getThumbnail);

router.post("/process/:filename", startProcessingJob);

router.get("/process/:jobID/status", getJobStatus)

export default router;