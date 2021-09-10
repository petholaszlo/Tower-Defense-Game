package logic;

public class BurnMutation implements Drawback {

    private int lastlyTriggered;
    private int interval;
    private int counter;

    public BurnMutation() {
        this.lastlyTriggered = 0;
        this.interval = 1 * Configuration.FPS;
        this.counter = 0;
    }

    @Override
    public void beforeFirstOmit(Enemy e) {
        e.takeDamage(2);
    }

    @Override
    public void omit(Enemy e) {
        if (lastlyTriggered < this.interval) {
            this.lastlyTriggered += 1;
            return;
        }

        e.takeDamage(2);
        this.counter++;
        this.lastlyTriggered = 0;
    }

    @Override
    public boolean isOver() {
        return counter >= 5;
    }

    @Override
    public void reset() {
        this.counter = 0;
    }

    @Override
    public void beforeRemove(Enemy e) {
        return;
    }

}