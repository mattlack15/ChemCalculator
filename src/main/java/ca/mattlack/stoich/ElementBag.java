package ca.mattlack.stoich;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ElementBag {
    private final Map<Element, Integer> elementCounts = new HashMap<>();

    public void addElement(Element element, int amount) {
        if (elementCounts.containsKey(element)) {
            elementCounts.put(element, elementCounts.get(element) + amount);
        } else {
            elementCounts.put(element, amount);
        }
    }

    public void removeElement(Element element, int amount) {
        if (elementCounts.containsKey(element)) {
            elementCounts.put(element, elementCounts.get(element) - amount);
        }
    }

    public int getElementCount(Element element) {
        return elementCounts.getOrDefault(element, 0);
    }

    public Map<Element, Integer> getElementCounts() {
        return elementCounts;
    }

    public void add(ElementBag other) {
        for (Element element : other.elementCounts.keySet()) {
            addElement(element, other.getElementCount(element));
        }
    }

    public void subtract(ElementBag other) {
        for (Element element : other.elementCounts.keySet()) {
            removeElement(element, other.getElementCount(element));
        }
    }

    public void multiply(int factor) {
        elementCounts.replaceAll((e, v) -> elementCounts.get(e) * factor);
    }

    public boolean isZero() {
        for (Element element : elementCounts.keySet()) {
            if (elementCounts.get(element) != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object bag) {
        if (!(bag instanceof ElementBag)) {
            return false;
        }

        for (Element element : elementCounts.keySet()) {
            if (elementCounts.get(element) != ((ElementBag) bag).getElementCount(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementCounts);
    }
}
