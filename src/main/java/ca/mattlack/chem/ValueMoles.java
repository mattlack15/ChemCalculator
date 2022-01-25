package ca.mattlack.chem;

public class ValueMoles extends ValueElement {
    @Override
    public double next(double current, MolarMass state) {
        return current * 6.02E23;
    }

    @Override
    public double prev(double current, MolarMass state) {
        return current * state.getMolarMass();
    }

    @Override
    public String getName() {
        return "Moles";
    }
}
