import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day11 {

    private static final String INPUT_FILE = "day11.txt";
    private static final ArrayList<int[]> inputs = new ArrayList<>();
    private static final ArrayList<String> flashed = new ArrayList<>();
    private static final int NUM_STEPS = 100;
    private static int stepNumber = 1;
    private static int totalFlashes = 0;

    public static void main(String[] args) throws IOException {
        readInput();
        while (takeStep()) {
            stepNumber++;
        }
        System.out.println("Part 1: " + totalFlashes);
        System.out.println("Part 2: " + stepNumber);
    }

    private static boolean takeStep() {
        for (int row = 0; row < inputs.size(); row++) {
            for (int col = 0; col < inputs.get(row).length; col++) {
                increase(row, col);
            }
        }
        final boolean allFlashed = flashed.size() == inputs.size() * inputs.get(0).length;
        flashed.clear();
        return !allFlashed;
    }

    private static void increase(final int row, final int col) {
        if (flashed.contains(row + "" + col))
            return;
        inputs.get(row)[col] = (inputs.get(row)[col] + 1) % 10;
        if (inputs.get(row)[col] == 0) {
            flashed.add(row + "" + col);
            if (stepNumber <= NUM_STEPS) totalFlashes++;
            for (int r = Math.max(row - 1, 0); r <= Math.min(row + 1, inputs.size() - 1); r++) {
                for (int c = Math.max(col - 1, 0); c <= Math.min(col + 1, inputs.get(0).length - 1); c++) {
                    increase(r, c);
                }
            }
        }
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                inputs.add(Arrays.stream(line.split("")).mapToInt(Integer::parseInt).toArray());
                line = br.readLine();
            }
        }
    }
}
