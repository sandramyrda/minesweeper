import java.util.Random;

public class Game {
    boolean gameComplete = false;
    String CYAN = "\u001B[36m";
    String ANSI_RESET = "\u001B[0m";

    public Game() {
    }

    public void greeting() {
        System.out.println(CYAN + "Welcome to Minesweeper" + ANSI_RESET);
    }

    public Location[][] createBoard() {
        
        Location[][] board = new Location[12][12];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Location();
            }
        }
        // dropping random bombs
        Random rand = new Random();

        for (int i = 0; i <= 10; i++) {
            int rand_a = rand.nextInt(9) + 1;
            int rand_b = rand.nextInt(9) + 1;

            board[rand_a][rand_b].value = 8;
        }
        // drawing the borders
        for (int i = 0; i < 12; i++) {
            board[0][i].value = 9;
        }

        for (int i = 0; i < 12; i++) {
            board[11][i].value = 9;
        }

        for (int i = 0; i < 12; i++) {
            board[i][0].value = 9;
        }

        for (int i = 0; i < 12; i++) {
            board[i][11].value = 9;
        }

        // counting the surrounding bombs
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {

                if (board[i][j].value != 8) {

                    int bombsAround = 0;

                    int[][] surr = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 },
                            { 1, 1 } };

                    for (int[] spot : surr) {
                        int row = i + spot[0];
                        int col = j + spot[1];

                        if (board[row][col].value == 8) {
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
                if (num.revealed == true && num.value == 8) {
                    System.out.print(MAGENTA + "✷ " + ANSI_RESET);
                } else if (num.revealed == true && num.value == 1) {
                    System.out.print(ANSI_BLUE + "1 " + ANSI_RESET);
                } else if (num.revealed == true && num.value == 2) {
                    System.out.print(ANSI_GREEN + "2 " + ANSI_RESET);
                } else if (num.revealed == true && num.value == 3) {
                    System.out.print(ANSI_RED + "3 " + ANSI_RESET);
                } else if (num.revealed == true && num.value == 4) {
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
}
