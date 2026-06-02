# centroid-finder

## Project Overview

`centroid-finder` is a mixed Java / Node.js project for finding color-matching centroids in video frames and serving video processing requests from a web API.

- The `processor` module contains the Java video/image processing code.
- The `server` module contains a Node.js Express service that can invoke processing workflows and manage uploads/results.

## Author

- Project owner: `io.github.SouthBennett`
- Maintainers: repository contributors

## Tech Stack

- Java 21
- Maven
- JavaCV / FFmpeg
- JUnit Jupiter for Java tests
- Node.js (ESM)
- Express
- fluent-ffmpeg
- Jest / SuperTest for server tests

## Processor Module

### What it does

The Java processor reads a video file frame by frame, binarizes each frame based on a target color and threshold, finds connected pixel groups, computes centroids, and writes results to a CSV.

### How to run

From the `processor` directory:

1. Build the processor:
   - `mvn package`

2. Run the Java program:
   - `mvn exec:java -Dexec.mainClass="io.github.SouthBennett.centroidFinder.VideoProcessor" -Dexec.args="input.mp4 output.csv FF0000 100"`

3. Or run the generated jar with dependencies:
   - `java -jar target/centroidFinder-1.0-SNAPSHOT-jar-with-dependencies.jar input.mp4 output.csv FF0000 100`

### Notes

- `args[0]` = input video path
- `args[1]` = output CSV path
- `args[2]` = hex target color (e.g. `FF0000`)
- `args[3]` = integer threshold

## Server Module

### What it does

The Node.js server exposes endpoints to upload videos and trigger processing jobs. It uses safe path helpers and FFmpeg utilities to manage video files, thumbnails, and results.

### How to run

From the `server` directory:

1. Install dependencies:
   - `npm install`

2. Start the server:
   - `npm start`

3. Run in development mode:
   - `npm run dev`

## Testing

### Java tests

From the `processor` directory:
- `mvn test`

### Node.js tests

From the `server` directory:
- `npm test`

## Project Structure

- `processor/src/main/java/...` Java processing code
- `processor/src/test/java/...` Java unit tests
- `server/index.js` Express entry point
- `server/routes/` API route definitions
- `server/controllers/` request handling logic
- `server/utils/` helper utilities like path construction

---

This README now includes basic usage, project structure, and how to run both the Java processor and Node server.