
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Minesweeper {

    public static void main(String[] args) {
        int won = 0;
        int lost = 0;

        boolean allOut = true;

        Scanner myScan = new Scanner(System.in);

        Game game = new Game();

        game.greeting();
        String size = myScan.nextLine();
        String[] sizeSplit = size.split(" ");
        int gridInt = Integer.parseInt(sizeSplit[0]);
        int bombInt = Integer.parseInt(sizeSplit[1]);

        Location[][] g = game.createBoard(gridInt, bombInt);

        game.printBoard(g);

        System.out.println("Let's look for some bombs");

        while (!game.gameComplete) {

            System.out.println("Choose a location, give me two numbers (1-10) divided by space: ");
            String loc = myScan.nextLine();
            String[] split = loc.split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            if (g[x][y].value == 10) {
                for (Location[] bombs : g) {
                    for (Location bomb : bombs) {
                        if (bomb.value == 10) {
                            bomb.revealed = true;
                        }
                    }
                }
                game.gameComplete = true;
                g[x][y].revealed = true;
                game.printBoard(g);
                lost++;
                System.out.println("˗ˏˋ BOOM ˎˊ˗");
            } else if (g[x][y].value > 0 && g[x][y].value < 9) {
                g[x][y].revealed = true;
                game.printBoard(g);
            } else {

                g[x][y].revealed = true;
                Location.revealNeighbours(g, x, y);
                game.printBoard(g);
            }

            allOut = true; // this check has to happen after the game logic and after the move has been
                           // assessed - not before that.

            for (int i = 0; i < g.length; i++) {
                for (int j = 0; j < g.length; j++) {
                    if (g[i][j].value >= 0 && g[i][j].value < 9 && !g[i][j].revealed) {
                        allOut = false;
                        break;
                    }
                }
            }

            if (allOut) {
                game.gameComplete = true;
                // game.printBoard(g);
                won++;
                System.out.println("˗ˏˋ CONGRATULATIONS ˎˊ˗");
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_count.txt"))) {
            writer.write("Wins: " + won);
            writer.newLine();
            writer.write("Losses: " + lost);
            System.out.println("Counts saved.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}