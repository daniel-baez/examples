package cl.daplay.primetable;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public final class Main_UT {

    @Test
    public void padding_test() {
        assertEquals(Main.span(5, 1).length(), "5".length());
        assertEquals(Main.span(5, 2).length(), "5 ".length());
        assertEquals(Main.span(5, 3).length(), "5  ".length());
        assertEquals(Main.span(5, 4).length(), "5   ".length());
        assertEquals(Main.span(5, 5).length(), "5    ".length());
    }

    @Test
    public void prime_generation_test() {
        final List<Integer> expected = new LinkedList<>();
        final Scanner scanner = new Scanner(Main_UT.class.getResourceAsStream("/known_primes.txt"));
        while (scanner.hasNextInt()) {
            final int i = scanner.nextInt();
            expected.add(i);
        }

        final int[] primes = Main.nthPrimes(expected.size());

        final List<Integer> actual = new AbstractList<Integer>() {
            @Override
            public Integer get(final int index) {
                return primes[index];
            }

            @Override
            public int size() {
                return primes.length;
            }
        };

        assertEquals(expected, actual);
    }

}
