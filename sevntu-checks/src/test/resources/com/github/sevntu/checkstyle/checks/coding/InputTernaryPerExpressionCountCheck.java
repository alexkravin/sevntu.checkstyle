package com.github.sevntu.checkstyle.checks.coding;

@SuppressWarnings("unused")
public class InputTernaryPerExpressionCountCheck {

    final int x;
    final int y;
    final int z;

    public boolean getSmth() {
        // examples of ugly code:
        int k = (a == b) ? (a == b) ? (a == b) ? 5 : 6 : 6 : 6;
        // simple:
        int a = 5;
        final int b = 6;
        final int d = (a == b) ? (a == b) ? 5 : 6 : 6; // bad (nested in first position)
        int c = (a == b) ? 5 : 6; // good
        final int d = (a == b) ? (a == b) ? 5 : 6 : 6; // bad (nested in first position)
        int e = (a == b) ? 5 : (a == b) ? 5 : 6; // bad (nested in second position)

        // more complex:
        Integer result = (0.2 == Math.random()) ? null : 5; // good
        final Integer result2 = (0.2 == Math.random()) ? (0.3 == Math.random()) ? null : 3 : 6; // bad (nested in first position)
        Integer result3 = (0.2 == Math.random()) ? null : (0.3 == Math.random()) ? null : 4; // bad (nested in second position)

        // and more complex:
        int r1 = (getSmth() || Math.random() == 5) ? null : (int) Math.cos(400 * (10 + 40)); // good
        final int r2 = (0.2 == Math.random()) ? (0.3 == Math.random()) ? null : (int) Math.cos(400 * (10 + 40)) : 6; // bad (nested in first position)
        int r3 = (int) ((0.2 == Math.random()) ? (Integer) null + getInt() : (0.3 == Math.random()) ? (Integer) null : (int) Math.sin(300 * (12 + 30))); // bad (nested in second position)

        // String inline ternary:
        checkSmth("msg " + ((a == b) ? "5" : "6"), getInt(), getSmth()); // good
        checkSmth("msg " + ((a == b) ? (a == b) ? "5" : "6" : "6"), getSmth(), getSmth()); // bad (nested in first position)
        checkSmth("msg " + ((a == b) ? "5" : (a == b) ? "5" : "6"), getSmth(), getSmth()); // bad (nested in second position)
        String x = (getSmth() ? "A" : "B") + (getSmth() ? "B" : "C");
        x = getSmth() ? "A" : "B" + (getSmth() ? "B" : "C");
        String String = new String("");
        return getSmth();
    }

    InputNestedTernaryCheck() {
        // in C-tor (final variable first-initialization):
        x = (getSmth() || Math.random() == 5) ? null : (int) Math
                .cos(400 * (10 + 40)); // good
        y = (0.2 == Math.random()) ? (0.3 == Math.random()) ? null : (int) Math
                .cos(400 * (10 + 40)) : 6; // bad (nested in first position)
        z = (int) ((0.2 == Math.random()) ? (Integer) null + getInt()
                : (0.3 == Math.random()) ? (Integer) null : (int) Math
                        .sin(300 * (12 + 30))); // bad (nested in second
                                                // position)
    }

    public String apply(String column) {
        return alias1 != null ? dialect.quote(alias1, column) : dialect
                .quote(column) + " = " + alias2 != null ? dialect.quote(alias2,
                column) : dialect.quote(column);
    }
    
    

    private void foo() {
        setDetail(new TextUpdate(
                "1. Ensure all tradenames in "
                + (sectorName != null ? sectorName + " " : "")
                + "2. If company possibly warrants placement in other industry"
                + ", flag to relevant analyst.\n"
                + "3. Update focus tag\n"
                + "4. Update " + (sectorName != null ? sectorName + " " : "") + "timestamp\n"));
    }

   public static void checkSmth() {
       callString = "{? = call " +
               (StringUtils.hasLength(catalogNameToUse) ? catalogNameToUse + "." : "") +
               (StringUtils.hasLength(schemaNameToUse) ? schemaNameToUse + "." : "") +
               procedureNameToUse + "(";
   } 

    private void checkSmth(String arg, int int1, boolean smth) {

    }
}
