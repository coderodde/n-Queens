package net.coderodde.fun;

/**
 * This class represents a feasible layout of queens in a <code>n</code>-queens 
 * problem.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Feb 23, 2016)
 */
public class Queens {

    private final int[] layout;

    Queens(int[] layout) {
        this.layout = layout.clone();
    }

    public int getSize() {
        return layout.length;
    }

    public int[] getRepresentationArray() {
        return layout.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String horizontalBar = getHorizontalBar();
        sb.append(horizontalBar);

        for (int y = 0; y < layout.length; ++y) {
            sb.append(getRow(layout[y]));
            sb.append(horizontalBar);
        }

        return sb.toString();
    }

    private String getRow(int x) {
        StringBuilder sb = new StringBuilder().append("|");

        for (int i = 0; i < layout.length; ++i) {
            sb.append((i == x) ? " Q |" : "   |");
        }

        return sb.append('\n').toString();
    }

    private String getHorizontalBar() {
        StringBuilder sb = new StringBuilder().append('+');

        for (int i = 0; i < layout.length; ++i) {
            sb.append("---+");
        }

        return sb.append('\n').toString();
    }
}
