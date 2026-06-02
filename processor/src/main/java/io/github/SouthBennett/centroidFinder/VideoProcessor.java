package io.github.SouthBennett.centroidFinder;

/**
 * Entry point for the video processing application.
 *
 * <p>Parses command line arguments and delegates processing to
 * {@link VideoFrameProcessor}.</p>
 */
public class VideoProcessor {

  /**
   * Main entry method.
   *
   * <p>Expects an input video path, output CSV path, target color, and
   * threshold. If the threshold cannot be parsed as an integer, an error
   * message is printed and processing is aborted.</p>
   *
   * @param args command line arguments
   *             args[0] = input video path
   *             args[1] = output CSV file path
   *             args[2] = target color in hex form
   *             args[3] = integer threshold
   * @throws Exception if frame processing encounters an error
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 4) {
      System.out.println("Usage: java VideoProcessor <input_video> <output_csv> <hex_target_color> <threshold>");
      return;
    }

    String inputPath = args[0];
    String outputCsv = args[1];
    String targetColor = args[2];
    int threshold = 0;

    try {
      threshold = Integer.parseInt(args[3]);
    } catch (NumberFormatException e) {
      System.err.println("Threshold must be an integer.");
      return;
    }

    VideoFrameProcessor vp = new VideoFrameProcessor();
    vp.processVideo(inputPath, outputCsv, targetColor, threshold);
  }
}
