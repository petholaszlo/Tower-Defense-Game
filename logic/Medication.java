package logic;

public class Medication extends Tower {

    public Medication() {
        super(2, 2, 4, 200, 40, 1200, "Medication");
    }

    public Medication(int[] base) {
        super(2, 2, 4, 200, 40, 1200, "Medication", base[0], base[1]);
    }

    @Override
    void specialAttack(Enemy e) {
        e.addDrawback(new SlowMutation());
    }

    @Override
    void modifyBaseStat() {
        this.reloadTime -= 1;
    }

}