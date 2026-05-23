package io.github.SouthBennett.centroidFinder;

public class VideoProcessor {
  public static void main(String[] args) throws Exception{
    if (args.length < 3) {
      System.out.println("Usage: java ImageSummaryApp <input_image> <hex_target_color> <threshold>");
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
