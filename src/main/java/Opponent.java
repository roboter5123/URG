public class Opponent extends Entity {

    private Ai ai;
    private int reach;
    public String race;



    public Opponent() {}

    public Opponent(Player player, String type) {

        if (type == "orc") {

            this.ai = new Orc(player, this);
            this.setSprite("Orc");
        }

        this.setMaxHealth(ai.getMaxHealth());
        this.setHealth(this.getMaxHealth());
        this.setDmg(ai.getDmg());
        this.setReach(ai.getReach());

    }

    public void calculateMovement(Board board) {

        this.ai.calculateMovementDirection(board);

    }

    public Ai getAi() {
        return ai;
    }

    public void setAi(Ai ai) {
        this.ai = ai;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}