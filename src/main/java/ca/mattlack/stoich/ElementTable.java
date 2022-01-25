package ca.mattlack.stoich;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ElementTable {

    private static Map<String, Element> elementMap = new HashMap<>();

    public static void load() {
        File file = new File("masses.csv");
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                // Read all lines.
                String line = reader.readLine();
                while (line != null) {
                    String[] parts = line.split(",");

                    // Parse the name and mass.
                    String name = parts[0];
                    double mass = Double.parseDouble(parts[1]);

                    // Add the element to the element map.
                    Element element = new Element(name, mass);
                    elementMap.put(name, element);

                    // Read the next line.
                    line = reader.readLine();
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Could not find masses.csv");
        }
    }

    public static Element parseElement(String symbol) {
        return elementMap.get(symbol);
    }

    public static Compound parseCompound(String formula) {
        Compound compound = new Compound();

        int index = 0;
        int currAmount = 1;
        while (index < formula.length()) {
            int componentLength = 1;
            char c = formula.charAt(index);

            // If it's a number,
            if (Character.isDigit(c)) {
                // We need to parse it into currAmount.

                int digitStart = index;
                int digitEnd = digitStart;
                while (digitEnd < formula.length() && Character.isDigit(formula.charAt(digitEnd))) {
                    digitEnd++;
                }
                currAmount = Integer.parseInt(formula.substring(digitStart, digitEnd));
                index = digitEnd;
                continue;
            }

            // It's a new component.

            CompoundComponent component;

            if (c == '(') {
                int end = getParenthesesEndIndex(formula, index);
                String subFormula = formula.substring(index + 1, end);
                component = parseCompound(subFormula);
                index = end + 1;
            } else {

                // If it's an element.
                String symbol = "" + c;

                // If the next character is lower case, it's also part of the symbol.
                if (index + 1 < formula.length() && Character.isLowerCase(formula.charAt(index + 1))) {
                    symbol += formula.charAt(index + 1);
                    componentLength++;
                }

                component = parseElement(symbol);

                index += componentLength;
            }

            // Look for _number and set currAmount to it.
            if (index < formula.length() && formula.charAt(index) == '_') {
                index++;
                int digitStart = index;
                int digitEnd = digitStart;
                while (digitEnd < formula.length() && Character.isDigit(formula.charAt(digitEnd))) {
                    digitEnd++;
                }
                if (digitEnd == digitStart) {
                    throw new RuntimeException("Error parsing compound " + formula + " at index " + index + ": expected number after _");
                }
                currAmount = Integer.parseInt(formula.substring(digitStart, digitEnd));
                index = digitEnd;
            }

            compound.addComponent(component, currAmount);
            currAmount = 1;
        }

        return compound;
    }

    /**
     * Parse something that could be an element or a compound.
     */
    public static CompoundComponent parseComponent(String text) {
        Element element = parseElement(text);
        if (element != null) {
            return element;
        }
        return parseCompound(text);
    }

    public static int getParenthesesEndIndex(String str, int start) {
        int index = start;
        int parenthesesCount = 0;
        while (index < str.length()) {
            char c = str.charAt(index);
            if (c == '(') {
                parenthesesCount++;
            } else if (c == ')') {
                parenthesesCount--;
                if (parenthesesCount == 0) {
                    return index;
                }
            }
            index++;
        }
        return -1;
    }
}
