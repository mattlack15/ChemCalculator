package ca.mattlack.chem;

public class ValueAtoms extends ValueElement {
    @Override
    public double next(double current, MolarMass state) {
        return current;
    }

    @Override
    public double prev(double current, MolarMass state) {
        return current / state.getAtomsPerUnit();
    }

    @Override
    public String getName() {
        return "atoms";
    }
}
