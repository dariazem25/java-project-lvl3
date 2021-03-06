package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addRules(Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addRules(n -> n == null || n > 0);
        return this;
    }

    public NumberSchema range(int a, int b) {
        addRules(n -> n == null || (n >= a && n <= b));
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        return (value == null || value instanceof Integer) && super.isValid(value);
    }
}
