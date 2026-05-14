package test.java.io.github.SouthBennet.centroidFinder;
import static org.junit.Assert.*;
import org.junit.Test;

import main.java.io.github.SouthBennet.centroidFinder.BinaryGroupFinder;
import main.java.io.github.SouthBennet.centroidFinder.DfsBinaryGroupFinder;
import main.java.io.github.SouthBennet.centroidFinder.Group;

import java.util.List;

public class DfsBinaryGroupFinderTest {

    private final BinaryGroupFinder finder = new DfsBinaryGroupFinder();

    @Test
    public void testSingleOnePixel() {
        int[][] image = {{1}};

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());
        assertGroup(groups.get(0), 1, 0, 0);
    }

    @Test
    public void testSingleZeroPixel() {
        int[][] image = {{0}};

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(0, groups.size());
    }

    @Test
    public void testAllZerosReturnsEmptyList() {
        int[][] image = {
            {0, 0, 0},
            {0, 0, 0}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(0, groups.size());
    }

    @Test
    public void testAllOnesOneBigGroup() {
        int[][] image = {
            {1, 1, 1},
            {1, 1, 1}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());
        assertGroup(groups.get(0), 6, 1, 0);
    }

    @Test
    public void testDiagonalOnesAreNotConnected() {
        int[][] image = {
            {1, 0},
            {0, 1}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(2, groups.size());
        assertGroup(groups.get(0), 1, 1, 1);
        assertGroup(groups.get(1), 1, 0, 0);
    }

    @Test
    public void testHorizontalConnection() {
        int[][] image = {
            {1, 1, 1, 1}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());
        assertGroup(groups.get(0), 4, 1, 0);
    }

    @Test
    public void testVerticalConnection() {
        int[][] image = {
            {1},
            {1},
            {1},
            {1}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());
        assertGroup(groups.get(0), 4, 0, 1);
    }

    @Test
    public void testMultipleGroupsSortedBySizeDescending() {
        int[][] image = {
            {1, 1, 0, 0, 1},
            {1, 0, 0, 0, 1},
            {0, 0, 1, 0, 0}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(3, groups.size());

        assertGroup(groups.get(0), 3, 0, 0);
        assertGroup(groups.get(1), 2, 4, 0);
        assertGroup(groups.get(2), 1, 2, 2);
    }

    @Test
    public void testCentroidUsesIntegerDivision() {
        int[][] image = {
            {1, 1},
            {1, 0}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size());

        // pixels: (0,0), (1,0), (0,1)
        // x = (0 + 1 + 0) / 3 = 0
        // y = (0 + 0 + 1) / 3 = 0
        assertGroup(groups.get(0), 3, 0, 0);
    }

    @Test
    public void testSeparateCornersCreatesFourGroups() {
        int[][] image = {
            {1, 0, 1},
            {0, 0, 0},
            {1, 0, 1}
        };

        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(4, groups.size());

        assertGroup(groups.get(0), 1, 2, 2);
        assertGroup(groups.get(1), 1, 2, 0);
        assertGroup(groups.get(2), 1, 0, 2);
        assertGroup(groups.get(3), 1, 0, 0);
    }

    @Test
    public void testNullImageThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            finder.findConnectedGroups(null);
        });
    }

    @Test
    public void testNullRowThrowsException() {
        int[][] image = {
            {1, 0},
            null,
            {0, 1}
        };

        assertThrows(NullPointerException.class, () -> {
            finder.findConnectedGroups(image);
        });
    }

    @Test
    public void testEmptyImageThrowsException() {
        int[][] image = {};

        assertThrows(IllegalArgumentException.class, () -> {
            finder.findConnectedGroups(image);
        });
    }

    @Test
    public void testEmptyRowThrowsException() {
        int[][] image = {
            {}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            finder.findConnectedGroups(image);
        });
    }

    @Test
    public void testJaggedArrayThrowsException() {
        int[][] image = {
            {1, 0, 1},
            {1, 0}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            finder.findConnectedGroups(image);
        });
    }

    @Test
    public void testInvalidValueThrowsException() {
        int[][] image = {
            {1, 0, 1},
            {0, 2, 0}
        };

        assertThrows(IllegalArgumentException.class, () -> {
            finder.findConnectedGroups(image);
        });
    }

    private void assertGroup(Group group, int expectedSize, int expectedX, int expectedY) {
        assertEquals(expectedSize, group.size());
        assertEquals(expectedX, group.centroid().x());
        assertEquals(expectedY, group.centroid().y());
    }
}