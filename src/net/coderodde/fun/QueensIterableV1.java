package net.coderodde.fun;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This awesome class iterates over all possible arrangements of queens on a
 * <code>n<sup>2</sup></code> chess board such that no queen offends another
 * one.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Feb 23, 2016)
 */
public class QueensIterableV1 implements Iterable<Queens> {

    private final int dimension;

    public QueensIterableV1(int dimension) {
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

        private final int[] state = new int[dimension];
        private int stackSize;
        private Queens next;

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

        private boolean isSafePosition(int x, int y) {
            // Check upwards.
            for (int yy = y - 1; yy >= 0; --yy) {
                if (state[yy] == x) {
                    return false;
                }
            }

            // Check in diagonal (up and to the left).
            for (int yy = y - 1, xx = x - 1; yy >= 0 && xx >= 0; --xx, --yy) {
                if (state[yy] == xx) {
                    return false;
                }
            }

            // Check in diagonal (up and to the right).
            for (int yy = y - 1, xx = x + 1; 
                    yy >= 0 && xx < dimension; 
                    ++xx, --yy) {
                if (state[yy] == xx) {
                    return false;
                }
            }

            return true;
        }

        private void solve() {
            mainLoop:
            while (stackSize > 0) {
                int y = stackSize - 1;

                for (int x = peek() + 1; x < dimension; ++x) {
                    state[y] = x;

                    if (isSafePosition(x, y)) {
                        if (stackSize == dimension) {
                            next = new Queens(state);
                            return;
                        }

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
