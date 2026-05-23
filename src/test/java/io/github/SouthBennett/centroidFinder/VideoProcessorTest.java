package io.github.SouthBennett.centroidFinder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.junit.jupiter.api.Test;

public class VideoProcessorTest {

    private static final String VALID_VIDEO_PATH = "sampleVideo/ensantina.mp4";

    @Test
    public void processVideoRunsWithoutThrowing() {
        VideoFrameProcessor processor = new VideoFrameProcessor();

        assertDoesNotThrow(() ->
            processor.processVideo(
                VALID_VIDEO_PATH,
                "000000",
                "test-groups.csv",
                125,
                1
            )
        );
    }

    @Test
    public void processVideoCreatesGivenGroupsCsv() throws Exception {
        File outputFile = new File("test-groups.csv");

    
        if (outputFile.exists()) {
            outputFile.delete();
        }

        VideoFrameProcessor processor = new VideoFrameProcessor();

        processor.processVideo(
            VALID_VIDEO_PATH,
            "000000",
            "test-groups.csv",
            125,
            1
        );

        assertTrue(outputFile.exists());
    }

    @Test
    public void processVideoCreatesBinarizedImage() throws Exception {
        File outputFile = new File("binarized.png");

   

        if (outputFile.exists()) {
            outputFile.delete();
        }

        VideoFrameProcessor processor = new VideoFrameProcessor();

        processor.processVideo(
            VALID_VIDEO_PATH,
            "000000",
            "test-groups.csv",
            125,
            1
        );

        assertTrue(outputFile.exists());
    }

    @Test
    public void createGrabberReturnsNonNullGrabber() throws Exception {
        FFmpegFrameGrabber grabber =
            VideoFrameProcessor.createGrabber(VALID_VIDEO_PATH);

        assertNotNull(grabber);

        VideoFrameProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveVideoWidth() throws Exception {
        FFmpegFrameGrabber grabber =
            VideoFrameProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getImageWidth() > 0);

        VideoFrameProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveVideoHeight() throws Exception {
        FFmpegFrameGrabber grabber =
            VideoFrameProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getImageHeight() > 0);

        VideoFrameProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveFrameRate() throws Exception {
        FFmpegFrameGrabber grabber =
            VideoFrameProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getFrameRate() > 0);

        VideoFrameProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveFrameCount() throws Exception {
        FFmpegFrameGrabber grabber =
            VideoFrameProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getLengthInFrames() > 0);

        VideoFrameProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberThrowsForBadPath() {
        assertThrows(
            IllegalArgumentException.class,
            () -> VideoFrameProcessor.createGrabber(
                "sampleVideo/does-not-exist.mp4"
            )
        );
    }

    @Test
    public void processVideoThrowsForBadPath() {
        VideoFrameProcessor processor = new VideoFrameProcessor();

        assertThrows(
            IllegalArgumentException.class,
            () -> processor.processVideo(
                "sampleVideo/does-not-exist.mp4",
                "000000",
                "sampleOutput/test-groups.csv",
                125,
                1
            )
        );
    }

    @Test
    public void extractVideoFramesRunsWithoutThrowing() throws Exception {
        FFmpegFrameGrabber grabber =
            VideoFrameProcessor.createGrabber(VALID_VIDEO_PATH);

        assertDoesNotThrow(() ->
            VideoFrameProcessor.extractVideoFrames(
                grabber,
                "000000",
                "test-groups.csv",
                125,
                1
            )
        );

        VideoFrameProcessor.closeGrabber(grabber);
    }

    @Test
    public void extractVideoFramesThrowsForInvalidHexColor() throws Exception {
        FFmpegFrameGrabber grabber =
            VideoFrameProcessor.createGrabber(VALID_VIDEO_PATH);

        assertThrows(
            IllegalArgumentException.class,
            () -> VideoFrameProcessor.extractVideoFrames(
                grabber,
                "BADHEX",
                "sampleOutput/test-groups.csv",
                125,
                1
            )
        );

        VideoFrameProcessor.closeGrabber(grabber);
    }
}