import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class CenterRoom {

    public static final int ROOM_SIZE = 7;

    public static int permutations = Objects.requireNonNull(new File("rooms/CenterRooms").list()).length;
    boolean[][] wallPositions;

    public boolean[][] getWallPositions() throws FileNotFoundException {

        wallPositions = new boolean[ROOM_SIZE][ROOM_SIZE];
        Random random = new Random();
        int roomNumber = random.nextInt(permutations);

        File file = new File("rooms/CenterRooms/centerRoom" + roomNumber + ".csv");

        Scanner scanner = new Scanner(file);
        int counter = 0;

        while (scanner.hasNext()) {

            String line = scanner.nextLine();
            String[] lineSplit = line.split(",");

            for (int i = 0; i < lineSplit.length; i++) {

                if (Objects.equals(lineSplit[i], "x")) {
                    wallPositions[i][counter] = true;
                }
            }
            counter++;
        }

        return wallPositions;
    }


    public void fillSpaces(Board board, int[] roomcoord) throws FileNotFoundException {

        Space[][] spaces = board.getSpaces();
        boolean[][] wallPositions = getWallPositions();

        for (int r = 0; r < ROOM_SIZE; r++) {

            for (int c = 0; c < ROOM_SIZE; c++) {

                if (wallPositions[r][c]) {

                    spaces[r + roomcoord[0]][c + roomcoord[1]].setEntityOnField(new Wall());
                }
            }
        }
    }
}
