import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day6 {

    private static final String INPUT_FILE = "day6.txt";
    private static final int NUM_DAYS = 256;
    private static List<Integer> allSpawns = new ArrayList<>();
    private static final HashMap<Integer, Long> productionValues = new HashMap<>();

    public static void main(String[] args) throws IOException {
        readInput();
        long totalSpawns = 0;
        for (Integer spawn : allSpawns) {
            if (!productionValues.containsKey(spawn)) {
                long productionValue = getProductionValue(spawn);
                productionValues.put(spawn, productionValue);
            }
            totalSpawns += productionValues.get(spawn);
        }
        System.out.println(totalSpawns);
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            allSpawns = Arrays.stream(br.readLine().split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
    }

    private static long getProductionValue(final int initialValue) {
        int daysRemaining = NUM_DAYS;
        long[] spawns = new long[9];
        spawns[initialValue] = 1;
        while (daysRemaining > 0) {
            long[] newSpawns = new long[9];
            for (int i = spawns.length - 1; i >= 0; i--) {
                long numSpawns = spawns[i];
                if (i == 0){
                    newSpawns[6] += numSpawns;
                    newSpawns[8] += numSpawns;
                } else {
                    newSpawns[i - 1] += numSpawns;
                }
            }
            spawns = newSpawns.clone();
            daysRemaining--;
        }
        return Arrays.stream(spawns).sum();
    }
}
