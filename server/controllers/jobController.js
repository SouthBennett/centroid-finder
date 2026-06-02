import fs from "fs";
import { spawn } from "child_process";
import { v4 as uuidv4 } from "uuid";
import chalk from "chalk";

import { getVideoPath, getResultPath } from "../utils/pathUtils.js";

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

    const videoPath = getVideoPath(filename);

    if (!fs.existsSync(videoPath)) {
      return res.status(404).json({
        error: "No Mander videos found"
      });
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
      error: "Eroor fetching job status"
    });
  }
}
