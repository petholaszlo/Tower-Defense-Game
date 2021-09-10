package logic;

public class Alcohol extends Tower {

    public Alcohol() {
        super(1, 1, 2, 100, 20, 850, "Alcohol");
    }

    public Alcohol(int[] base) {
        super(1, 1, 2, 100, 20, 850, "Alcohol", base[0], base[1]);
    }

    @Override
    void specialAttack(Enemy e) {
        e.addDrawback(new WeakMutation());
    }

    @Override
    void modifyBaseStat() {
        this.range += 50;
    }
}