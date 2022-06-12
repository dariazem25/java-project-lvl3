package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        Predicate<String> p = s -> !s.isEmpty();
        getRules().add(p);
        return this;
    }

    public StringSchema minLength(int length) {
        Predicate<String> p = s -> s.length() >= length;
        getRules().add(p);
        return this;
    }

    public StringSchema contains(String substring) {
        Predicate<String> p = s -> s.contains(substring);
        getRules().add(p);
        return this;
    }
}
