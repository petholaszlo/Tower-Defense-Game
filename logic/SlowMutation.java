package logic;

public class SlowMutation implements Drawback {

    private int lastlyTriggered;
    private int interval;

    public SlowMutation() {
        this.interval = 5 * Configuration.FPS;
        this.lastlyTriggered = 0;
    }

    @Override
    public void beforeFirstOmit(Enemy e) {
        e.setSpeedModifier(0.5f);
    }

    @Override
    public void omit(Enemy e) {
        this.lastlyTriggered += 1;
    }

    @Override
    public boolean isOver() {
        return lastlyTriggered >= this.interval;
    }

    @Override
    public void reset() {
        this.lastlyTriggered = 0;
    }

    @Override
    public void beforeRemove(Enemy e) {
        e.setSpeedModifier(1);
    }

}