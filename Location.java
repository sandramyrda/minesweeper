public class Location {
    int value = 0;
    boolean revealed = false;

    public Location(int value) {
        value = this.value;
    }

    public Location() {
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static boolean isValidCell(Location[][] arr, int row, int col) {
        return row >= 1 && row < (arr.length - 1) && col >= 1 && col < (arr[0].length - 1);
    }

    public static void revealNeighbours(Location[][] arr, int a, int b) {

        int[][] surr = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1,
                -1 }, { 1, 0 },
                { 1, 1 } };

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

}
