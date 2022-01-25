package ca.mattlack.stoich;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CompoundComponent implements Comparable<CompoundComponent> {
    private final Map<CompoundComponent, Integer> components = new HashMap<>();

    public Map<CompoundComponent, Integer> getComponents() {
        return components;
    }

    public void addComponent(CompoundComponent component, int amount) {
        if (components.containsKey(component)) {
            // Add.
            components.put(component, components.get(component) + amount);
        } else {
            components.put(component, amount);
        }
    }

    public ElementBag getElementCounts() {
        ElementBag bag = new ElementBag();

        if (this instanceof Element) {
            bag.addElement((Element) this, 1);
            return bag;
        }

        for (CompoundComponent comp : components.keySet()) {
            ElementBag b = comp.getElementCounts();
            b.multiply(components.get(comp));
            bag.add(b);
        }

        return bag;
    }

    public abstract double molarMass();

    public abstract String formula();

    @Override
    public String toString() {
        return formula();
    }

    @Override
    public int compareTo(CompoundComponent o) {
        double molarMass = molarMass();
        if (o instanceof Element && !(this instanceof Element)) {
            return 1;
        } else if (this instanceof Element && !(o instanceof Element)) {
            return -1;
        } else {
            return Double.compare(molarMass, o.molarMass());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CompoundComponent)) {
            return false;
        }
        return ((CompoundComponent) other).getElementCounts().equals(getElementCounts());
    }

    @Override
    public int hashCode() {
        return getElementCounts().hashCode();
    }
}
