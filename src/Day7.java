import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    private static final String INPUT_FILE = "day7.txt";
    private static final HashMap<Integer, Integer> distanceScores = new HashMap<>();
    private static List<Integer> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readInput();
        Collections.sort(inputs);
        int medianIndex = inputs.size() / 2;
        System.out.println("Part 1: " + getPart1PositionScore(inputs.get(medianIndex)));

        // start at the average position
        float inputSum = inputs.stream().reduce(0, Integer::sum);
        int currentPosition = Math.round(inputSum / inputs.size());
        int currentScore = getPart2PositionScore(currentPosition);
        final int direction = getPart2PositionScore(currentPosition + 1) < currentScore ? 1 :
                getPart2PositionScore(currentPosition - 1) < currentScore ? -1 : 0;

        if (direction != 0) {
            int newScore = currentScore - 1;
            while (newScore < currentScore) {
                currentScore = newScore;
                currentPosition += direction;
                newScore = getPart2PositionScore(currentPosition);
            }
        }
        System.out.println("Part 2: " + currentScore);
    }

    private static int getPart1PositionScore(final int position) {
        return inputs.stream().reduce(0, (total, i) -> total + Math.abs(position - i));
    }

    private static int getPart2PositionScore(final int position) {
        return inputs.stream().reduce(0, (total, i) -> total + getDistanceFuel(Math.abs(position - i)));
    }

    private static int getDistanceFuel(final int n) {
        if (n <= 1)
            return n;
        if (!distanceScores.containsKey(n)) {
            final int score = n + getDistanceFuel(n - 1);
            distanceScores.put(n, score);
        }
        return distanceScores.get(n);
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            inputs = Arrays.stream(br.readLine().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
    }
}
