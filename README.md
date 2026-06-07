# Centroid Finder

---

## Project Overview

Centroid Finder is a video processing application that allows you to analyze salamander movement over time. The program processes salamander videos by extracting frames and pinpointing the centroid (center point) of the salamander in each frame. The timestamp and centroid for each frame that is processed are saved and written to a CSV file, allowing further analysis of salamander movement through patterns in the data. The generated CSV output can also be used for visualization, and tracking salamander movement by comparing centroid positions across timestamps throughout the video.

The video processor is based in Java while the server was created using Node.js/Express. The server contains API endpoints that allow the user to view all available videos, generate video thumbnails, start video processing jobs and track/monitor the status of those jobs. All of the video processing is done asynchronously, which allows large videos to be analyzed and processed without blocking the server.

---

## Authors
 - Connor Hughes
 - Xavier Lewis