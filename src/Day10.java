import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day10 {

    private static final String INPUT_FILE = "day10.txt";
    private static final HashMap<Character, Character> braceSets = new HashMap<>() {{
        put('<', '>');
        put('[', ']');
        put('(', ')');
        put('{', '}');
    }};
    private static final HashMap<Character, int[]> braceScores = new HashMap<>() {{
        put(')', new int[]{3, 1});
        put(']', new int[]{57, 2});
        put('}', new int[]{1197, 3});
        put('>', new int[]{25137, 4});
    }};
    private static final ArrayList<Long> incompleteRowScores = new ArrayList<>();
    private static int part1Score = 0;

    public static void main(String[] args) throws IOException {
        readInput();
        Collections.sort(incompleteRowScores);
        System.out.println("Part 1: " + part1Score);
        System.out.println("Part 2: " + incompleteRowScores.get(incompleteRowScores.size()/2));
    }

    private static void parseLine(final String line) {
        final ArrayList<Character> expected = new ArrayList<>();
        for (char c : line.toCharArray()) {
            if (braceSets.containsKey(c)) {
                expected.add(braceSets.get(c));
            } else {
                if (expected.get(expected.size() - 1).equals(c)) {
                    expected.remove(expected.size() - 1);
                } else {
                    // found a corrupt character
                    part1Score += braceScores.get(c)[0];
                    return;
                }
            }
        }
        // no corrupt characters, but may be incomplete
        long incompleteScore = 0;
        for(int i = expected.size() - 1; i >= 0; i--) {
            incompleteScore = (incompleteScore * 5) + braceScores.get(expected.get(i))[1];

        }
        incompleteRowScores.add(incompleteScore);
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                parseLine(line);
                line = br.readLine();
            }
        }
    }
}
