package io.github.SouthBennett.centroidFinder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageProcessor {
  public void processImage(BufferedImage videoImage, String hexTargetColor, int threshold) {
    // Parse the target color from a hex string (format RRGGBB) into a 24-bit integer (0xRRGGBB)
    int targetColor = 0;
    try {
      targetColor = Integer.parseInt(hexTargetColor, 16);
    } catch (NumberFormatException e) {
      System.err.println("Invalid hex target color. Please provide a color in RRGGBB format.");
      return;
    }
    
    // Create the DistanceImageBinarizer with a EuclideanColorDistance instance.
    ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
    ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
    
    // Binarize the input image.
    int[][] binaryArray = binarizer.toBinaryArray(videoImage);
    BufferedImage binaryImage = binarizer.toBufferedImage(binaryArray);
    
    // Write the binarized image to disk as "binarized.png".
    try {
      ImageIO.write(binaryImage, "png", new File("binarized.png"));
      System.out.println("Binarized image saved as binarized.png");
    } catch (Exception e) {
      System.err.println("Error saving binarized image.");
      e.printStackTrace();
    }
    
    // Create an ImageGroupFinder using a BinarizingImageGroupFinder with a DFS-based BinaryGroupFinder.
    ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
    
    // Find connected groups in the input image.
    // The BinarizingImageGroupFinder is expected to internally binarize the image,
    // then locate connected groups of white pixels.
    List<Group> groups = groupFinder.findConnectedGroups(videoImage);
    
    // Write the groups information to a CSV file "groups.csv".
    try (PrintWriter writer = new PrintWriter("groups.csv")) {
      for (Group group : groups) {
        writer.println(group.toCsvRow());
      }
      System.out.println("Groups summary saved as groups.csv");
    } catch (Exception e) {
      System.err.println("Error writing groups.csv");
      e.printStackTrace();
    }
  }
}
