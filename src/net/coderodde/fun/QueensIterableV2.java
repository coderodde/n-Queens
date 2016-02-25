package net.coderodde.fun;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This awesome class iterates over all possible arrangements of queens on a
 * <code>n<sup>2</sup></code> chess board such that no queen offends another
 * one.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Feb 25, 2016)
 */
public class QueensIterableV2 implements Iterable<Queens> {

    private final int dimension;

    public QueensIterableV2(int dimension) {
        if (dimension < 1) {
            throw new IllegalArgumentException(
                    "Requested dimension is not a positive integer: " + 
                     dimension + ".");
        }

        this.dimension = dimension;
    }

    @Override
    public Iterator<Queens> iterator() {
        return new QueensIterator();
    }

    private final class QueensIterator implements Iterator<Queens> {

        private Queens next;
        private int stackSize;
        private final int[] state = new int[dimension];
        
        private final boolean[] verticalMarks = 
                new boolean[dimension];
        
        private final boolean[] descendingDiagonalMarks =
                new boolean[2 * dimension  - 1];
        
        private final boolean[] ascendingDiagonalMarks  = 
                new boolean[2 * dimension - 1];

        QueensIterator() {
            state[0] = -1;
            push(-1);
            solve();
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Queens next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration exceeded.");
            }

            Queens ret = next;
            solve();
            return ret;
        }

        private int size() {
            return stackSize;
        }

        private void push(int i) {
            state[stackSize++] = i;
        }

        private int pop() {
            return state[--stackSize];
        }

        private int peek() {
            return state[stackSize - 1];
        }
        
        private int getDescendingDiagonalIndex(int x, int y) {
            return x + dimension - 1 - y;
        }

        private int getAscendingDiagonalIndex(int x, int y) {
            return 2 * dimension - 2 - x - y;
        }
        
        private void mark(int x, int y) {
            verticalMarks[x] = true;
            ascendingDiagonalMarks [getAscendingDiagonalIndex (x, y)] = true; 
            descendingDiagonalMarks[getDescendingDiagonalIndex(x, y)] = true;
        }
        
        private void unmark(int x, int y) {
            verticalMarks[x] = false;
            ascendingDiagonalMarks [getAscendingDiagonalIndex (x, y)] = false;
            descendingDiagonalMarks[getDescendingDiagonalIndex(x, y)] = false;
        }
        
        private boolean isSafePosition(int x, int y) {
            return !(verticalMarks[x]
                  || ascendingDiagonalMarks[getAscendingDiagonalIndex(x, y)]
                  || descendingDiagonalMarks[getDescendingDiagonalIndex(x, y)]);
        }

        private void solve() {
            mainLoop:
            while (stackSize > 0) {
                int y = stackSize - 1;
                
                if (peek() != -1) {
                    unmark(peek(), y);
                }
                
                for (int x = peek() + 1; x < dimension; ++x) {
                    state[y] = x;

                    if (isSafePosition(x, y)) {
                        if (stackSize == dimension) {
                            next = new Queens(state);
                            return;
                        }

                        mark(x, y);
                        push(-1);
                        continue mainLoop;
                    }
                }
                
                pop();
            }

            next = null;
        }
    }
}
