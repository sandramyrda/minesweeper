
import java.util.Scanner;

public class Minesweeper {

    public static boolean isValidCell(Location[][] arr, int row, int col) {
        return row >= 1 && row < (arr.length - 1) && col >= 1 && col < (arr[0].length - 1);
    }

    public static void revealNeighbours(Location[][] arr, int a, int b) {

        int[][] surr = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1,
                -1 }, { 1, 0 },
                { 1, 1 } };

        for (int[] spot : surr) {
            int row = a + spot[0];
            int col = b + spot[1];

            if (isValidCell(arr, row, col) && !arr[row][col].revealed && arr[row][col].value == 0) {
                arr[row][col].revealed = true;
                revealNeighbours(arr, row, col);
            } else {
                arr[row][col].revealed = true;
            }

        }

    }

    public static void main(String[] args) {

        Game game = new Game();

        Location[][] g = game.createBoard();

        game.greeting();

        game.printBoard(g);

        Scanner myScan = new Scanner(System.in);

        System.out.println("Let's look for some bombs");

        while (!game.gameComplete) {
            System.out.println("Choose a location, give me two numbers (1-10) divided by space: ");
            String loc = myScan.nextLine();
            String[] split = loc.split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            if (g[x][y].value == 8) {
                game.gameComplete = true;
                g[x][y].revealed = true;
                game.printBoard(g);
                System.out.println("˗ˏˋ BOOM ˎˊ˗");
            } else if (g[x][y].value > 0 && g[x][y].value < 8) {
                g[x][y].revealed = true;
                game.printBoard(g);
            } else {

                g[x][y].revealed = true;
                revealNeighbours(g, x, y);
                game.printBoard(g);
            }
        }

    }
}