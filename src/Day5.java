import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Day5 {

    private static final String INPUT_FILE = "day5.txt";
    private static final HashMap<String, Integer> spots = new HashMap<>();
    private static final boolean INCLUDE_DIAGONALS = true;

    public static void main(String[] args) throws IOException {
        readInput();
        System.out.print(spots.entrySet().stream().filter(map -> map.getValue() > 1).toList().size());
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                final String[] coords = line.split(" -> ");
                final Integer[] begin = Arrays.stream(coords[0].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                final Integer[] end = Arrays.stream(coords[1].split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                final int xOffset = end[0].compareTo(begin[0]);
                final int yOffset = end[1].compareTo(begin[1]);
                if (INCLUDE_DIAGONALS || xOffset == 0 || yOffset == 0) {
                    int currentX = begin[0], currentY = begin[1];
                    final String endSpot = buildIndex(end[0], end[1]);
                    String currentSpot = buildIndex(currentX, currentY);
                    addSpot(currentSpot);
                    while (!currentSpot.equals(endSpot)) {
                        currentX += xOffset;
                        currentY += yOffset;
                        currentSpot = buildIndex(currentX, currentY);
                        addSpot(currentSpot);
                    }
                }
                line = br.readLine();
            }
        }
    }

    private static String buildIndex(final int x, final int y) {
        return x + "," + y;
    }

    private static void addSpot(final String spot) {
        spots.compute(spot, (key, val) -> (val == null) ? 1 : val + 1);
    }
}
