import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 {

    private static final String INPUT_FILE = "day7.txt";
    private static List<Integer> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readInput();
        Collections.sort(inputs);
        int medianIndex = inputs.size() / 2;
        System.out.println("Part 1: " + getPart1PositionScore(inputs.get(medianIndex)));

        float averageInput = inputs.stream().reduce(0, Integer::sum).floatValue() / inputs.size();
        int aveFloorScore = getPart2PositionScore((int)Math.floor(averageInput));
        int aveCeilScore = getPart2PositionScore((int)Math.ceil(averageInput));
        System.out.println("Part 2: " + (Math.min(aveCeilScore, aveFloorScore)));
    }

    private static int getPart1PositionScore(final int position) {
        return inputs.stream().reduce(0, (total, i) -> total + Math.abs(position - i));
    }

    private static int getPart2PositionScore(final int position) {
        return inputs.stream().reduce(0, (total, i) -> total + getDistanceFuel(Math.abs(position - i)));
    }

    private static int getDistanceFuel(final int n) {
        return (n * (n+1) / 2);
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            inputs = Arrays.stream(br.readLine().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
    }
}
