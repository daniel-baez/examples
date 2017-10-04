package cl.daplay.second;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class App {

    private static CharSequence padding(final int len) {
        if (len <= 0) throw new IllegalArgumentException(format("invalid padding length: %d", len));

        return new CharSequence() {
            @Override
            public int length() {
                return len;
            }

            @Override
            public char charAt(final int index) {
                if (index < 0 || index >= len) throw new IllegalArgumentException(format("invalid index: %d for padding of length: %d", len, index));
                return ' ';
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return padding(end - start);
            }
        };
    }

    private static List<List<String>> newLines() {
        final List<List<String>> lines = new ArrayList<>();
        lines.add(new LinkedList<>());
        return lines;
    }

    private static List<String> newLine(final String word) {
        final LinkedList<String> line = new LinkedList<>();
        line.add(word);
        return line;
    }

    private static int spaces(final List<String> line) {
        return line.isEmpty() ? 0 : line.size() - 1;
    }

    private static List<CharSequence> buildPaddingSequence(final int spaces, final int _padding) {
        if (spaces == 0) {
            return Collections.emptyList();
        }

        final int total = spaces + _padding;
        final int remainder = total % spaces;
        final int size = total / spaces;

        return new AbstractList<CharSequence>() {
            @Override
            public int size() {
                return spaces;
            }

            @Override
            public CharSequence get(final int index) {
                if (index < 0 || index >= spaces) throw new IllegalArgumentException(format("invalid index: %d for padding sequence of length: %d", index, spaces));
                return padding(size + (index < remainder ? 1 : 0));
            }
        };
    }

    private static int lineLength(final List<String> line) {
        return line.stream().mapToInt(String::length).sum() + spaces(line);
    }

    private static boolean fits(final String word, final List<String> line, final int len) {
        final int newLength = word.length() + lineLength(line) + 1;
        return newLength <= len;
    }

    public static String justify(final int len, final String words) {
        final List<List<String>> lines = newLines();

        // collect the lines
        for (final String word : asList(words.split(" "))) {
            final List<String> line = lines.get(lines.size() - 1);

            if (fits(word, line, len)) {
                line.add(word);
            } else {
                lines.add(newLine(word));
            }
        }

        // build output
        final StringBuilder result = new StringBuilder();

        for (final List<String> line : lines) {
            final List<CharSequence> padding = buildPaddingSequence(spaces(line), len - lineLength(line));

            for (int i = 0; i < line.size(); i++) {
                result.append(line.get(i));

                if (i < padding.size()) {
                    result.append(padding.get(i));
                }
            }

            result.append("\n");
        }

        return result.toString().trim();
    }

}
