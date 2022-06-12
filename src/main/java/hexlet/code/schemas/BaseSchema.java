package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BaseSchema<T> {
    private List<Predicate<T>> rules = new ArrayList<>();

    public final boolean isValid(T obj) {
        if (obj == null && rules.isEmpty()) {
            return true;
        } else if (obj == null) {
            return false;
        }

        for (Predicate<T> p : rules) {
            if (!p.test(obj)) {
                return false;
            }
        }
        return true;
    }

    public final List<Predicate<T>> getRules() {
        return rules;
    }
}
