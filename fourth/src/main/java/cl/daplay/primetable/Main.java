package cl.daplay.primetable;

import java.util.stream.Stream;

public final class Main {

    public static void main(String... args) {
        args = args.length == 0 ? new String[] { "10" } : args;

        final int[] primes = nthPrimes(Integer.parseInt(args[0], 10));
        final int[][] table = new int[primes.length][primes.length];

        for (int row = 0; row < primes.length; row++) {
            for (int column = 0; column < primes.length; column++) {
                table[row][column] = primes[row] * primes[column];
            }
        }

        final int padding = 1 + Integer.toString(primes[primes.length - 1] * primes[primes.length - 1], 10).length();

        // header
        System.out.printf("%s|", whitespace(padding));
        for (int row = 0; row < table.length; row++) {
            System.out.printf("%s", span(primes[row], padding));
        }
        System.out.printf("%n");

        // hr
        final int hrSize = padding * (primes.length + 1) + 1;
        System.out.println(repeat(hrSize, '='));

        for (int row = 0; row < table.length; row++) {
            System.out.printf("%s|", span(primes[row], padding));

            for (int column = 0; column < table[0].length; column++) {
                System.out.printf("%s", span(table[row][column], padding));
            }

            System.out.printf("%n");
        }
    }

    public static String span(int n, int length) {
        final String txt = Integer.toString(n, 10);
        final int zzz = length - txt.length();

        return new StringBuilder(new CharSequence() {
            @Override
            public int length() {
                return length;
            }

            @Override
            public char charAt(final int index) {
                // pads left
                return index < zzz ? ' ' : txt.charAt(index - zzz);
                // pads right
                // return index < txt.length() ? txt.charAt(index) : ' ';
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return span(n, end - start);
            }
        }).toString();
    }

    public static String whitespace(int length) {
        return repeat(length, ' ');
    }

    public static String repeat(int length, final char c) {
        return new StringBuilder(new CharSequence() {
            @Override
            public int length() {
                return length;
            }

            @Override
            public char charAt(final int index) {
                return c;
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return whitespace(end - start);
            }
        }).toString();
    }

    private static Stream<Integer> infinite() {
        return Stream.iterate(2, it -> it + 1);
    }

    public static int[] nthPrimes(int n) {
        return infinite().filter(Main::isPrime).limit(n).mapToInt(Integer::intValue).toArray();
    }

    public static boolean isPrime(int k) {
        final int n = Math.abs(k);
        if (n <= 1) return false;
        return !infinite().limit(n - 2).filter(it -> n % it == 0).findAny().isPresent();
    }

}
