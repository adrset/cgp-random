package cgp.lib.simulation;

public interface ISimulation<T> {
    public void run();
    public double evaluate();
    public void init();
}
