package cgp.simulation.individual;

import cgp.simulation.ICloneable;

public interface IIndividual extends ICloneable {
    void init();
    double evaluate();}
