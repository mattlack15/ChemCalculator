package ca.mattlack.stoich;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ElementTable.load();

        // Test parsing compounds.
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter chemical equation, compound, or 'x' to exit:");
            String formula = scanner.nextLine();
            if (formula.equalsIgnoreCase("x")) return;
            if (!formula.contains("->")) {
                CompoundComponent component = ElementTable.parseComponent(formula);
                System.out.println("Compound: " + component);
                System.out.println("Molar mass: " + String.format("%.2f", component.molarMass()));
                continue;
            }
            ChemicalEquation equation = ChemicalEquation.parse(formula);

            System.out.println("Equation: " + equation);

            if (!equation.isBalanced()) {
                System.out.println("Equation is not balanced! Try again.");
                continue;
            }

            System.out.println("Enter the compound, then the amount in grams:");

            System.out.println("Compound:");
            String compoundStr = scanner.next();
            CompoundComponent component = ElementTable.parseComponent(compoundStr);

            System.out.println("Amount (grams or moles) ex. (24.01g, 0.93mol):");
            String amStr = scanner.next();
            double moles;
            if (amStr.endsWith("g")) {
                moles = Double.parseDouble(amStr.substring(0, amStr.length() - 1));
                moles /= component.molarMass();
            } else {
                // Remove letters.
                amStr = amStr.replaceAll("[a-zA-Z]", "");
                moles = Double.parseDouble(amStr);
            }
            scanner.nextLine();
            Map<CompoundComponent, Double> otherValues = equation.calculateMolesNeeded(component, moles);

            // Print out the values in a table.
            System.out.println();
            System.out.println("Compound    \tMoles\tMass");
            for (Map.Entry<CompoundComponent, Double> entry : otherValues.entrySet()) {
                System.out.printf("%-12s\t%.2fmol\t%.2fg\n", entry.getKey(), entry.getValue(), entry.getValue() * entry.getKey().molarMass());
            }
            System.out.println();
        }
    }
}
