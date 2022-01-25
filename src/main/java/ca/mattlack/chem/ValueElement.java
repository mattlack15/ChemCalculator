package ca.mattlack.chem;

public abstract class ValueElement {
    public abstract double next(double current, MolarMass state);
    public abstract double prev(double current, MolarMass state);
    public abstract String getName();
}
