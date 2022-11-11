import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int round = 1;
    static int boardSize = 21;
    static int playerHealth = 5;
    static int playerMaxHealth = 5;
    static int opponentCount = 8;
    static Board board = new Board(boardSize);
    static int ROOM_SIZE = 7;

    public static void main(String[] args) {

//      places the player at a random space on the board and generates opponents so that no character is on the same space
        initialize();
//      sets the amount of turns the player has. Opponents Always have 1 turn;
        int playerMoves = 2;
        boolean play = true;

        while (play) {
//            lets the player move playerMoves amount of time. checks for a death on each move and if an opponent dies heals the player for amount of health set in checkDeaths
            for (int i = 0; i < playerMoves; i++) {

                drawMap();
                playerMove();
                play = checkForDeaths();

                if (!play) {

                    break;
                }
            }

//            moves opponents if there are still opponents left
            if (play) {

                List<Opponent> opponents = board.getOpponents();

                for (Opponent opponent : opponents) {

                    opponentsMove(opponent);
                    play = checkForDeaths();

                    if (!play) {

                        break;
                    }
                }
            }
            round++;
        }
    }

    public static void initialize() {

        Random random = new Random();
        generateWalls();
        placePlayerOnField(random);
        generateOpponents(random);
    }

    private static void generateWalls() {

        for (int k = 0; k < boardSize; k += ROOM_SIZE) {

            for (int l = 0; l < boardSize; l += ROOM_SIZE) {


                CenterRoom.fillSpaces(board, new int[]{k, l});
            }
        }
    }

    private static void placePlayerOnField(Random random) {

        while (true) {

            Player player = new Player(playerHealth, playerMaxHealth);
            int playerXPos = random.nextInt(boardSize);
            int playerYPos = random.nextInt(boardSize);

            if (board.getSpaces()[playerXPos][playerYPos].getEntityOnField() == null){
                player.setxPos(playerXPos);
                player.setyPos(playerYPos);
                board.getSpaces()[playerXPos][playerYPos].setEntityOnField(player);
                board.setPlayer(player);
                break;
            }
        }
    }

    private static void generateOpponents(Random random) {

        for (int i = 0; i < opponentCount; i++) {

            boolean isInvalidPosition = true;
            Opponent opponent = new Opponent(board.getPlayer(), "orc");

            while (isInvalidPosition) {

                int opponentXPos = random.nextInt(boardSize);
                int opponentYPos = random.nextInt(boardSize);

                if (board.getSpaces()[opponentXPos][opponentYPos].getEntityOnField() == null) {

                    isInvalidPosition = false;
                    opponent.setxPos(opponentXPos);
                    opponent.setyPos(opponentYPos);
                    board.getSpaces()[opponentXPos][opponentYPos].setEntityOnField(opponent);
                    board.getEntities().add(opponent);
                }
            }
        }
    }

    public static void drawMap() {

        System.out.println("\n".repeat(50));
        System.out.println("Untitled Fighting Game by roboter5123");
        System.out.println("Round: " + round + " Highscore: roboter5123 rounds: 40");
        System.out.println(board);
        System.out.println("Your Health " + board.getPlayer().getHealth() + "/" + board.getPlayer().getMaxHealth());
    }

    public static void playerMove() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("What direction do you want to move?\n(w = up / a = left / s = down / d = right)");
        String input = scanner.nextLine();

        switch (input) {
            case "+X", "+x", "d" -> board.getPlayer().move(+1, 'x', board);
            case "-X", "-x", "a" -> board.getPlayer().move(-1, 'x', board);
            case "+Y", "+y", "s" -> board.getPlayer().move(1, 'y', board);
            case "-Y", "-y", "w" -> board.getPlayer().move(-1, 'y', board);
        }

    }

    public static void opponentsMove(Opponent opponent) {

        opponent.calculateMovement(board);
    }

    private static boolean checkForDeaths() {

        if (board.getPlayer().getHealth() < 1) {

            gameOver(false);
            return false;

        } else {

            List<Opponent> opponentsToRemove = new ArrayList<>();

            for (Opponent opponent : board.getOpponents()) {

                if (opponent.getHealth() < 1) {

                    opponentsToRemove.add(opponent);
                }
            }

            board.removeopponents(opponentsToRemove);

            if (board.getOpponents().size() < 1) {

                gameOver(true);
                return false;
            }
        }
        return true;
    }

    public static void gameOver(boolean hasWon) {

        if (hasWon) {
            drawMap();
            System.out.println("Congratulations!You defeated you opponents and won the game!");

        } else {

            System.out.println("You got killed and lost the game!");
        }
    }
}
