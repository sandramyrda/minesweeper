import java.util.Random;

public class Game {
    boolean gameComplete = false;
    String CYAN = "\u001B[36m";
    String ANSI_RESET = "\u001B[0m";

    int[][] surr = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
            { 1, 1 } };

    public Game() {
    }

    public void greeting() {
        System.out.println(CYAN + "Welcome to Minesweeper" + ANSI_RESET);
        System.out
                .println("Choose the size of the grid and the number of bombs by typing two numbers divided by space:");
    }

    public Location[][] createBoard(int grid, int bombs) {

        Location[][] board = new Location[grid + 2][grid + 2];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Location();
            }
        }
        // dropping random bombs
        Random rand = new Random();

        for (int i = 1; i <= bombs; i++) {
            int rand_a = rand.nextInt(grid - 1) + 1;
            int rand_b = rand.nextInt(grid - 1) + 1;

            board[rand_a][rand_b].value = 10;
        }
        // drawing the borders
        for (int i = 0; i < (grid + 2); i++) {
            board[0][i].value = 9;
        }

        for (int i = 0; i < (grid + 2); i++) {
            board[grid + 1][i].value = 9;
        }

        for (int i = 0; i < (grid + 2); i++) {
            board[i][0].value = 9;
        }

        for (int i = 0; i < (grid + 2); i++) {
            board[i][grid + 1].value = 9;
        }

        // counting the surrounding bombs
        for (int i = 1; i < (grid + 1); i++) {
            for (int j = 1; j < (grid + 1); j++) {

                if (board[i][j].value != 10) {

                    int bombsAround = 0;

                    // int[][] surr = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1,
                    // -1 }, { 1, 0 },
                    // { 1, 1 } };

                    for (int[] spot : surr) {
                        int row = i + spot[0];
                        int col = j + spot[1];

                        if (board[row][col].value == 10) {
                            bombsAround++;
                        }
                    }

                    board[i][j].value = bombsAround;
                }
            }
        }

        return board;
    }

    public void printBoard(Location[][] board) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_PURPLE = "\u001B[35m";
        String LOW_INTENSITY = "\u001B[2m";
        String MAGENTA = "\u001B[35m";

        for (Location[] row : board) {
            for (Location num : row) {
                if (num.revealed == true && num.value == 10) {
                    System.out.print(MAGENTA + "✷ " + ANSI_RESET);
                } else if (num.revealed == true && num.value == 1) {
                    System.out.print(ANSI_BLUE + "1 " + ANSI_RESET);
                } else if (num.revealed == true && num.value == 2) {
                    System.out.print(ANSI_GREEN + "2 " + ANSI_RESET);
                } else if (num.revealed == true && num.value == 3) {
                    System.out.print(ANSI_RED + "3 " + ANSI_RESET);
                } else if (num.revealed == true
                        && (num.value == 4 || num.value == 5 || num.value == 6 || num.value == 7 || num.value == 8)) {
                    System.out.print(ANSI_PURPLE + "4 " + ANSI_RESET);
                } else if (num.value == 9) {
                    System.out.print("● ");
                } else if (num.revealed == true) {
                    System.out.print("■ ");
                } else {
                    System.out.print(LOW_INTENSITY + "■ " + ANSI_RESET);
                }

            }
            System.out.println();
        }
    }

    // revealBombs method?
}
