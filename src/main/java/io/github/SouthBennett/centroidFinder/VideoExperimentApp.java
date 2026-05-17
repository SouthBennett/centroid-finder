// Tells Java what package/folder this class belongs to
package io.github.SouthBennett.centroidFinder;

// Imports the JavaCV class used to open and read video files
import org.bytedeco.javacv.FFmpegFrameGrabber;

// Imports the JavaCV Frame class which represents one video frame
import org.bytedeco.javacv.Frame;

// Imports a converter that changes a JavaCV Frame into a BufferedImage
import org.bytedeco.javacv.Java2DFrameConverter;

// Imports Java's built-in image saving tools
import javax.imageio.ImageIO;

// Imports Java's BufferedImage class for working with images
import java.awt.image.BufferedImage;

// Imports Java's File class for file paths and saving files
import java.io.File;

// Defines the class named VideoExperimentApp
public class VideoExperimentApp {

    // Main method where the program starts running
    public static void main(String[] args) throws Exception {

        // Stores the path to the video file we want to open
        String videoPath = "sampleVideo/ensantina.mp4";

        // Creates a video reader/grabber for the video file
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);

        // Opens the video file and prepares it for reading
        grabber.start();

        // title 
        System.out.println("Video metadata:");

        //  width of the video in pixels
        System.out.println("Width: " + grabber.getImageWidth());

        //  height of the video in pixels
        System.out.println("Height: " + grabber.getImageHeight());

        //  frame rate (frames per second)
        System.out.println("Frame rate: " + grabber.getFrameRate());

        //  total number of frames in the video
        System.out.println("Total frames: " + grabber.getLengthInFrames());

        //  total video length in microseconds
        System.out.println("Length in microseconds: " + grabber.getLengthInTime());

        try (// Creates a converter to turn JavaCV Frames into BufferedImages
        Java2DFrameConverter converter = new Java2DFrameConverter()) {
            // Grabs the next image frame from the video
            Frame frame = grabber.grabImage();
         

            // Checks if a valid frame was found
            if (frame != null) {

                // Converts the JavaCV Frame into a normal Java BufferedImage
                BufferedImage image = converter.convert(frame);

                // Creates a File object for where the image will be saved
                File output = new File("sampleOutput/first-frame.png");
 
                // Saves the BufferedImage as a PNG image file
                ImageIO.write(image, "png", output);

                //  location of the saved image
                System.out.println("Saved first frame to: " + output.getPath());

            } else {

                // Runs if no image frame could be extracted from the video
                System.out.println("No image frame found.");
            }
        }

        // Stops the video grabber and releases resources
        grabber.stop();

        // Fully closes the grabber
        grabber.close();
    }
}
