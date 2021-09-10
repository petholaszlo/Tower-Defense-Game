package logic;

public class Leukocyte extends Tower {

    public Leukocyte() {
        super(1, 1, 1, 100, 10, 500, "Leukocyte");
    }

    public Leukocyte(int[] base) {
        super(1, 1, 1, 100, 10, 500, "Leukocyte", base[0], base[1]);
    }

    @Override
    void specialAttack(Enemy e) {
        e.addDrawback(new BurnMutation());
    }

    @Override
    void modifyBaseStat() {
        this.baseDamage += 5;

    }

}