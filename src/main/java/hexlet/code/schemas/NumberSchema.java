package hexlet.code.schemas;

import java.util.Objects;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addRules(Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addRules(n -> n > 0);
        return this;
    }

    public NumberSchema range(int a, int b) {
        addRules(n -> n >= a && n <= b);
        return this;
    }
}
