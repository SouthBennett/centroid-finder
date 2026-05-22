package io.github.SouthBennett.centroidFinder;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.junit.jupiter.api.Test;

public class VideoProcessorTest {

    private static final String VALID_VIDEO_PATH = "sampleVideo/ensantina.mp4";

    @Test
    public void processVideoReturnsImage() throws Exception {
        VideoProcessor processor = new VideoProcessor();

        BufferedImage image = processor.processVideo(VALID_VIDEO_PATH);

        assertNotNull(image);
    }

    @Test
    public void processVideoReturnsImageWithPositiveWidth() throws Exception {
        VideoProcessor processor = new VideoProcessor();

        BufferedImage image = processor.processVideo(VALID_VIDEO_PATH);

        assertTrue(image.getWidth() > 0);
    }

    @Test
    public void processVideoReturnsImageWithPositiveHeight() throws Exception {
        VideoProcessor processor = new VideoProcessor();

        BufferedImage image = processor.processVideo(VALID_VIDEO_PATH);

        assertTrue(image.getHeight() > 0);
    }

    @Test
    public void createGrabberReturnsNonNullGrabber() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertNotNull(grabber);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveVideoWidth() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getImageWidth() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveVideoHeight() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getImageHeight() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveFrameRate() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getFrameRate() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberHasPositiveFrameCount() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        assertTrue(grabber.getLengthInFrames() > 0);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void extractFirstFrameVideoReturnsImage() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        BufferedImage image = VideoProcessor.extractFirstFrameVideo(grabber);

        assertNotNull(image);

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void extractedFrameHasSameWidthAsVideo() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        int videoWidth = grabber.getImageWidth();

        BufferedImage image = VideoProcessor.extractFirstFrameVideo(grabber);

        assertEquals(videoWidth, image.getWidth());

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void extractedFrameHasSameHeightAsVideo() throws Exception {
        FFmpegFrameGrabber grabber = VideoProcessor.createGrabber(VALID_VIDEO_PATH);

        int videoHeight = grabber.getImageHeight();

        BufferedImage image = VideoProcessor.extractFirstFrameVideo(grabber);

        assertEquals(videoHeight, image.getHeight());

        VideoProcessor.closeGrabber(grabber);
    }

    @Test
    public void createGrabberThrowsExceptionForBadPath() {
        assertThrows(
            IllegalArgumentException.class,
            () -> VideoProcessor.createGrabber("sampleVideo/does-not-exist.mp4")
        );
    }
}