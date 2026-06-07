# Centroid Finder

---

## Project Overview

Centroid Finder is a video processing application that allows you to analyze salamander movement over time. The program processes salamander videos by extracting frames and pinpointing the centroid (center point) of the salamander in each frame. The timestamp and centroid for each frame that is processed are saved and written to a CSV file, allowing further analysis of salamander movement through patterns in the data. The generated CSV output can also be used for visualization, and tracking salamander movement by comparing centroid positions across timestamps throughout the video.

The video processor is based in Java while the server was created using Node.js/Express. The server contains API endpoints that allow the user to view all available videos, generate video thumbnails, start video processing jobs and track/monitor the status of those jobs. All of the video processing is done asynchronously, which allows large videos to be analyzed and processed without blocking the server.

---

## Features

- List Available Videos
- Generate Thumbnails
- Start processing jobs
- Track Job Status
- Generate CSV output

---

## Tech Stack

- Java
- Node.js
- Express
- FFmpeg
- Jest
- Supertest

## Installation 

1. Clone the repository
2. Install Node dependencies (npm i)
3. Install Java dependencies/build the processor (mvn compile)
4. Run tests (mvn test)
5. Create .env file
6. Configure the JAR path
7. Create any required folders needed. 
8. Start the server (cd server, npm run dev)

---

## Testing

- 

---

## Authors
 - Connor Hughes
 - Xavier Lewis