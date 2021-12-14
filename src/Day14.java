import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class Day14 {

    private static final String INPUT_FILE = "day14.txt";
    private static final HashMap<String, Character> mappings = new HashMap<>();
    private static final HashMap<Character, Long> occurrences = new HashMap<>();
    private static final int NUM_STEPS = 40;
    private static HashMap<String, Long> codePairs = new HashMap<>();

    public static void main(String[] args) throws IOException {
        readInput();
        for (int s = 1; s <= NUM_STEPS; s++) {
            final HashMap<String, Long> newCode = new HashMap<>(codePairs);
            for (String pair : mappings.keySet()) {
                if (codePairs.containsKey(pair)) {
                    final char insert = mappings.get(pair);
                    final long pairCount = codePairs.get(pair);
                    newCode.compute(pair.charAt(0) + "" + insert, (key, val) -> val == null ? pairCount : val + pairCount);
                    newCode.compute(insert + "" + pair.charAt(1), (key, val) -> val == null ? pairCount : val + pairCount);
                    newCode.computeIfPresent(pair, (key, val) -> val - pairCount);
                    occurrences.compute(insert, (key, val) -> val == null ? pairCount : val + pairCount);
                }
            }
            codePairs = new HashMap<>(newCode);
            if (s == 10) {
                System.out.println("Part 1: " + (Collections.max(occurrences.values()) - Collections.min(occurrences.values())));
            }
        }
        System.out.println("Part 2: " + (Collections.max(occurrences.values()) - Collections.min(occurrences.values())));
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            final String code = br.readLine();
            occurrences.put(code.charAt(0), 1L);
            for (int c = 1; c < code.length(); c++) {
                codePairs.compute(code.charAt(c - 1) + "" + code.charAt(c), (key, val) -> val == null ? 1 : val + 1);
                occurrences.compute(code.charAt(c), (key, val) -> val == null ? 1 : val + 1);
            }
            br.readLine();
            String line = br.readLine();
            while (line != null) {
                String[] mapping = line.split(" -> ");
                mappings.put(mapping[0], mapping[1].charAt(0));
                line = br.readLine();
            }
        }
    }
}