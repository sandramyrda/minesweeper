
import java.util.Scanner;

public class Minesweeper {

    public static void main(String[] args) {

        HistoryWriter historian = new HistoryWriter();

        Scanner myScan = new Scanner(System.in);

        Game game = new Game();

        game.greeting();

        game.resetBoard(game, myScan);

        while (!game.getGameComplete()) {

            System.out.println("Choose a location, give me two numbers (1-10) divided by space: ");

            String[] scannedLoc = game.handleScan(myScan);
            int x = Integer.parseInt(scannedLoc[0]);
            int y = Integer.parseInt(scannedLoc[1]);

            Location[][] g = game.getG();

            game.handleTurn(x, y, g, game);

            if (game.getGameComplete()) {
                historian.setLost(historian.getLost() + 1);
                game.printBoard(g);
                System.out.println("˗ˏˋ BOOM ˎˊ˗");
                historian.setLost(historian.getLost() + 1);
                System.out.println("Would you like to play again? (y/n)");
                String answer = myScan.nextLine();
                if (answer.equals("y")) {
                    game.resetBoard(game, myScan);
                } else {
                    historian.writeHistory();
                }

            }

            game.checkAllOut(g, game);

            if (game.getAllOut()) {
                game.setGameComplete(true);
                historian.setWon(historian.getWon() + 1);
                System.out.println("˗ˏˋ CONGRATULATIONS ˎˊ˗");
                historian.setWon(historian.getWon() + 1);
                System.out.println("Would you like to play again? (y/n)");
                String answer = myScan.nextLine();
                // game.resetBoard(game, myScan);
                System.out.println(answer);
                if (answer.equals("y")) {
                    // I was checking if answer == "y" but answer is a String and "y" is a char so
                    // it wasn't working
                    game.resetBoard(game, myScan);
                } else {
                    historian.writeHistory();
                }
            }

        }

    }
}