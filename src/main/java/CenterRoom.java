public class CenterRoom{

    public static final int ROOM_SIZE = 7;
    boolean[][] wallPositions;
    public boolean[][] getWallPositions() {

//        TODO Switch this for a read of a random centerRoomtemplate file

        wallPositions = new boolean[][]{
                {true,true,false,false,false,true,true},
                {true,false,false,false,false,false,true},
                {false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false},
                {false,false,false,false,false,false,false},
                {true,false,false,false,false,false,true},
                {true,true,false,false,false,true,true},
                };

        return wallPositions;
    }


    public void fillSpaces(Board board, int[] roomcoord) {

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
