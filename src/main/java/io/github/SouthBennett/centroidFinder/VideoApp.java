package io.github.SouthBennett.centroidFinder;

public class VideoApp {
  public static void main(String[] args) throws Exception{
    VideoProcessor vp = new VideoProcessor();
    vp.processVideo("sampleVideo/ensantina.mp4", "115938", 115);
  }
}
