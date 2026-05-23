package io.github.SouthBennett.centroidFinder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Collections;

import javax.imageio.ImageIO;

import java.io.FileWriter;
import java.util.Collections;

public class ImageProcessor {

  public void processImage(BufferedImage videoImage, String hexTargetColor, String outputCsv, int threshold, double timestampSeconds) {

  int targetColor = parseTargetColor(hexTargetColor);

  ColorDistanceFinder distanceFinder = new EuclideanColorDistance();

  ImageBinarizer binarizer = createBinarizer(distanceFinder, targetColor, threshold);

  int[][] binaryArray = createBinaryArray(binarizer, videoImage);

  BufferedImage binaryImage = createBinaryImage(binarizer, binaryArray);

  saveBinaryImage(binaryImage);

  List<Group> groups = findGroups(binarizer, videoImage);

  Group largestGroup = Collections.max(groups);

  System.out.println(largestGroup.toCsvRow());

  writeLargestGroupToCsv(largestGroup, outputCsv, timestampSeconds);
}


public int parseTargetColor(String hexTargetColor) {

  try {
    return Integer.parseInt(hexTargetColor, 16);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid hex target color.");
    }
}


public ImageBinarizer createBinarizer(ColorDistanceFinder distanceFinder, int targetColor, int threshold) {
  return new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
}


public int[][] createBinaryArray(ImageBinarizer binarizer, BufferedImage videoImage) {
  return binarizer.toBinaryArray(videoImage);
}

/**
 * Converts binary grid back into image.
 */
public BufferedImage createBinaryImage(ImageBinarizer binarizer, int[][] binaryArray) {
  return binarizer.toBufferedImage(binaryArray);
}


public void saveBinaryImage(BufferedImage binaryImage) {
  try {
    ImageIO.write(binaryImage, "png", new File("binarized.png"));
    System.out.println("Binarized image saved.");
} catch (Exception e) {
  System.err.println("Error saving image.");
    e.printStackTrace();
  }
}


public List<Group> findGroups(ImageBinarizer binarizer,BufferedImage videoImage) {
  ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
  return groupFinder.findConnectedGroups(videoImage);
}


  public void writeLargestGroupToCsv( Group largestGroup, String outputCsv, double timestampSeconds) {
    try ( PrintWriter writer = new PrintWriter( new FileWriter( outputCsv,true))) {
        writer.println("Time Stamp: " + timestampSeconds + "," + " Centroid " + " X: " + largestGroup.centroid().x() + "," + " Y: " + largestGroup.centroid().y());
    } catch (Exception e) {
        throw new IllegalArgumentException("Error writing CSV.");
    }
  }

  public void writeNoCentroidRow(String outputCsv, double timestampSeconds) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(outputCsv, true))) {
      writer.println(timestampSeconds + ",-1,-1");
    } catch (Exception e) {
      System.err.println("Error writing CSV.");
      e.printStackTrace();
    }
  }
}
