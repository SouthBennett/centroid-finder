package test.java.io.github.SouthBennet.centroidFinder;
import static org.junit.Assert.*;
import org.junit.Test;

import main.java.io.github.SouthBennet.centroidFinder.BinarizingImageGroupFinder;
import main.java.io.github.SouthBennet.centroidFinder.BinaryGroupFinder;
import main.java.io.github.SouthBennet.centroidFinder.DfsBinaryGroupFinder;
import main.java.io.github.SouthBennet.centroidFinder.DistanceImageBinarizer;
import main.java.io.github.SouthBennet.centroidFinder.EuclideanColorDistance;
import main.java.io.github.SouthBennet.centroidFinder.ImageBinarizer;

import java.awt.image.BufferedImage;
import java.util.List;

public class BinarizingImageGroupFinderTest {

    @Test
    public void testFindConnectedGroups_noWhitePixelsReturnsEmptyList() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(
            new EuclideanColorDistance(),
            0xFFFFFF,
            1
        );

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        BinarizingImageGroupFinder finder =
            new BinarizingImageGroupFinder(binarizer, groupFinder);

        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0x000000);
        image.setRGB(1, 0, 0x000000);
        image.setRGB(0, 1, 0x000000);
        image.setRGB(1, 1, 0x000000);

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(0, groups.size());
    }

    @Test
    public void testFindConnectedGroups_singleWhitePixel() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(
            new EuclideanColorDistance(),
            0xFFFFFF,
            1
        );

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        BinarizingImageGroupFinder finder =
            new BinarizingImageGroupFinder(binarizer, groupFinder);

        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, 0xFFFFFF);
        image.setRGB(1, 0, 0x000000);
        image.setRGB(0, 1, 0x000000);
        image.setRGB(1, 1, 0x000000);

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());
        assertEquals(1, groups.get(0).size());
    }

    @Test
    public void testFindConnectedGroups_connectedWhitePixelsOneGroup() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(
            new EuclideanColorDistance(),
            0xFFFFFF,
            1
        );

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        BinarizingImageGroupFinder finder =
            new BinarizingImageGroupFinder(binarizer, groupFinder);

        BufferedImage image = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);

        image.setRGB(0, 0, 0xFFFFFF);
        image.setRGB(1, 0, 0xFFFFFF);
        image.setRGB(2, 0, 0x000000);

        image.setRGB(0, 1, 0x000000);
        image.setRGB(1, 1, 0xFFFFFF);
        image.setRGB(2, 1, 0x000000);

        image.setRGB(0, 2, 0x000000);
        image.setRGB(1, 2, 0x000000);
        image.setRGB(2, 2, 0x000000);

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());
        assertEquals(3, groups.get(0).size());
    }

    @Test
    public void testFindConnectedGroups_separateWhitePixelsTwoGroups() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(
            new EuclideanColorDistance(),
            0xFFFFFF,
            1
        );

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        BinarizingImageGroupFinder finder =
            new BinarizingImageGroupFinder(binarizer, groupFinder);

        BufferedImage image = new BufferedImage(3, 1, BufferedImage.TYPE_INT_RGB);

        image.setRGB(0, 0, 0xFFFFFF);
        image.setRGB(1, 0, 0x000000);
        image.setRGB(2, 0, 0xFFFFFF);

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(2, groups.size());
        assertEquals(1, groups.get(0).size());
        assertEquals(1, groups.get(1).size());
    }

    @Test
    public void testFindConnectedGroups_diagonalPixelsAreNotConnected() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(
            new EuclideanColorDistance(),
            0xFFFFFF,
            1
        );

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        BinarizingImageGroupFinder finder =
            new BinarizingImageGroupFinder(binarizer, groupFinder);

        BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

        image.setRGB(0, 0, 0xFFFFFF);
        image.setRGB(1, 0, 0x000000);
        image.setRGB(0, 1, 0x000000);
        image.setRGB(1, 1, 0xFFFFFF);

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(2, groups.size());
    }

    @Test
    public void testFindConnectedGroups_groupsSortedLargestFirst() {
        ImageBinarizer binarizer = new DistanceImageBinarizer(
            new EuclideanColorDistance(),
            0xFFFFFF,
            1
        );

        BinaryGroupFinder groupFinder = new DfsBinaryGroupFinder();
        BinarizingImageGroupFinder finder =
            new BinarizingImageGroupFinder(binarizer, groupFinder);

        BufferedImage image = new BufferedImage(4, 2, BufferedImage.TYPE_INT_RGB);

        image.setRGB(0, 0, 0xFFFFFF);
        image.setRGB(1, 0, 0xFFFFFF);
        image.setRGB(2, 0, 0x000000);
        image.setRGB(3, 0, 0xFFFFFF);

        image.setRGB(0, 1, 0xFFFFFF);
        image.setRGB(1, 1, 0x000000);
        image.setRGB(2, 1, 0x000000);
        image.setRGB(3, 1, 0x000000);

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(2, groups.size());
        assertEquals(3, groups.get(0).size());
        assertEquals(1, groups.get(1).size());
    }
}