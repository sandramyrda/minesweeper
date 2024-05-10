import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HistoryWriter {
    private int won = 0;
    private int lost = 0;

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public int getWon() {
        return won;
    }

    public void writeHistory() {
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
