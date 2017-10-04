package cl.daplay.first;

public interface Utils {

    static boolean isBlank(final String string) {
        return null == string || string.trim().isEmpty();
    }

}
