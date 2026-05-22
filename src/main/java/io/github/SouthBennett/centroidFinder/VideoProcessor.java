// Tells Java what package/folder this class belongs to
package io.github.SouthBennett.centroidFinder;

// Imports the JavaCV class used to open and read video files
import org.bytedeco.javacv.FFmpegFrameGrabber;

// Imports the JavaCV Frame class which represents one video frame
import org.bytedeco.javacv.Frame;

// Imports a converter that changes a JavaCV Frame into a BufferedImage
import org.bytedeco.javacv.Java2DFrameConverter;

// Imports Java's BufferedImage class for working with images
import java.awt.image.BufferedImage;
import java.io.File;


public class VideoProcessor {

    public BufferedImage  processVideo(String videoPath) throws Exception{


  
            // video grabber to process frames
            FFmpegFrameGrabber grabber = createGrabber(videoPath);
    


        try {
            // print meta data
            printVideoMetadata(grabber);
    
            // extract first frame
            return extractFirstFrameVideo(grabber);
        } finally {
            // saveFrame(firstFrame, "sampleOutput/Processed-frame.png");
            closeGrabber(grabber);
        }
    }

    /**
     * Creates and starts the video grabber.
     */
    public static FFmpegFrameGrabber createGrabber(String videoPath) throws Exception {

        File videoFile = new File(videoPath);

        if(!videoFile.exists()){
            throw new IllegalArgumentException("Video file does not exist: " + videoPath);
        }

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);

        grabber.start();

        return grabber;
    }

    /**
     * Prints video information.
     */
    public static void printVideoMetadata(FFmpegFrameGrabber grabber) {

        System.out.println("Video metadata:");

        System.out.println("Width: " + grabber.getImageWidth());

        System.out.println("Height: " + grabber.getImageHeight());

        System.out.println("Frame rate: " + grabber.getFrameRate());

        System.out.println("Total frames: " + grabber.getLengthInFrames());

        System.out.println("Length in microseconds: " + grabber.getLengthInTime());
    }


    /**
     * Extracts the first frame from the video.
     */
    public static BufferedImage extractFirstFrameVideo(FFmpegFrameGrabber grabber) throws Exception {

        try (Java2DFrameConverter converter = new Java2DFrameConverter()) {

            Frame frame = grabber.grabImage();

            if (frame == null) {

                System.out.println("No image frame found.");

                return null;
            }

            return converter.convert(frame);
        }
    }

    /**
     * Stops and closes the grabber.
     */
    public static void closeGrabber(FFmpegFrameGrabber grabber) throws Exception {

        grabber.stop();

        grabber.close();
    }
}
