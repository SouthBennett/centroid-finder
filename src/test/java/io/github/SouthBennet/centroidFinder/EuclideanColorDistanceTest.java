package test.java.io.github.SouthBennet.centroidFinder;
import static org.junit.Assert.*;
import org.junit.Test;

import main.java.io.github.SouthBennet.centroidFinder.ColorDistanceFinder;
import main.java.io.github.SouthBennet.centroidFinder.EuclideanColorDistance;

public class EuclideanColorDistanceTest {

  private static final double EPSILON = 0.0001;

  private final ColorDistanceFinder finder = new EuclideanColorDistance();

  @Test
  public void testSameColorBlackDistanceIsZero() {
    assertEquals(0.0, finder.distance(0x000000, 0x000000), EPSILON);
  }

  @Test
  public void testSameColorWhiteDistanceIsZero() {
    assertEquals(0.0, finder.distance(0xFFFFFF, 0xFFFFFF), EPSILON);
  }

  @Test
  public void testBlackToWhite() {
    double expected = Math.sqrt(255 * 255 + 255 * 255 + 255 * 255);

    assertEquals(expected, finder.distance(0x000000, 0xFFFFFF), EPSILON);
  }

  @Test
  public void testBlackToRed() {
    assertEquals(255.0, finder.distance(0x000000, 0xFF0000), EPSILON);
  }

  @Test
  public void testBlackToGreen() {
    assertEquals(255.0, finder.distance(0x000000, 0x00FF00), EPSILON);
  }

  @Test
  public void testBlackToBlue() {
    assertEquals(255.0, finder.distance(0x000000, 0x0000FF), EPSILON);
  }

  @Test
  public void testRedToGreen() {
    double expected = Math.sqrt(255 * 255 + 255 * 255);

    assertEquals(expected, finder.distance(0xFF0000, 0x00FF00), EPSILON);
  }

  @Test
  public void testRedToBlue() {
    double expected = Math.sqrt(255 * 255 + 255 * 255);


    assertEquals(expected, finder.distance(0xFF0000, 0x0000FF), EPSILON);
  }    



  @Test
  public void testGreenToBlue() {
    double expected = Math.sqrt(255 * 255 + 255 * 255);

    assertEquals(expected, finder.distance(0x00FF00, 0x0000FF), EPSILON);
  }

  @Test
  public void testSmallDifferenceOnlyInRed() {
    assertEquals(1.0, finder.distance(0x010000, 0x000000), EPSILON);
  }

  @Test
  public void testSmallDifferenceOnlyInGreen() {
    assertEquals(1.0, finder.distance(0x000100, 0x000000), EPSILON);
  }

  @Test
  public void testSmallDifferenceOnlyInBlue() {
    assertEquals(1.0, finder.distance(0x000001, 0x000000), EPSILON);
  }

  @Test
  public void testMixedColorDistance() {
    // colorA = (18, 52, 86)
    // colorB = (101, 67, 33)
    double expected = Math.sqrt(
      Math.pow(18 - 101, 2) +
      Math.pow(52 - 67, 2) +
      Math.pow(86 - 33, 2)
    );

    assertEquals(expected, finder.distance(0x123456, 0x654321), EPSILON);
  }

  @Test
  public void testDistanceIsSymmetric() {
    double distanceAB = finder.distance(0x123456, 0xABCDEF);
    double distanceBA = finder.distance(0xABCDEF, 0x123456);

    assertEquals(distanceAB, distanceBA, EPSILON);
  }
}
