package ca.mattlack.stoich;

import java.util.*;

public class Element extends CompoundComponent {

    private String symbol;
    private double molarMass;

    public Element(String symbol, double molarMass) {
        this.symbol = symbol;
        this.molarMass = molarMass;
    }

    @Override
    public double molarMass() {
        return molarMass;
    }

    public String formula() {
        return symbol;
    }

    @Override
    public Map<CompoundComponent, Integer> getComponents() {
        return new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Element element = (Element) o;
        return Objects.equals(symbol, element.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
