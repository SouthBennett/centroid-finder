package io.github.SouthBennett.centroidFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DfsBinaryGroupFinder implements BinaryGroupFinder {
   /**
    * Finds connected pixel groups of 1s in an integer array representing a binary image.
    * 
    * The input is a non-empty rectangular 2D array containing only 1s and 0s.
    * If the array or any of its subarrays are null, a NullPointerException
    * is thrown. If the array is otherwise invalid, an IllegalArgumentException
    * is thrown.
    *
    * Pixels are considered connected vertically and horizontally, NOT diagonally.
    * The top-left cell of the array (row:0, column:0) is considered to be coordinate
    * (x:0, y:0). Y increases downward and X increases to the right. For example,
    * (row:4, column:7) corresponds to (x:7, y:4).
    *
    * The method returns a list of sorted groups. The group's size is the number 
    * of pixels in the group. The centroid of the group
    * is computed as the average of each of the pixel locations across each dimension.
    * For example, the x coordinate of the centroid is the sum of all the x
    * coordinates of the pixels in the group divided by the number of pixels in that group.
    * Similarly, the y coordinate of the centroid is the sum of all the y
    * coordinates of the pixels in the group divided by the number of pixels in that group.
    * The division should be done as INTEGER DIVISION.
    *
    * The groups are sorted in DESCENDING order according to Group's compareTo method.
    * 
    * @param image a rectangular 2D array containing only 1s and 0s
    * @return the found groups of connected pixels in descending order
    */
    @Override
    public List<Group> findConnectedGroups(int[][] image) {
        if(!findConnectedGroupsErrorCheck(image)) return null;

        List<Group> groups = new ArrayList<>();
            for(int r = 0; r < image.length; r++){
                for(int c = 0; c < image[0].length; c++){

                    GroupStats stats = validMovesAndSize(image, r, c);
                    int avgX = 0;
                    int avgY = 0;

                    if(stats.size() > 0){
                        avgX = stats.sumX() / stats.size();
                        avgY = stats.sumY() / stats.size();
                        System.out.println(avgX + " x ");
                        System.out.println(avgY + " y ");
                        System.out.println(stats.size() + "size");
                    }
                        
                    if( stats.size() != 0){
                        Group group = new Group(stats.size(), new Coordinate(avgX, avgY));
                        groups.add(group);

                    }
                }
            } 
        groups.sort(Collections.reverseOrder());
        return groups;
    }

  

    private GroupStats validMovesAndSize(int[][] image, int row, int col){
        GroupStats empty = new GroupStats(0, 0, 0);
        if(row < 0 || row >= image.length || col < 0 || col >= image[0].length){
            return empty;
        }

        if(image[row][col] == 0) return empty;

        int size = 1;
        int x = col;
        int y = row;

        image[row][col] = 0;

        GroupStats up = validMovesAndSize(image, row - 1, col);
        size += up.size();
        x += up.sumX();
        y += up.sumY();
        
        GroupStats down = validMovesAndSize(image, row + 1, col);
        size += down.size();
        x += down.sumX();
        y += down.sumY();

        GroupStats left = validMovesAndSize(image, row, col - 1 );
        size += left.size();
        x += left.sumX();
        y += left.sumY();


        GroupStats right = validMovesAndSize(image, row, col + 1);
        size += right.size();
        x += right.sumX();
        y += right.sumY();

        GroupStats group = new GroupStats(size, x, y);

        return group;
    }

    private boolean findConnectedGroupsErrorCheck(int[][] image){
        if(image == null) throw new NullPointerException();

        if(image.length == 0 || image[0].length == 0) throw new IllegalArgumentException();

        int cols = image[0].length;

        for(int row = 0; row < image.length; row++){
            if(image[row] == null){
                throw new NullPointerException();
            }
            if (image[row].length != cols) {
                throw new IllegalArgumentException();
            }
            for (int col = 0; col < image[row].length; col++) {
                if(image[row][col] != 0 && image[row][col] != 1) {
                    throw new IllegalArgumentException();
                }
            }
        }
        return true;
    }
    
}
