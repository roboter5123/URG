public class CenterRoom{

    public static final int ROOM_SIZE = 7;

    public static boolean[][] getWallPositions() {

        boolean[][] wallPositions = new boolean[ROOM_SIZE][ROOM_SIZE];

        for (int i = 0; i < wallPositions.length; i++) {

            if (i > 1 && i < 5) {

                continue;
            }

            for (int j = 0; j < wallPositions.length; j++) {

                if ((i == 0 || i == 6) && (j == 0 || j == 1 || j == 5 || j == 6)) {

                    wallPositions[i][j] = true;

                } else if ((i == 1 || i == 5 || i == 6) && (j == 0 || j == 6)) {

                    wallPositions[i][j] = true;

                }
            }
        }

        return wallPositions;
    }


    public static void fillSpaces(Board board, int[] roomcoord) {

        Space[][] spaces = board.getSpaces();
        boolean[][] wallPositions = getWallPositions();

        for (int i = 0; i < ROOM_SIZE; i++) {

            for (int j = 0; j < ROOM_SIZE; j++) {

                if (wallPositions[i][j]) {

                    spaces[i + roomcoord[0]][j + roomcoord[1]].setEntityOnField(new Wall());
                }
            }
        }
    }
}
