import java.util.Scanner;

public class GameUtils {
    public static boolean isValidCell(Location[][] arr, int row, int col) {
        return row >= 1 && row < (arr.length - 1) && col >= 1 && col < (arr[0].length - 1);
    }

    public static String[] handleScan(Scanner scan) {
        String size = scan.nextLine();
        String[] sizeSplit = size.split(" ");
        return sizeSplit;
    }
}
