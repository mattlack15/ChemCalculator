package ca.mattlack.chem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MolarMass {
    private double molarMass;
    private double atomsPerUnit;

    public MolarMass(double molarMass, double atomsPerUnit) {
        this.molarMass = molarMass;
        this.atomsPerUnit = atomsPerUnit;
    }

    public double getMolarMass() {
        return molarMass;
    }

    public void setMolarMass(double molarMass) {
        this.molarMass = molarMass;
    }

    public double getAtomsPerUnit() {
        return atomsPerUnit;
    }

    public void setAtomsPerUnit(double atomsPerUnit) {
        this.atomsPerUnit = atomsPerUnit;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Get the molar mass and atoms per unit.
        System.out.println("Enter the molar mass:");
        double molarMass = scanner.nextDouble();
        System.out.println("Enter the atoms per unit:");
        double atomsPerUnit = scanner.nextDouble();

        // Create a new MolarMass object.
        MolarMass molarMassObject = new MolarMass(molarMass, atomsPerUnit);

        String builder = "1. Mass\n" +
                "2. Moles\n" +
                "3. Particles\n" +
                "4. Atoms\n";
        System.out.println(builder);

        // Get the start.
        System.out.println("Enter the start:");
        int start = scanner.nextInt() - 1;

        // Get the end.
        System.out.println("Enter the end:");
        int end = scanner.nextInt() - 1;

        // Create element list.
        List<ValueElement> elementList = new ArrayList<>();
        elementList.add(new ValueMass());
        elementList.add(new ValueMoles());
        elementList.add(new ValueParticles());
        elementList.add(new ValueAtoms());

        System.out.println("Enter " + elementList.get(start).getName() + ":");
        double current = scanner.nextDouble();

        int index = start;
        while (index != end) {

            int sign = (int) Math.signum(end - start);

            ValueElement element = elementList.get(index);
            if (sign == -1) {
                current = element.prev(current, molarMassObject);
            } else {
                current = element.next(current, molarMassObject);
            }

            index += sign;
        }

        System.out.println("Result: " + current);
    }
}
