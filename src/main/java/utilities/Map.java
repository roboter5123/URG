package utilities;

import entities.Entity;

import java.io.FileNotFoundException;
import java.util.Random;

public class Map {

    private final Space[][] spaces;
    int mapSize;
    final int ROOM_SIZE;
    Random random;

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

    private void setWalls(Random random) {

        for (int k = 0; k < mapSize ; k++) {

            for (int l = 0; l < mapSize; l ++) {

                if ((k == 0 && l == 0) || (k == mapSize - 1 && l == 0) || (k == 0 && l == mapSize - 1) ||(k == mapSize - 1 && l == mapSize - 1)) {

                    fillCornerRoom(k, l, random);

                } else if (l == 0 || k == 0 || k == mapSize -1 || l == mapSize - 1) {

                    fillEdgeRoom(k, l, random);
                } else {

                    try {

                        Room room = new Room("CenterRoom");
                        room.fillSpaces(this, new int[]{k, l}, 0, random);
                    } catch (FileNotFoundException e) {

                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void fillCornerRoom(int k, int l, Random random) {

        Room room = new Room("CornerRoom");
        int rotation = 0;

        if (k == 0 && l == mapSize - 1) {

            rotation = 1;
        } else if (k == mapSize - 1 && l == mapSize - 1) {

            rotation = 2;
        } else if (k == mapSize - 1 && l == 0) {

            rotation = 3;
        }

        try {

            room.fillSpaces(this, new int[]{k, l}, rotation, random);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    private void fillEdgeRoom(int k, int l, Random random) {

        Room room = new Room("EdgeRoom");
        int rotation = 0;

        if (k == 0) {

            rotation = 1;
        } else if (l == mapSize - 1) {

            rotation = 2;
        } else if (k == mapSize - 1) {

            rotation = 3;
        }

        try {

            room.fillSpaces(this, new int[]{k, l}, rotation, random);
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    public Space[][] getSpaces() {

        return spaces;
    }

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
