package ca.mattlack.chem;

public class ValueParticles extends ValueElement {

    @Override
    public double next(double current, MolarMass state) {
        return current * state.getAtomsPerUnit();
    }

    @Override
    public double prev(double current, MolarMass state) {
        return current / 6.02E23;
    }

    @Override
    public String getName() {
        return "Particles";
    }
}
