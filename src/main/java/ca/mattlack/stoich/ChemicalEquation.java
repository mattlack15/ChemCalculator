package ca.mattlack.stoich;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChemicalEquation {
    private final Map<CompoundComponent, Integer> reactants = new HashMap<>();
    private final Map<CompoundComponent, Integer> products = new HashMap<>();

    public boolean isBalanced() {
        ElementBag reactantBag = new ElementBag();
        for (CompoundComponent component : reactants.keySet()) {
            ElementBag other = component.getElementCounts();
            other.multiply(reactants.get(component));
            reactantBag.add(other);
        }

        ElementBag productBag = new ElementBag();
        for (CompoundComponent component : products.keySet()) {
            ElementBag other = component.getElementCounts();
            other.multiply(products.get(component));
            productBag.add(other);
        }

        productBag.subtract(reactantBag);
        return productBag.isZero();
    }

    public void addReactant(CompoundComponent component, int count) {
        reactants.put(component, count);
    }

    public void addProduct(CompoundComponent component, int count) {
        products.put(component, count);
    }

    public Map<CompoundComponent, Integer> getReactants() {
        return reactants;
    }

    public Map<CompoundComponent, Integer> getProducts() {
        return products;
    }

    public Map<CompoundComponent, Double> calculateMolesNeeded(CompoundComponent component, double moles) {
        if (!reactants.containsKey(component) && !products.containsKey(component)) {
            throw new IllegalArgumentException("Component not in equation");
        }

        int amount;
        if (reactants.containsKey(component)) {
            amount = reactants.get(component);
        } else {
            amount = products.get(component);
        }

        double ratio = moles / (double) amount;

        Map<CompoundComponent, Double> result = new HashMap<>();
        for (CompoundComponent other : reactants.keySet()) {
            result.put(other, reactants.get(other) * ratio);
        }
        for (CompoundComponent other : products.keySet()) {
            result.put(other, products.get(other) * ratio);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (CompoundComponent component : reactants.keySet()) {
            if (reactants.get(component) > 1) {
                builder.append(reactants.get(component));
            }
            builder.append(component.formula());
            builder.append(" + ");
        }
        builder.delete(builder.length() - 3, builder.length());
        builder.append(" -> ");
        for (CompoundComponent component : products.keySet()) {
            if (products.get(component) > 1) {
                builder.append(products.get(component));
            }
            builder.append(component.formula());
            builder.append(" + ");
        }
        builder.delete(builder.length() - 3, builder.length());

        return builder.toString();
    }

    public static ChemicalEquation parse(String text) {
        // Form: "2reactant + 3reactant -> product + 7product"
        String[] parts = text.split("->");
        String[] reactants = parts[0].split("\\+");
        String[] products = parts[1].split("\\+");

        ChemicalEquation equation = new ChemicalEquation();
        for (String reactant : reactants) {

            reactant = reactant.trim();

            // Get the amount, only if there is a number at the start.
            int amount = 1;
            Matcher matcher = Pattern.compile("^\\d+").matcher(reactant);
            if (matcher.find()) {
                amount = Integer.parseInt(matcher.group());
            }
            // Get the compound name.
            String name = reactant.replaceAll("^\\d+", "").trim();
            equation.addReactant(ElementTable.parseComponent(name), amount);
        }
        for (String product : products) {

            product = product.trim();

            // Get the amount, only if there is a number at the start.
            int amount = 1;
            Matcher matcher = Pattern.compile("^\\d+").matcher(product);
            if (matcher.find()) {
                amount = Integer.parseInt(matcher.group());
            }
            // Get the compound name.
            String name = product.replaceAll("^\\d+", "").trim();
            equation.addProduct(ElementTable.parseComponent(name), amount);
        }

        return equation;
    }
}
