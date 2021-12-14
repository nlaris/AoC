import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day4 {

    private static final String INPUT_FILE = "day4.txt";
    private static final List<Board> boards = new ArrayList<>();
    private static List<Integer> turns = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readInput();
        boolean winnerFound = false;
        for (int turn : turns) {
            for (int b = 0; b < boards.size(); b++) {
                Board board = boards.get(b);
                int boardScore = board.checkTurn(turn);
                if (boardScore > 0) {
                    if (!winnerFound) {
                        winnerFound = true;
                        System.out.println("Part 1: " + boardScore * turn);
                    }
                    if (boards.size() == 1) {
                        System.out.println("Part 2: " + boardScore * turn);
                    }
                    boards.remove(b);
                    b--;
                }
            }
        }
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            turns = Arrays.stream(line.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            br.readLine();
            line = br.readLine();
            while (line != null) {
                final Board board = new Board();
                for (int i = 0; i < 5; i++) {
                    board.addRow(i, Arrays.stream(line.split(" ")).filter(x -> !x.isEmpty()).toArray(String[]::new));
                    line = br.readLine();
                }
                boards.add(board);
                line = br.readLine();
            }
        }
    }

    private static class Board {

        private final int[][] tiles = new int[6][6];

        public void addRow(int row, String[] numbers) {
            for (int i = 0; i < numbers.length; i++) {
                tiles[row][i] = Integer.parseInt(numbers[i]);
            }
        }

        public int checkTurn(int num) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (tiles[i][j] == num) {
                        tiles[i][j] = -1;
                        tiles[i][5]++;
                        tiles[5][j]++;
                        if (tiles[i][5] == 5 || tiles[5][j] == 5) {
                            return getUnmarkedNumSum();
                        }
                        return 0;
                    }
                }
            }
            return 0;
        }

        private int getUnmarkedNumSum() {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (tiles[i][j] > -1) {
                        sum += tiles[i][j];
                    }
                }
            }
            return sum;
        }
    }
}
