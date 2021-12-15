import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Day15 {

    private static final String INPUT_FILE = "day15.txt";
    private static Spot[][] map;
    private static int mapWidth;
    private static int mapLength;

    public static void main(String[] args) throws IOException {
        readInput(false);
        System.out.println("Part 1: " + getShortestPath());
        readInput(true);
        System.out.println("Part 2: " + getShortestPath());
    }

    private static int getShortestPath() {
        final PriorityQueue<Spot> pq = new PriorityQueue<>();
        pq.add(map[0][0]);
        int bestScore = -1;
        while (pq.size() > 0) {
            final Spot currentSpot = pq.remove();
            if (bestScore == -1 || currentSpot.score < bestScore) {
                if (currentSpot.r == mapLength - 1 && currentSpot.c == mapWidth - 1) {
                    bestScore = currentSpot.totalScore;
                } else {
                    if (currentSpot.r > 0) checkScore(currentSpot.r - 1, currentSpot.c, currentSpot.totalScore, pq);
                    if (currentSpot.c > 0) checkScore(currentSpot.r, currentSpot.c - 1, currentSpot.totalScore, pq);
                    if (currentSpot.r < mapLength - 1) checkScore(currentSpot.r + 1, currentSpot.c, currentSpot.totalScore, pq);
                    if (currentSpot.c < mapWidth - 1) checkScore(currentSpot.r, currentSpot.c + 1, currentSpot.totalScore, pq);
                }
            }
        }
        return bestScore;
    }

    private static void checkScore(final int r, final int c, final int totalScore, final PriorityQueue<Spot> pq) {
        if (map[r][c].totalScore == 0 || map[r][c].totalScore > totalScore + map[r][c].score) {
            map[r][c].totalScore = totalScore + map[r][c].score;
            pq.add(map[r][c]);
        }
    }

    private static void readInput(final boolean part2) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            br.mark(1000000);
            mapLength = (int) br.lines().count();
            br.reset();
            String line = br.readLine();
            mapWidth = line.length();
            if (part2) {
                map = new Spot[mapLength * 5][mapWidth * 5];
                for (int row = 0; row < mapLength; row++) {
                    for (int col = 0; col < mapWidth; col++) {
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                final int score = ((Integer.parseInt(line.split("")[col]) + i + j - 1) % 9) + 1;
                                map[row + (i * mapLength)][col + (j * mapWidth)] = new Spot(row + (i * mapLength), col + (j * mapWidth), score);
                                map[row + (j * mapLength)][col + (i * mapWidth)] = new Spot(row + (j * mapLength), col + (i * mapWidth), score);
                            }
                        }
                    }
                    line = br.readLine();
                }
                mapWidth *= 5;
                mapLength *= 5;
            } else {
                map = new Spot[mapLength][mapWidth];
                for (int row = 0; row < mapLength; row++) {
                    for (int col = 0; col < mapWidth; col++) {
                        map[row][col] = new Spot(row, col, Integer.parseInt(line.split("")[col]));
                    }
                    line = br.readLine();
                }
            }
            map[0][0].score = 0;
        }
    }

    private static class Spot implements Comparable<Spot> {

        int r;
        int c;
        int score;
        int totalScore;

        public Spot(final int r, final int c, final int score) {
            this.r = r;
            this.c = c;
            this.score = score;
        }

        @Override
        public int compareTo(Spot other) {
            return Integer.compare(this.score, other.score);
        }
    }
}