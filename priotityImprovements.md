# Top Two Improvements

1. Writing Documentation
  - Per-folder README: explain purpose of processor vs server, how they interact, data exchange format for centroids/results.
  - Update README.md file explaining to the user how to run the Centroid Finder program. And explaining the overall project. (author, tech stack, etc...)

2. Adding Tests
  - Add focused unit tests for edge cases: tiny images, single-pixel groups, touching groups, border wrapping.
  - Add integration tests that run a short real video sample through VideoProcessor and validate produced centroids/results files. 