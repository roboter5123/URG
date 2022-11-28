import utilities.Level;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        Level level = init();
        level.gameLoop();
    }

    public static Level init() {

        Scanner sc = new Scanner(System.in);
        int mapSize = 1;

        boolean validInput = false;
        do {
            System.out.println("How big should the map be? recommended: 3");

            try {

                mapSize = Integer.parseInt(sc.nextLine());

                if (mapSize > 0) {

                    validInput = true;
                }
            } catch (NumberFormatException ignored) {
            }

            if (!validInput) {

                System.out.println("Please enter a valid number larger than 0.");
            }
        } while (!validInput);

        return new Level(mapSize, mapSize * 2 + 1, mapSize * 2);
    }
}
