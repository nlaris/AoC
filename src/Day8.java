import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

    private static final String INPUT_FILE = "day8.txt";
    private static final List<String> numberInputs = new ArrayList<>();
    private static final List<String> numberOutputs = new ArrayList<>();
    private static final Map<String, Integer> NUMBER_MAPPINGS  = new HashMap<>() {{
        put("abcefg", 0);
        put("cf", 1);
        put("acdeg", 2);
        put("acdfg", 3);
        put("bcdf", 4);
        put("abdfg", 5);
        put("abdefg", 6);
        put("acf", 7);
        put("abcdefg", 8);
        put("abcdfg", 9);
    }};
    private static final Map<Integer, Character> NUM_OCCURENCES = new HashMap<>() {{
        put(4, 'e');
        put(6, 'b');
        put(9, 'f');
    }};

    public static void main(String[] args) throws IOException {
        readInput();
        System.out.println("Part 1: " + numberOutputs.stream()
                .flatMap(output -> Arrays.stream(output.split(" ")))
                .filter(o -> o.length() <= 4 || o.length() == 7)
                .count());

        int part2Total = 0;
        for (int i = 0; i < numberInputs.size(); i++) {
            String[] inputs = Arrays.stream(numberInputs.get(i).split(" ")).sorted(new SortByStringSize()).toArray(String[]::new);
            String[] outputs = numberOutputs.get(i).split(" ");
            String joinedInputs = String.join("", inputs);
            final Map<Character, Character> solution = new HashMap<>();

            // deduce each letter's real value
            for (char c = 'a'; c <= 'g'; c++) {
                char finalC = c;
                int letterCount = (int) joinedInputs.chars().filter(ch -> ch == finalC).count();
                Character mapping = NUM_OCCURENCES.get(letterCount);
                if (mapping != null) {
                    solution.put(c, mapping);
                } else {
                    if (letterCount == 7) {
                        // inputs[2] represents the digital `4`
                        if (!inputs[2].contains(String.valueOf(c))) {
                            solution.put(c, 'g');
                        } else {
                            solution.put(c, 'd');
                        }
                    } else {
                        // inputs[0] represents the digital `1`
                        if (!inputs[0].contains(String.valueOf(c))) {
                            solution.put(c, 'a');
                        } else {
                            solution.put(c, 'c');
                        }
                    }
                }
            }

            // build output for this row
            StringBuilder result = new StringBuilder();
            for (String output : outputs) {
                StringBuilder actualOutput = new StringBuilder();
                for (char c : output.toCharArray()) {
                    actualOutput.append(solution.get(c));
                }
                String sortedValue = Stream.of(actualOutput.toString().split("")).sorted().collect(Collectors.joining());
                result.append(NUMBER_MAPPINGS.get(sortedValue));
            }
            part2Total += Integer.parseInt(result.toString());
        }
        System.out.println("Part 2: " + part2Total);
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                final String[] displays = line.split(" \\| ");
                numberInputs.add(displays[0]);
                numberOutputs.add(displays[1]);
                line = br.readLine();
            }
        }
    }

    static class SortByStringSize implements Comparator<String> {
        public int compare(String a, String b)
        {
            return a.length() - b.length();
        }
    }
}
