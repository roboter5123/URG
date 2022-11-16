public class CornerRoom {

    public static final int ROOM_SIZE = 7;
    boolean[][] wallPositions;

    public void fillSpaces(Board board, int[] roomcoord, int rotation) {

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

    public void rotate(boolean[][] wallpositions){

        final int M = wallpositions.length;
        final int N = wallpositions[0].length;
        boolean[][] ret = new boolean[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = wallpositions[r][c];
            }
        }

    }



    public boolean[][] getWallPositions(int rotation) {
        //        TODO Switch this for a read of a random centerRoomtemplate file

        wallPositions = new boolean[][]{
                {true, true, true, true, true, true, true},
                {true, false, false, false, false, false, true},
                {true, false, false, false, false, false, false},
                {true, false, false, false, false, false, false},
                {true, false, false, false, false, false, false},
                {true, false, false, false, false, false, true},
                {true, true, false, false, false, true, true},
        };

        for (int i = 0; i <= rotation; i++) {

            rotate(wallPositions);
        }

        return wallPositions;
    }
}