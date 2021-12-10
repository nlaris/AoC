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
    private static final HashMap<Character, Integer> braceScoresPt1 = new HashMap<>() {{
        put(')', 3);
        put(']', 57);
        put('}', 1197);
        put('>', 25137);
    }};
    private static final HashMap<Character, Integer> braceScoresPt2 = new HashMap<>() {{
        put(')', 1);
        put(']', 2);
        put('}', 3);
        put('>', 4);
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
                    part1Score += braceScoresPt1.get(c);
                    return;
                }
            }
        }
        // no corrupt characters, but may be incomplete
        long incompleteScore = 0;
        for(int i = expected.size() - 1; i >= 0; i--) {
            incompleteScore = (incompleteScore * 5) + braceScoresPt2.get(expected.get(i));

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
