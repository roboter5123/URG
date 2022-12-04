package utilities;

import entities.Entity;

import java.io.FileNotFoundException;
import java.util.Random;

public class Map {

    private final Space[][] spaces;
    int mapSize;
    final int ROOM_SIZE;
    Random random;

    /**
     * Generates a Map with a space array in size (mapSize * ROOM_SIZE)²
     * @param mapSize The size of the spaces in amount of rooms per side. So that an array of all rooms would be of mapSize² size.
     * @param ROOM_SIZE The size of a single room.
     * @param random Used to determine a random WallPositions file.
     */
    public Map(int mapSize, int ROOM_SIZE, Random random) {

        this.ROOM_SIZE = ROOM_SIZE;
        this.mapSize = mapSize;
        this.random = random;
        spaces = new Space[mapSize * ROOM_SIZE][mapSize * ROOM_SIZE];

        for (int y = 0; y < spaces.length; y++) {

            for (int x = 0; x < spaces[y].length; x++) {

                spaces[y][x] = new Space();
                spaces[y][x].setyPos(y);
                spaces[y][x].setxPos(x);
            }
        }
        setWalls(random);
    }

    /**
     * Sets all the walls on the map.
     * @param random Used to determine a random WallPositions file.
     */
    private void setWalls(Random random) {

        for (int yPos = 0; yPos < mapSize; yPos++) {

            for (int xPos = 0; xPos < mapSize; xPos++) {

                if ((yPos == 0 && xPos == 0) || (yPos == mapSize - 1 && xPos == 0) || (yPos == 0 && xPos == mapSize - 1) || (yPos == mapSize - 1 && xPos == mapSize - 1)) {

                    fillCornerRoom(yPos, xPos, random);

                } else if (xPos == 0 || yPos == 0 || yPos == mapSize - 1 || xPos == mapSize - 1) {

                    fillEdgeRoom(yPos, xPos, random);
                } else {

                    fillCenterRoom(yPos, xPos, random);
                }
            }
        }
    }

    /**
     * Fills a room with type CenterRoom with walls
     * @param yPos Y Position of the room. Should be in the format of 0 to mapSize.
     * @param xPos X Position of the room. Should be in the format of 0 to mapSize.
     * @param random Used to determine a random WallPositions file.
     */
    private void fillCenterRoom(int yPos, int xPos, Random random) {

        try {

            Room room = new Room("CenterRoom");
            room.fillSpaces(this, new int[]{yPos, xPos}, 0, random);
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * Fills a room with type CornerRoom with walls.
     * @param yPos Y Position of the room. Should be in the format of 0 to mapSize.
     * @param xPos X Position of the room. Should be in the format of 0 to mapSize.
     * @param random Used to determine a random WallPositions file.
     */
    private void fillCornerRoom(int yPos, int xPos, Random random) {

        Room room = new Room("CornerRoom");
        int rotation = 0;

        if (yPos == 0 && xPos == mapSize - 1) {

            rotation = 1;
        } else if (yPos == mapSize - 1 && xPos == mapSize - 1) {

            rotation = 2;
        } else if (yPos == mapSize - 1 && xPos == 0) {

            rotation = 3;
        }

        try {

            room.fillSpaces(this, new int[]{yPos, xPos}, rotation, random);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    /**
     * Fills a room with type EdgeRoom with walls.
     * @param yPos Y Position of the room. Should be in the format of 0 to mapSize.
     * @param xPos X Position of the room. Should be in the format of 0 to mapSize.
     * @param random Used to determine a random WallPositions file.
     */
    private void fillEdgeRoom(int yPos, int xPos, Random random) {

        Room room = new Room("EdgeRoom");
        int rotation = 0;

        if (yPos == 0) {

            rotation = 1;
        } else if (xPos == mapSize - 1) {

            rotation = 2;
        } else if (yPos == mapSize - 1) {

            rotation = 3;
        }

        try {

            room.fillSpaces(this, new int[]{yPos, xPos}, rotation, random);
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * @param entity Entity to be placed on the space array.
     */
    public void placeEntity(Entity entity) {

        boolean notPlaced = true;

        while (notPlaced) {

            int xPos = random.nextInt(mapSize * ROOM_SIZE - 1);
            int yPos = random.nextInt(mapSize * ROOM_SIZE - 1);

            if (spaces[yPos][xPos].getEntityOnField() == null) {

                notPlaced = false;

                entity.setxPos(xPos);
                entity.setyPos(yPos);

                spaces[yPos][xPos].setEntityOnField(entity);
            }
        }
    }


    public Space[][] getSpaces() {

        return spaces;
    }

    @Override
    public String toString() {

        Space[][] spaces1 = spaces;

        StringBuilder map = new StringBuilder();

        for (int y = 0; y < spaces1[0].length; y++) {

            map.append("-".repeat(4).repeat(spaces1.length));
            map.append("\n");

            for (int x = 0; x < spaces1[y].length; x++) {

                Space curSpace = spaces1[y][x];
                map.append("|");

                if (curSpace.getEntityOnField() != null) {

                    map.append(curSpace.getEntityOnField().getSprite());
                } else {

                    map.append("   ");
                }
            }
            map.append("|");
            map.append("\n");
        }
        map.append("====".repeat(spaces1[0].length));
        return map.toString();
    }
}
