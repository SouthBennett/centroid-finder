package io.github.SouthBennett.centroidFinder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Collections;

import javax.imageio.ImageIO;

import java.io.FileWriter;

public class ImageProcessor {

  /**
   * Processes a video frame image and writes the centroid of the largest
   * detected color-matching group to a CSV file.
   *
   * @param videoImage      frame image to analyze
   * @param hexTargetColor  target color in hexadecimal format
   * @param outputCsv       path to the CSV file to append results
   * @param threshold       maximum allowed color distance for pixel matching
   * @param timestampSeconds frame timestamp in seconds
   */
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

  /**
   * Parses the provided hexadecimal color string into an integer RGB value.
   *
   * @param hexTargetColor target color string, such as "ff0000"
   * @return integer representation of the target color
   * @throws IllegalArgumentException if the string is not valid hex
   */
  public int parseTargetColor(String hexTargetColor) {
    try {
      return Integer.parseInt(hexTargetColor, 16);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid hex target color.");
    }
  }

  /**
   * Creates an {@link ImageBinarizer} configured with the specified target
   * color and threshold.
   *
   * @param distanceFinder color distance strategy
   * @param targetColor    target RGB color as an integer
   * @param threshold      matching threshold
   * @return configured {@link ImageBinarizer}
   */
  public ImageBinarizer createBinarizer(ColorDistanceFinder distanceFinder, int targetColor, int threshold) {
    return new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
  }

  /**
   * Converts the input image into a binary array using the provided binarizer.
   *
   * @param binarizer  image binarization strategy
   * @param videoImage source image to convert
   * @return binary mask array
   */
  public int[][] createBinaryArray(ImageBinarizer binarizer, BufferedImage videoImage) {
    return binarizer.toBinaryArray(videoImage);
  }

  /**
   * Converts a binary array back into a {@link BufferedImage}.
   *
   * @param binarizer  image binarization strategy
   * @param binaryArray binary mask array
   * @return binary mask image
   */
  public BufferedImage createBinaryImage(ImageBinarizer binarizer, int[][] binaryArray) {
    return binarizer.toBufferedImage(binaryArray);
  }

  /**
   * Saves the binarized image to a file named {@code binarized.png}.
   *
   * @param binaryImage image to save
   */
  public void saveBinaryImage(BufferedImage binaryImage) {
    try {
      ImageIO.write(binaryImage, "png", new File("binarized.png"));
      System.out.println("Binarized image saved.");
    } catch (Exception e) {
      System.err.println("Error saving image.");
      e.printStackTrace();
    }
  }

  /**
   * Finds connected groups of matching pixels in the source image.
   *
   * @param binarizer  binarization strategy used to identify matching pixels
   * @param videoImage source image containing the frame
   * @return list of detected groups
   */
  public List<Group> findGroups(ImageBinarizer binarizer, BufferedImage videoImage) {
    ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
    return groupFinder.findConnectedGroups(videoImage);
  }

  /**
   * Appends the centroid of the largest detected group to the CSV file.
   *
   * @param largestGroup    group whose centroid is written
   * @param outputCsv       output CSV file path
   * @param timestampSeconds timestamp in seconds for the current frame
   */
  public void writeLargestGroupToCsv(Group largestGroup, String outputCsv, double timestampSeconds) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(outputCsv, true))) {
      writer.println("Time Stamp: " + timestampSeconds + "," + " Centroid " + " X: " + largestGroup.centroid().x() + "," + " Y: " + largestGroup.centroid().y());
    } catch (Exception e) {
      throw new IllegalArgumentException("Error writing CSV.");
    }
  }

  /**
   * Writes a placeholder row indicating no centroid was found.
   *
   * @param outputCsv       output CSV file path
   * @param timestampSeconds timestamp in seconds for the current frame
   */
  public void writeNoCentroidRow(String outputCsv, double timestampSeconds) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(outputCsv, true))) {
      writer.println(timestampSeconds + ",-1,-1");
    } catch (Exception e) {
      System.err.println("Error writing CSV.");
      e.printStackTrace();
    }
  }
}
