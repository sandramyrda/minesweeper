import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import testing.Loc;

public class Minesweeper {

    public static void main(String[] args) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_PURPLE = "\u001B[35m";

        Scanner myScan = new Scanner(System.in);

        Location[][] board = new Location[12][12];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new Location();
            }
        }

        Random rand = new Random();

        // dropping 10 random bombs on the board (not on the borders)
        for (int i = 0; i <= 10; i++) {
            int rand_a = rand.nextInt(9) + 1;
            int rand_b = rand.nextInt(9) + 1;

            board[rand_a][rand_b].value = 8;
        }

        // drawing the borders of the board
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

        // assigning the number of bombs around a space to each element of the board
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {

                if (board[i][j].value != 8) {

                    int bombsAround = 0;

                    // implement: HAS TO BE A WAY TO SHORTEN THIS CODE
                    boolean con1 = (board[i - 1][j - 1].value == 8);
                    boolean con2 = (board[i - 1][j].value == 8);
                    boolean con3 = (board[i - 1][j + 1].value == 8);
                    boolean con4 = (board[i][j - 1].value == 8);
                    boolean con5 = (board[i][j + 1].value == 8);
                    boolean con6 = (board[i + 1][j - 1].value == 8);
                    boolean con7 = (board[i + 1][j].value == 8);
                    boolean con8 = (board[i + 1][j + 1].value == 8);

                    if (con1) {
                        bombsAround++;
                    }
                    if (con2) {
                        bombsAround++;
                    }
                    if (con3) {
                        bombsAround++;
                    }
                    if (con4) {
                        bombsAround++;
                    }
                    if (con5) {
                        bombsAround++;
                    }
                    if (con6) {
                        bombsAround++;
                    }
                    if (con7) {
                        bombsAround++;
                    }
                    if (con8) {
                        bombsAround++;
                    }

                    board[i][j].value = bombsAround;
                }
            }
        }

        // board[4][5].revealed = true;

        // printing the whole board to the console depending on the values
        for (Location[] row : board) {
            for (Location num : row) {
                if (num.revealed == true && num.value == 8) {
                    System.out.print(num.value + " ");
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
                } else {
                    System.out.print("■ ");
                }

            }
            System.out.println();
        }

        System.out.println("Welcome to Minesweeper");
        System.out.println("Let's look for some bombs");
        System.out.println("Choose a location, give me two numbers (1-10) divided by space: ");
        String loc = myScan.nextLine();
        String[] split = loc.split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        if (board[x][y].value == 8) {
            System.out.println("˗ˏˋ BOOM ˎˊ˗");
        } else {
            board[x][y].revealed = true;
            for (Location[] row : board) {
                for (Location num : row) {
                    if (num.revealed == true && num.value == 8) {
                        System.out.print(num.value + " ");
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
                    } else {
                        System.out.print("■ ");
                    }

                }
                System.out.println();
            }
        }

    }
}
