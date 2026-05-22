package io.github.SouthBennett.centroidFinder;

public class VideoApp {
  public static void main(String[] args) throws Exception{
    VideoProcessor videoImage = new VideoProcessor();
    ImageProcessor imagePro = new ImageProcessor();
    
    imagePro.processImage(videoImage.processVideo("sampleVideo/ensantina.mp4"), "000000", 115);
  }
}
