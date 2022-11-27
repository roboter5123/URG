package entities;

import utilities.Map;

import java.util.Scanner;

public class Player extends Entity {

    private final int reach = 2;

    public Player(int health, int maxHealth) {

        this.setMaxHealth(maxHealth);
        this.setHealth(health);
        this.setDmg(1);
        this.setSprite(" P ");
    }

    public Player(int maxHealth) {

        this.setMaxHealth(maxHealth);
        this.setHealth(maxHealth);
        this.setDmg(1);
        this.setSprite(" P ");
    }

    public void move(Map map){

        Scanner scanner = new Scanner(System.in);
        System.out.println("What direction do you want to move?\n(w = up / a = left / s = down / d = right)");
        String input = scanner.nextLine();

        switch (input) {
            case "+X", "+x", "d" -> super.move(+1, 'x', map);
            case "-X", "-x", "a" -> super.move(-1, 'x', map);
            case "+Y", "+y", "s" -> super.move(1, 'y', map);
            case "-Y", "-y", "w" -> super.move(-1, 'y', map);
        }
    }

    public int getReach() {

        return reach;
    }
}


