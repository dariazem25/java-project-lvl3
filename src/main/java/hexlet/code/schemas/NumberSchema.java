package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        Predicate<Integer> p = Objects::nonNull;
        getRules().add(p);
        return this;
    }

    public NumberSchema positive() {
        Predicate<Integer> p = n -> n > 0;
        getRules().add(p);
        return this;
    }

    public NumberSchema range(int a, int b) {
        Predicate<Integer> p = n -> n >= a && n <= b;
        getRules().add(p);
        return this;
    }
}
