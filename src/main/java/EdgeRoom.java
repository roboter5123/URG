import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class EdgeRoom {

    public static final int ROOM_SIZE = 7;
    boolean[][] wallPositions;
    public static int permutations = Objects.requireNonNull(new File("rooms/EdgeRooms").list()).length;

    public void fillSpaces(Board board, int[] roomcoord, int rotation) throws FileNotFoundException {

        Space[][] spaces = board.getSpaces();
        boolean[][] wallPositions = getWallPositions(rotation);

        for (int r = 0; r < ROOM_SIZE; r++) {

            for (int c = 0; c < ROOM_SIZE; c++) {

                if (wallPositions[r][c]) {

                    spaces[r + roomcoord[0]][c + roomcoord[1]].setEntityOnField(new Wall());
                }
            }
        }
    }

    public boolean[][] rotate(boolean[][] wallpositions) {

        int M = wallpositions.length;
        int N = wallpositions[0].length;

        boolean[][] ret = new boolean[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = wallpositions[r][c];
            }
        }
        return ret;
    }


    public boolean[][] getWallPositions(int rotation) throws FileNotFoundException {

        wallPositions = new boolean[ROOM_SIZE][ROOM_SIZE];
        Random random = new Random();
        int roomNumber = random.nextInt(permutations);

        File file = new File("rooms/EdgeRooms/edgeRoom" + roomNumber + ".csv");

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

        for (int i = 0; i < rotation; i++) {

            wallPositions = rotate(wallPositions);
        }

        return wallPositions;
    }
}
