
import java.util.Scanner;

public class Minesweeper {

    public static void main(String[] args) {

        // where should the historian be? game or minesweeper?

        Scanner myScan = new Scanner(System.in);

        Game game = new Game();

        System.out.println("\u001B[36m" + "Welcome to Minesweeper" + "\u001B[0m");

        System.out
                .println("Choose the size of the grid and the number of bombs by typing two numbers divided by space:");
        String[] scannedSize = GameUtils.handleScan(myScan);
        int gridInt = Integer.parseInt(scannedSize[0]);
        int bombInt = Integer.parseInt(scannedSize[1]);

        game.resetBoard(gridInt, bombInt);

        while (!game.getGameComplete()) {

            System.out.println("Choose a location, give me two numbers (1-10) divided by space: ");

            String[] scannedLoc = GameUtils.handleScan(myScan);
            int x = Integer.parseInt(scannedLoc[0]);
            int y = Integer.parseInt(scannedLoc[1]);

            // I brought the below checks into the handleTurn function but now that function
            // is handling Scanner within Game.java 🥲
            game.handleTurn(x, y, myScan);

            // if (game.getGameComplete()) {
            // historian.setLost(historian.getLost() + 1);
            // game.printBoard();
            // System.out.println("˗ˏˋ BOOM ˎˊ˗");
            // historian.setLost(historian.getLost() + 1);
            // System.out.println("Would you like to play again? (y/n)");
            // String answer = myScan.nextLine();
            // if (answer.equals("y")) {
            // System.out
            // .println(
            // "Choose the size of the grid and the number of bombs by typing two numbers
            // divided by space:");
            // String[] scn = GameUtils.handleScan(myScan);
            // int grd = Integer.parseInt(scn[0]);
            // int bmb = Integer.parseInt(scn[1]);
            // game.resetBoard(grd, bmb);
            // } else {
            // historian.writeHistory();
            // }

            // }

            // // leaving this here because it's touching the scanner a lot (?)

            // game.checkAllOut();

            // if (game.getAllOut()) {
            // game.setGameComplete(true);
            // historian.setWon(historian.getWon() + 1);
            // System.out.println("˗ˏˋ CONGRATULATIONS ˎˊ˗");
            // historian.setWon(historian.getWon() + 1);
            // System.out.println("Would you like to play again? (y/n)");
            // String answer = myScan.nextLine();

            // System.out.println(answer);
            // if (answer.equals("y")) {
            // System.out
            // .println(
            // "Choose the size of the grid and the number of bombs by typing two numbers
            // divided by space:");
            // String[] scanned = GameUtils.handleScan(myScan);
            // int grdInt = Integer.parseInt(scanned[0]);
            // int bmbInt = Integer.parseInt(scanned[1]);
            // game.resetBoard(grdInt, bmbInt);
            // } else {
            // historian.writeHistory();
            // }
            // }

        }

    }
}