package io.github.SouthBennett.centroidFinder;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ImageProcessorTest {

    /**
     * Tests hex color parsing.
     */
    @Test
    public void testParseTargetColor() {

        ImageProcessor processor =
            new ImageProcessor();

        int parsedColor =
            processor.parseTargetColor(
                "FFFFFF"
            );

        assertEquals(
            0xFFFFFF,
            parsedColor
        );
    }

    /**
     * Tests invalid hex string.
     */
    @Test
    public void testParseInvalidTargetColor() {

        ImageProcessor processor =
            new ImageProcessor();

        assertThrows(
            IllegalArgumentException.class,

            () -> processor.parseTargetColor(
                "ZZZZZZ"
            )
        );
    }

    /**
     * Tests binary array dimensions.
     */
    @Test
    public void testCreateBinaryArrayDimensions() {

        BufferedImage image =
            new BufferedImage(
                5,
                5,
                BufferedImage.TYPE_INT_RGB
            );

        ImageProcessor processor =
            new ImageProcessor();

        ColorDistanceFinder distanceFinder =
            new EuclideanColorDistance();

        ImageBinarizer binarizer =
            processor.createBinarizer(
                distanceFinder,
                0x000000,
                115
            );

        int[][] binaryArray =
            processor.createBinaryArray(
                binarizer,
                image
            );

        assertEquals(
            5,
            binaryArray.length
        );

        assertEquals(
            5,
            binaryArray[0].length
        );
    }

    /**
     * Tests that black pixel becomes white
     * in binary array when matching target color.
     */
    @Test
    public void testBinaryArrayContainsWhitePixel() {

        BufferedImage image =
            new BufferedImage(
                3,
                3,
                BufferedImage.TYPE_INT_RGB
            );

        image.setRGB(
            1,
            1,
            Color.BLACK.getRGB()
        );

        ImageProcessor processor =
            new ImageProcessor();

        ColorDistanceFinder distanceFinder =
            new EuclideanColorDistance();

        ImageBinarizer binarizer =
            processor.createBinarizer(
                distanceFinder,
                0x000000,
                115
            );

        int[][] binaryArray =
            processor.createBinaryArray(
                binarizer,
                image
            );

        assertEquals(
            1,
            binaryArray[1][1]
        );
    }

    /**
     * Tests DFS connected group finding.
     */
    @Test
    public void testFindGroups() {

        BufferedImage image =
            new BufferedImage(
                5,
                5,
                BufferedImage.TYPE_INT_RGB
            );

        // create connected black square
        image.setRGB(1,1, Color.BLACK.getRGB());
        image.setRGB(1,2, Color.BLACK.getRGB());
        image.setRGB(2,1, Color.BLACK.getRGB());
        image.setRGB(2,2, Color.BLACK.getRGB());

        ImageProcessor processor =
            new ImageProcessor();

        ColorDistanceFinder distanceFinder =
            new EuclideanColorDistance();

        ImageBinarizer binarizer =
            processor.createBinarizer(
                distanceFinder,
                0x000000,
                115
            );

        List<Group> groups =
            processor.findGroups(
                binarizer,
                image
            );

        assertEquals(
            1,
            groups.size()
        );
    }

    /**
     * Tests binary image reconstruction.
     */
    @Test
    public void testCreateBinaryImage() {

        int[][] binaryArray = {

            {0,0,0},
            {0,1,0},
            {0,0,0}
        };

        ImageProcessor processor =
            new ImageProcessor();

        ColorDistanceFinder distanceFinder =
            new EuclideanColorDistance();

        ImageBinarizer binarizer =
            processor.createBinarizer(
                distanceFinder,
                0x000000,
                115
            );

        BufferedImage image =
            processor.createBinaryImage(
                binarizer,
                binaryArray
            );

        assertNotNull(image);

        assertEquals(
            3,
            image.getWidth()
        );

        assertEquals(
            3,
            image.getHeight()
        );
    }
}
