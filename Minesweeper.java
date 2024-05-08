
import java.util.Scanner;

public class Minesweeper {

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
            } else {
                g[x][y].revealed = true;
                game.printBoard(g);
            }
        }

    }
}