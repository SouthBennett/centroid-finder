- Refactoring & readability
  - Remove duplicated code across different GroupFinder implementations; centralize common traversal/neighbor logic.
  - Improve naming (make intent explicit), keep classes small (SRP).
  - Add Javadoc for public classes and methods; document units (pixels, coordinates).

- Documentation & developer ergonomics
  - Per-folder README: explain purpose of processor vs server, how they interact, data exchange format for centroids/results.
  - Add example input/output images/videos under sampleVideo and a small script to run one-shot processing.
  - Document expected JVM flags and Node versions.

- Memory & resource management
  - Ensure proper release of native resources (FrameGrabber/FFmpeg/Scope) with try-with-resources or explicit close/dispose. Watch for native memory leaks in tests/target.
  - Reuse buffers between frames instead of allocating new ones each iteration.

- Tests
  - Add focused unit tests for edge cases: tiny images, single-pixel groups, touching groups, border wrapping.
  - Add property-based or fuzz tests for binarizer and group-finder to catch invariants.
  - Add integration tests that run a short real video sample through VideoProcessor and validate produced centroids/results files.
  - Mock or isolate JNI/native components to make unit tests deterministic.