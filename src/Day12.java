import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day12 {

    private static final String INPUT_FILE = "day12.txt";
    private static final HashMap<String, ArrayList<String>> mappings = new HashMap<>();

    public static void main(String[] args) throws IOException {
        readInput();
        System.out.println("Part 1: " + checkPaths("start", new HashMap<>(), 1));
        System.out.println("Part 2: " + checkPaths("start", new HashMap<>(), 2));
    }

    private static int checkPaths(final String p,
                                   final HashMap<String,Integer> smallCavesChecked,
                                   final int smallCaveLimit) {
        if (p.equals("end")) {
            return 1;
        }
        if (Character.isLowerCase(p.charAt(0))) {
            if (smallCavesChecked.values().stream().anyMatch(x -> x == smallCaveLimit) && smallCavesChecked.containsKey(p)) {
                return 0;
            }
            smallCavesChecked.compute(p, (key, val) -> (val == null) ? 1 : val + 1);
        }
        return mappings.get(p).stream().mapToInt(x -> checkPaths(x, new HashMap<>(smallCavesChecked),smallCaveLimit)).sum();
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = br.readLine();
            while (line != null) {
                final String[] points = line.split("-");
                addMapping(points[0], points[1]);
                addMapping(points[1], points[0]);
                line = br.readLine();
            }
        }
    }

    private static void addMapping(final String from, final String to) {
        if (to.equals("start")) return;
        ArrayList<String> toPoints = mappings.get(from);
        if (toPoints == null) {
            toPoints = new ArrayList<>();
        }
        toPoints.add(to);
        mappings.put(from, toPoints);
    }
}
