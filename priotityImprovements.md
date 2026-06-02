# Top Two Improvements

1. Writing Documentation
  - Per-folder README: explain purpose of processor vs server, how they interact, data exchange format for centroids/results.
  - Update README.md file explaining to the user how to run the Centroid Finder program. And explaining the overall project. (author, tech stack, etc...)

2. Adding Tests
  - Add focused unit tests for edge cases: tiny images, single-pixel groups, touching groups, border wrapping.
  - Add integration tests that run a short real video sample through VideoProcessor and validate produced centroids/results files. 


3. Refactoring code
  - Move repeated path-building logic into helper functions like getVideoPath(filename) and getResultPath(filename).
  - Split the Express controller into smaller files: videoController, jobController, and maybe pathUtils.

4. Improving error handling 
  - Capture/log Java JAR errors instead of hiding them with stdio: "ignore" during development.
  - Validate inputs better: check missing targetColor, invalid hex format, invalid threshold, missing video file, and missing JAR file before spawning Java.

