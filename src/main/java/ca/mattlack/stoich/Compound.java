package ca.mattlack.stoich;

import java.util.Arrays;
import java.util.Map;

public class Compound extends CompoundComponent {

    @Override
    public double molarMass() {
        double sum = 0;

        // Go through all components, sum up the masses.
        for (Map.Entry<CompoundComponent, Integer> entry : getComponents().entrySet()) {
            sum += entry.getKey().molarMass() * entry.getValue();
        }

        return sum;
    }


    @Override
    public String formula() {
        StringBuilder sb = new StringBuilder();

        // Go through all components, appending the symbols.

        // Make a list of the keys first, and sort by molar mass.
        Map<CompoundComponent, Integer> components = getComponents();
        CompoundComponent[] keys = components.keySet().toArray(new CompoundComponent[0]);
        Arrays.sort(keys);

        for (CompoundComponent key : keys) {

            if (!(key instanceof Element)) {
                sb.append('(');
            }

            sb.append(key.formula());

            if (!(key instanceof Element)) {
                sb.append(')');
            }

            int amount = components.get(key);
            if (amount > 1) {
                String amountStr = Integer.toString(amount);
                amountStr = toSubscript(amountStr);
                sb.append(amountStr);
            }
        }

        return sb.toString();

    }

    /**
     * The most common superscript digits (1, 2, and 3) were in ISO-8859-1 and were therefore carried over into those positions in the Latin-1 range of Unicode. The rest were placed in a dedicated section of Unicode at U+2070 to U+209F. The two tables below show these characters. Each superscript or subscript character is preceded by a normal x to show the subscripting/superscripting. The table on the left contains the actual Unicode characters; the one on the right contains the equivalents using HTML markup for the subscript or superscript.
     */
    private String toSubscript(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            char newChar = (char) (c - '0' + 0x2080);
            sb.append(newChar);
        }
        return sb.toString();
    }
}
