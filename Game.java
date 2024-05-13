import java.util.Random;
import java.util.Scanner;

public class Game {

    // if (game.allOut) -> inside the handleTurn function
    // if gamecomplete can be outside the while loop

    // class GameState or const = {object with values}

    // handleTurn method - has access to all the state so getAllOut should be done
    // through that function

    private boolean gameComplete = false;
    private boolean allOut = true;
    public Location[][] g;
    public HistoryWriter historian = new HistoryWriter();
    int[][] surr = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };

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

    public void resetBoard(int gridInt, int bombInt) {
        setGameComplete(false);

        this.g = createBoard(gridInt, bombInt);

        printBoard();

        System.out.println("Let's look for some bombs");
    }

    public Location[][] createBoard(int grid, int bombs) {

        // Location[][] board = new Location[grid + 2][grid + 2];

        this.g = new Location[grid + 2][grid + 2];

        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                g[i][j] = new Location();
            }
        }
        // dropping random bombs
        Random rand = new Random();

        for (int i = 1; i <= bombs; i++) {
            int rand_a = rand.nextInt(grid - 1) + 1;
            int rand_b = rand.nextInt(grid - 1) + 1;

            g[rand_a][rand_b].value = 10;
        }
        // drawing the borders
        for (int i = 0; i < (grid + 2); i++) {
            g[0][i].value = 9;
        }

        for (int i = 0; i < (grid + 2); i++) {
            g[grid + 1][i].value = 9;
        }

        for (int i = 0; i < (grid + 2); i++) {
            g[i][0].value = 9;
        }

        for (int i = 0; i < (grid + 2); i++) {
            g[i][grid + 1].value = 9;
        }

        // counting the surrounding bombs
        for (int i = 1; i < (grid + 1); i++) {
            for (int j = 1; j < (grid + 1); j++) {

                if (g[i][j].value != 10) {

                    int bombsAround = 0;

                    for (int[] spot : surr) {
                        int row = i + spot[0];
                        int col = j + spot[1];

                        if (g[row][col].value == 10) {
                            bombsAround++;
                        }
                    }

                    g[i][j].value = bombsAround;
                }
            }
        }

        return g;
    }

    public void printBoard() {

        for (Location[] row : g) {
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

    public void revealNeighbours(int a, int b) {

        for (int[] spot : surr) {
            int row = a + spot[0];
            int col = b + spot[1];

            if (GameUtils.isValidCell(g, row, col) && !g[row][col].revealed && g[row][col].value == 0) {
                g[row][col].revealed = true;
                revealNeighbours(row, col);
            } else {
                g[row][col].revealed = true;
            }

        }

    }

    public void handleTurn(int x, int y, Scanner myScan) {
        if (g[x][y].value == 10) {
            for (Location[] bombs : g) {
                for (Location bomb : bombs) {
                    if (bomb.value == 10) {
                        bomb.revealed = true;
                    }
                }
            }
            this.gameComplete = true;
            g[x][y].revealed = true;

        } else if (g[x][y].value > 0 && g[x][y].value < 9) {
            g[x][y].revealed = true;
            printBoard();
        } else {

            g[x][y].revealed = true;
            revealNeighbours(x, y);
            printBoard();
        }

        if (gameComplete) {
            historian.setLost(historian.getLost() + 1);
            printBoard();
            System.out.println("˗ˏˋ BOOM ˎˊ˗");
            historian.setLost(historian.getLost() + 1);
            System.out.println("Would you like to play again? (y/n)");
            String answer = myScan.nextLine();
            if (answer.equals("y")) {
                System.out
                        .println(
                                "Choose the size of the grid and the number of bombs by typing two numbers divided by space:");
                String[] scn = GameUtils.handleScan(myScan);
                int grd = Integer.parseInt(scn[0]);
                int bmb = Integer.parseInt(scn[1]);
                resetBoard(grd, bmb);
            } else {
                historian.writeHistory();
            }

        }

        // leaving this here because it's touching the scanner a lot (?)

        checkAllOut();

        if (allOut) {
            this.gameComplete = true;
            historian.setWon(historian.getWon() + 1);
            System.out.println("˗ˏˋ CONGRATULATIONS ˎˊ˗");
            historian.setWon(historian.getWon() + 1);
            System.out.println("Would you like to play again? (y/n)");
            String answer = myScan.nextLine();

            System.out.println(answer);
            if (answer.equals("y")) {
                System.out
                        .println(
                                "Choose the size of the grid and the number of bombs by typing two numbers divided by space:");
                String[] scanned = GameUtils.handleScan(myScan);
                int grdInt = Integer.parseInt(scanned[0]);
                int bmbInt = Integer.parseInt(scanned[1]);
                resetBoard(grdInt, bmbInt);
            } else {
                historian.writeHistory();
            }
        }

    }

    public void checkAllOut() {
        this.allOut = true;
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                if (g[i][j].value >= 0 && g[i][j].value < 9 && !g[i][j].revealed) {
                    this.allOut = false;
                    break;
                }
            }
        }

    }

}
