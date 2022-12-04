package utilities;

import entities.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Room {

    public static final int ROOM_SIZE = 7;
    private boolean[][] wallPositions;
    String roomType;
    private static int permutations;

    public Room(String roomType) {

        this.roomType = roomType;

        permutations = Objects.requireNonNull(new File("rooms/" + roomType).list()).length;
    }

    public void fillSpaces(Map map, int[] roomCoord, int rotation, Random random) throws FileNotFoundException {

        Space[][] spaces = map.getSpaces();
        boolean[][] wallPositions = getWallPositions(rotation, random);

        for (int y = 0; y < ROOM_SIZE; y++) {

            for (int x = 0; x < ROOM_SIZE; x++) {

                if (wallPositions[y][x]) {

                    spaces[y + (roomCoord[0]*ROOM_SIZE)][x + (roomCoord[1]*ROOM_SIZE)].setEntityOnField(new Wall());
                }
            }
        }
    }

    public boolean[][] rotate(boolean[][] wallPositions) {

        int M = wallPositions.length;
        int N = wallPositions[0].length;

        boolean[][] ret = new boolean[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M - 1 - r] = wallPositions[r][c];
            }
        }
        return ret;
    }

    public boolean[][] getWallPositions(int rotation, Random random) throws FileNotFoundException {

        wallPositions = new boolean[ROOM_SIZE][ROOM_SIZE];
        int roomNumber = random.nextInt(permutations);

        File file = new File("rooms/" + roomType + "/" + roomType + roomNumber + ".csv");

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

