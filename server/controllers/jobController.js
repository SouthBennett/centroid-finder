/**
 * jobController.js
 *
 * Controls lifecycle for background video-processing jobs.
 *
 * - `startProcessingJob(req, res)` starts a detached Java process to analyze a video
 *   and writes results to a CSV. It returns 202 with a `jobID` and tracks the
 *   job state in-memory under `jobs[jobID]`.
 * - `getJobStatus(req, res)` returns the current status and result path when done.
 *
 * Notes:
 * - Jobs are stored only in memory (not persisted). If the server restarts,
 *   job tracking will be lost.
 * - The controller expects `process.env.JAR_PATH` to point to the Java JAR.
 */
import fs from "fs";
import { spawn } from "child_process";
import { v4 as uuidv4 } from "uuid";
import chalk from "chalk";

import { getVideoPath, getResultPath } from "../utils/pathUtils.js";

// In-memory job registry: { [jobID]: { status: string, result: string } }
const jobs = {};

export function startProcessingJob(req, res) {
  try {
    const filename = req.params.filename;
    const { targetColor, threshold } = req.query;

    if (!targetColor || !threshold) {
      return res.status(400).json({
        error: "Missing target color and a threshold to find the mander"
      });
    }

    // Validate targetColor is a 6-digit hex string (accepts #RRGGBB or RRGGBB or 0xRRGGBB)
    const hexPattern = /^(#|0x)?[0-9A-Fa-f]{6}$/;
    if (!hexPattern.test(String(targetColor))) {
      return res.status(400).json({ error: "Invalid targetColor hex format" });
    }

    // Validate threshold is a positive integer
    const thresholdNum = Number(threshold);
    if (!Number.isInteger(thresholdNum) || thresholdNum <= 0) {
      return res.status(400).json({ error: "Invalid threshold value" });
    }

    const videoPath = getVideoPath(filename);

    if (!fs.existsSync(videoPath)) {
      return res.status(404).json({
        error: "No Mander videos found"
      });
    }

    // Ensure the configured JAR exists before spawning the process
    if (!process.env.JAR_PATH || !fs.existsSync(process.env.JAR_PATH)) {
      console.log(chalk.redBright("Missing or invalid JAR_PATH:"), process.env.JAR_PATH);
      return res.status(500).json({ error: "JAR file not found" });
    }

    const jobID = uuidv4();
    const outputCSV = getResultPath(filename);

    jobs[jobID] = {
      status: "Processing looking for the mander now",
      result: getResultPath(filename)
    };

    const child = spawn(
      "java",
      [
        "-jar",
        process.env.JAR_PATH,
        videoPath,
        outputCSV,
        targetColor,
        threshold
      ],
      {
        detached: true,
        stdio: "ignore"
      }
    );

    child.unref();

    return res.status(202).json({ jobID });
  } catch (error) {
    console.log(chalk.redBright("There was a problem starting your job"));

    return res.status(500).json({
      error: "Error starting the job"
    });
  }
}

export function getJobStatus(req, res) {
  try {
    const jobID = req.params.jobID;
    const job = jobs[jobID];

    if (!job) {
      return res.status(404).json({
        error: "You have no Job!!"
      });
    }

    if (fs.existsSync(`.${job.result}`)) {
      job.status = "all done here";
    }

    if (job.status === "all done here") {
      return res.status(200).json({
        status: "done",
        result: job.result
      });
    }

    return res.status(200).json({
      status: "Processing"
    });
  } catch (error) {
    console.log(chalk.bgRedBright("you had one job..." + error));

    return res.status(500).json({
      error: "Error fetching job status"
    });
  }
}
