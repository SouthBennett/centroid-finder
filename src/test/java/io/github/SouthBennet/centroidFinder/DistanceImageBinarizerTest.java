package test.java.io.github.SouthBennet.centroidFinder;
import static org.junit.Assert.*;
import org.junit.Test;

import main.java.io.github.SouthBennet.centroidFinder.ColorDistanceFinder;
import main.java.io.github.SouthBennet.centroidFinder.DistanceImageBinarizer;
import main.java.io.github.SouthBennet.centroidFinder.EuclideanColorDistance;

import java.awt.image.BufferedImage;

public class DistanceImageBinarizerTest {

  private final ColorDistanceFinder distanceFinder = new EuclideanColorDistance();

  @Test
  public void testToBinaryArray_exactTargetColorBecomesWhite() {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    image.setRGB(0, 0, 0xFF0000); // red

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0xFF0000, 1);

    int[][] result = binarizer.toBinaryArray(image);

    assertEquals(1, result[0][0]);
  }

  @Test
  public void testToBinaryArray_differentColorBecomesBlack() {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    image.setRGB(0, 0, 0x0000FF); // blue

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0xFF0000, 10);

    int[][] result = binarizer.toBinaryArray(image);

    assertEquals(0, result[0][0]);
  }

  @Test
  public void testToBinaryArray_distanceLessThanThresholdBecomesWhite() {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    image.setRGB(0, 0, 0xFE0000); // almost red

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0xFF0000, 2);

    int[][] result = binarizer.toBinaryArray(image);

    assertEquals(1, result[0][0]);
  }

  @Test
  public void testToBinaryArray_distanceEqualToThresholdBecomesBlack() {
    BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    image.setRGB(0, 0, 0xFE0000); // distance from red is 1

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0xFF0000, 1);

    int[][] result = binarizer.toBinaryArray(image);

    assertEquals(0, result[0][0]);
  }

  @Test
  public void testToBinaryArray_multiplePixels() {
    BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

    image.setRGB(0, 0, 0xFF0000); // white
    image.setRGB(1, 0, 0xFE0000); // white if threshold 2
    image.setRGB(0, 1, 0x0000FF); // black
    image.setRGB(1, 1, 0x00FF00); // black

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0xFF0000, 2);

    int[][] result = binarizer.toBinaryArray(image);

    assertEquals(1, result[0][0]);
    assertEquals(1, result[0][1]);
    assertEquals(0, result[1][0]);
    assertEquals(0, result[1][1]);
  }

  @Test
  public void testToBinaryArray_dimensionsMatchImage() {
    BufferedImage image = new BufferedImage(3, 2, BufferedImage.TYPE_INT_RGB);

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0x000000, 10);

    int[][] result = binarizer.toBinaryArray(image);

    assertEquals(2, result.length);       // height
    assertEquals(3, result[0].length);    // width
  }

  @Test
  public void testToBufferedImage_singleBlackPixel() {
    int[][] binary = {
      {0}
    };

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0x000000, 10);

    BufferedImage result = binarizer.toBufferedImage(binary);

    assertEquals(0x000000, result.getRGB(0, 0) & 0xFFFFFF);
  }

  @Test
  public void testToBufferedImage_singleWhitePixel() {
    int[][] binary = {
      {1}
    };

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0x000000, 10);

    BufferedImage result = binarizer.toBufferedImage(binary);

    assertEquals(0xFFFFFF, result.getRGB(0, 0) & 0xFFFFFF);
  }

  @Test
  public void testToBufferedImage_multiplePixels() {
    int[][] binary = {
      {1, 0},
      {0, 1}
    };

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0x000000, 10);

    BufferedImage result = binarizer.toBufferedImage(binary);

    assertEquals(0xFFFFFF, result.getRGB(0, 0) & 0xFFFFFF);
    assertEquals(0x000000, result.getRGB(1, 0) & 0xFFFFFF);
    assertEquals(0x000000, result.getRGB(0, 1) & 0xFFFFFF);
    assertEquals(0xFFFFFF, result.getRGB(1, 1) & 0xFFFFFF);
  }

  @Test
  public void testToBufferedImage_dimensionsMatchArray() {
    int[][] binary = {
      {1, 0, 1},
      {0, 1, 0}
    };

    DistanceImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, 0x000000, 10);

    BufferedImage result = binarizer.toBufferedImage(binary);

    assertEquals(3, result.getWidth());
    assertEquals(2, result.getHeight());
  }
}
