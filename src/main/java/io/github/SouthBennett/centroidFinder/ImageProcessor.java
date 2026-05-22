package io.github.SouthBennett.centroidFinder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageProcessor {

  public void processImage(

  BufferedImage videoImage, String hexTargetColor, int threshold) {

  int targetColor = parseTargetColor(hexTargetColor);

  ColorDistanceFinder distanceFinder = new EuclideanColorDistance();

  ImageBinarizer binarizer = createBinarizer(distanceFinder, targetColor, threshold);

  int[][] binaryArray = createBinaryArray(binarizer, videoImage);

  BufferedImage binaryImage = createBinaryImage(binarizer, binaryArray);

  saveBinaryImage(binaryImage);

  List<Group> groups = findGroups(binarizer, videoImage);

  writeGroupsToCsv(groups);
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

public void writeGroupsToCsv(List<Group> groups) {
  try (PrintWriter writer = new PrintWriter("groups.csv")) {
    for (Group group : groups) {
      writer.println(group.toCsvRow());
    }
      System.out.println("Groups summary saved.");
    } catch (Exception e) {
      System.err.println("Error writing CSV.");
      e.printStackTrace();
    }
  }
}
