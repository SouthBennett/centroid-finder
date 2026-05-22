package io.github.SouthBennett.centroidFinder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.junit.jupiter.api.Test;

public class VideoProcessorTest {

    private static final String VALID_VIDEO_PATH =
            "sampleVideo/ensantina.mp4";

    @Test
    public void processVideoRunsWithoutThrowing() {

        VideoProcessor processor = new VideoProcessor();

        assertDoesNotThrow(() ->
            processor.processVideo(
                VALID_VIDEO_PATH,
                "000000",
                125,
                1
            )
        );
    }

    @Test
    public void processVideoCreatesBinarizedImage() throws Exception {

        File outputFile =
                new File("binarized.png");

        if (outputFile.exists()) {
            outputFile.delete();
        }

        VideoProcessor processor = new VideoProcessor();

        processor.processVideo(
                VALID_VIDEO_PATH,
                "000000",
                125,
                1
        );

        assertTrue(outputFile.exists());
    }

    @Test
    public void processVideoCreatesGroupsCSV() throws Exception {

        File outputFile =
                new File("groups.csv");

        if (outputFile.exists()) {
            outputFile.delete();
        }

        VideoProcessor processor = new VideoProcessor();

        processor.processVideo(
                VALID_VIDEO_PATH,
                "000000",
                125,
                1
        );

        assertTrue(outputFile.exists());
    }

    @Test
    public void createGrabberReturnsNonNullGrabber() throws Exception {

        FFmpegFrameGrabber grabber =
                VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertNotNull(grabber);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveVideoWidth() throws Exception {

        FFmpegFrameGrabber grabber =
                VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getImageWidth() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveVideoHeight() throws Exception {

        FFmpegFrameGrabber grabber =
                VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getImageHeight() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveFrameRate() throws Exception {

        FFmpegFrameGrabber grabber =
                VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getFrameRate() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveFrameCount() throws Exception {

        FFmpegFrameGrabber grabber =
                VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getLengthInFrames() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberThrowsForBadPath() {

        assertThrows(
            IllegalArgumentException.class,
            () -> VideoProcessor.createGrabber(
                "sampleVideo/does-not-exist.mp4"
            )
        );
    }

    @Test
    public void processVideoThrowsForBadPath() {

        VideoProcessor processor = new VideoProcessor();

        assertThrows(
            IllegalArgumentException.class,
            () -> processor.processVideo(
                "sampleVideo/does-not-exist.mp4",
                "000000",
                125,
                1
            )
        );
    }

    @Test
    public void extractVideoFramesRunsWithoutThrowing() throws Exception {

        FFmpegFrameGrabber grabber =
                VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertDoesNotThrow(() ->
            VideoProcessor.extractVideoFrames(
                grabber,
                "000000",
                125,
                1
            )
        );

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void extractVideoFramesThrowsForInvalidHexColor() throws Exception {

        FFmpegFrameGrabber grabber =
                VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertThrows(
            IllegalArgumentException.class,
            () -> VideoProcessor.extractVideoFrames(
                grabber,
                "BADHEX",
                125,
                1
            )
        );

        VideoProcessor.closeGrabber(grabber);
    }
}