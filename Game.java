import java.util.Random;
import java.util.Scanner;

public class Game {
    private boolean gameComplete = false;
    private boolean allOut = true;
    public Location[][] g;
    int[][] surr = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

    String CYAN = "\u001B[36m";
    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_PURPLE = "\u001B[35m";
    String LOW_INTENSITY = "\u001B[2m";
    String MAGENTA = "\u001B[35m";

    public Location[][] getG() {
        return g;
    }

    public void setG(Location[][] g) {
        this.g = g;
    }

    public Game() {
    }

    public boolean getAllOut() {
        return this.allOut;
    }

    public void setAllOut(boolean val) {
        this.allOut = val;
    }

    public boolean getGameComplete() {
        return this.gameComplete;
    }

    public void setGameComplete(boolean val) {
        this.gameComplete = val;
    }

    public void greeting() {
        System.out.println(CYAN + "Welcome to Minesweeper" + ANSI_RESET);
        // System.out
        // .println("Choose the size of the grid and the number of bombs by typing two
        // numbers divided by space:");
    }

    public void resetBoard(Game game, Scanner myScan) {
        setGameComplete(false);
        System.out
                .println("Choose the size of the grid and the number of bombs by typing two numbers divided by space:");
        String[] scannedSize = game.handleScan(myScan);
        int gridInt = Integer.parseInt(scannedSize[0]);
        int bombInt = Integer.parseInt(scannedSize[1]);

        Location[][] g = game.createBoard(gridInt, bombInt);

        game.setG(g);

        game.printBoard(g);

        System.out.println("Let's look for some bombs");
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

    public boolean isValidCell(Location[][] arr, int row, int col) {
        return row >= 1 && row < (arr.length - 1) && col >= 1 && col < (arr[0].length - 1);
    }

    public void revealNeighbours(Location[][] arr, int a, int b) {

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

    public void handleTurn(int x, int y, Location[][] g, Game game) {
        if (g[x][y].value == 10) {
            for (Location[] bombs : g) {
                for (Location bomb : bombs) {
                    if (bomb.value == 10) {
                        bomb.revealed = true;
                    }
                }
            }
            game.setGameComplete(true);
            g[x][y].revealed = true;

        } else if (g[x][y].value > 0 && g[x][y].value < 9) {
            g[x][y].revealed = true;
            game.printBoard(g);
        } else {

            g[x][y].revealed = true;
            revealNeighbours(g, x, y);
            game.printBoard(g);
        }
    }

    public String[] handleScan(Scanner scan) {
        String size = scan.nextLine();
        String[] sizeSplit = size.split(" ");
        return sizeSplit;
    }

    public void checkAllOut(Location[][] g, Game game) {
        game.setAllOut(true);
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                if (g[i][j].value >= 0 && g[i][j].value < 9 && !g[i][j].revealed) {
                    game.setAllOut(false);
                    break;
                }
            }
        }

    }

}
