package logic;

public interface Drawback {
    public void omit(Enemy e);

    public void beforeFirstOmit(Enemy e);

    public void beforeRemove(Enemy e);

    public boolean isOver();

    public void reset();

}