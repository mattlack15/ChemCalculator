package ca.mattlack.chem;

public class ValueMass extends ValueElement {
    @Override
    public double next(double current, MolarMass state) {
        return current / state.getMolarMass();
    }

    @Override
    public double prev(double current, MolarMass state) {
        return current;
    }

    @Override
    public String getName() {
        return "Mass";
    }
}
