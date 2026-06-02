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


/**
 * Handles video-level processing by opening a video file, iterating over frames,
 * and delegating image processing to {@link ImageProcessor}.
 *
 * <p>Uses JavaCV and FFmpeg to read video frames, convert them to
 * {@link BufferedImage}, and process each extracted frame.</p>
 */
public class VideoFrameProcessor {

    /**
     * Processes the specified video using the provided color filter settings.
     *
     * @param videoPath       path to the input video file
     * @param outputCsv       path to the output CSV file for results
     * @param hexColorString  target color in hex form
     * @param threshold       color distance threshold for matching pixels
     * @throws Exception if video processing or frame extraction fails
     */
    public void processVideo(String videoPath, String outputCsv, String hexColorString, int threshold) throws Exception {
        processVideo(videoPath, hexColorString, outputCsv, threshold, Integer.MAX_VALUE);
    }

    /**
     * Processes the specified video and limits processing to a maximum number
     * of frames.
     *
     * @param videoPath       path to the input video file
     * @param hexColorString  target color in hex form
     * @param outputCsv       path to the output CSV file for results
     * @param threshold       color distance threshold for matching pixels
     * @param maxFrames       maximum number of frames to process
     * @throws Exception if video processing or frame extraction fails
     */
    public void processVideo(String videoPath, String hexColorString, String outputCsv, int threshold, int maxFrames) throws Exception {
        // video grabber to process frames
        FFmpegFrameGrabber grabber = createGrabber(videoPath);
        try {
            // print meta data
            printVideoMetadata(grabber);
            extractVideoFrames(grabber, hexColorString, outputCsv, threshold, maxFrames);
        } finally {
            // saveFrame(firstFrame, "sampleOutput/Processed-frame.png");
            closeGrabber(grabber);
        }
    }

    /**
     * Creates and starts an {@link FFmpegFrameGrabber} for the provided video
     * path.
     *
     * @param videoPath path to the input video file
     * @return a started {@link FFmpegFrameGrabber}
     * @throws Exception if the grabber cannot be initialized or started
     */
    public static FFmpegFrameGrabber createGrabber(String videoPath) throws Exception {
        File videoFile = new File(videoPath);

        if (!videoFile.exists()) {
            throw new IllegalArgumentException("Video file does not exist: " + videoPath);
        }

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
        grabber.start();
        return grabber;
    }

    /**
     * Prints basic metadata from the provided video grabber.
     *
     * @param grabber the grabber from which metadata is read
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
     * Extracts frames from the video grabber, converts each frame to a
     * {@link BufferedImage}, and processes the image with {@link ImageProcessor}.
     *
     * @param grabber         the video grabber providing frames
     * @param hexColorString  target color in hex form
     * @param outputCsv       path to the output CSV file for results
     * @param threshold       color distance threshold for matching pixels
     * @param maxFrames       maximum number of frames to process
     * @throws Exception if frame extraction or image processing fails
     */
    public static void extractVideoFrames(FFmpegFrameGrabber grabber, String hexColorString, String outputCsv, int threshold, int maxFrames) throws Exception {
        int frameCount = 0;

        while (frameCount < maxFrames) {
            Frame frame = grabber.grabImage();
            if (frame == null) break;

            try (Java2DFrameConverter converter = new Java2DFrameConverter()) {
                BufferedImage videoImage = converter.convert(frame);
                if (videoImage == null) continue;

                frameCount++;
                double timestampSeconds = grabber.getTimestamp() / 1_000_000.0;
                ImageProcessor imagePro = new ImageProcessor();
                imagePro.processImage(videoImage, hexColorString, outputCsv, threshold, timestampSeconds);
                System.out.println("Frame counter:" + frameCount);
            }
        }
    }

    /**
     * Stops and closes the grabber, releasing native resources.
     *
     * @param grabber the video grabber to close
     * @throws Exception if stopping or closing the grabber fails
     */
    public static void closeGrabber(FFmpegFrameGrabber grabber) throws Exception {
        grabber.stop();
        grabber.close();
    }
}
