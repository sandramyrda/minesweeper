import java.util.Arrays;

/**
 * Minesweeper
 */
public class Minesweeper {

    public static void main(String[] args) {

        int[][] board = new int[10][10];

        System.out.println(Arrays.deepToString(board).replace("], ", "]\n"));
    }
}