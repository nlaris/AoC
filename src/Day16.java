import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day16 {

    private static final String INPUT_FILE = "day16.txt";
    private static String binaryCode = "";
    private static int versionSum;

    public static void main(String[] args) throws IOException {
        readInput();
        Packet p = parsePackets(new Packet(binaryCode));
        System.out.println("Part 1: " + versionSum);
        System.out.println("Part 2: " + p.value);
    }

    private static Packet parsePackets(final Packet packet) {
        final int packetVersion = Integer.parseInt(packet.code.substring(0, 3), 2);
        final int typeId = Integer.parseInt(packet.code.substring(3, 6), 2);
        versionSum += packetVersion;
        if (typeId == 4) {
            String literalValue = "";
            boolean lastGroup = false;
            int i = 6;
            while (i < packet.code.length() && !lastGroup) {
                lastGroup = packet.code.charAt(i) == '0';
                literalValue = literalValue.concat(packet.code.substring(i + 1, i + 5));
                i += 5;
            }
            packet.value = Long.parseLong(literalValue, 2);
            packet.length = i;
            return packet;
        } else {
            if (packet.code.charAt(6) == '0') {
                final int length = Integer.parseInt(packet.code.substring(7, 22), 2);
                for (int index = 22; index < 22 + length;) {
                    Packet p = new Packet(packet.code.substring(index));
                    index = index + parsePackets(p).length;
                    packet.subPackets.add(p);
                }
                setPacketScore(packet, typeId);
                packet.length = 22 + length;
                return packet;
            } else {
                final int numPackets = Integer.parseInt(packet.code.substring(7, 18), 2);
                int index = 18;
                for (int i = 0; i < numPackets; i++) {
                    Packet p = new Packet(packet.code.substring(index));
                    index = index + parsePackets(p).length;
                    packet.subPackets.add(p);
                }
                setPacketScore(packet, typeId);
                packet.length = index;
                return packet;
            }
        }
    }

    private static void setPacketScore(final Packet p, final int typeId) {
        switch (typeId) {
            case 0 -> p.value = p.subPackets.stream().mapToLong(m -> m.value).sum();
            case 1 -> p.value = p.subPackets.stream().mapToLong(m -> m.value).reduce(1, (total, i) -> total * i);
            case 2 -> p.value = p.subPackets.stream().mapToLong(m -> m.value).min().orElse(0);
            case 3 -> p.value = p.subPackets.stream().mapToLong(m -> m.value).max().orElse(0);
            case 5 -> p.value = p.subPackets.get(0).value > p.subPackets.get(1).value ? 1 : 0;
            case 6 -> p.value = p.subPackets.get(0).value < p.subPackets.get(1).value ? 1 : 0;
            case 7 -> p.value = p.subPackets.get(0).value == p.subPackets.get(1).value ? 1 : 0;
        }
    }

    private static void readInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            final String input = br.readLine();
            for (char c : input.toCharArray()) {
                binaryCode = binaryCode.concat(String.format("%4s", Integer.toBinaryString(Character.getNumericValue(c))).replaceAll(" ", "0"));
            }
        }
    }

    private static class Packet {
        String code;
        int length;
        long value;
        ArrayList<Packet> subPackets = new ArrayList<>();

        public Packet(final String code){
            this.code = code;
        }
    }
}
