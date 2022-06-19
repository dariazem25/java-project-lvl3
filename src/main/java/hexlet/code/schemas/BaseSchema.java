package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BaseSchema<T> {
    private List<Predicate<T>> rules = new ArrayList<>();

    /**
     * @param obj - any object
     * @return true if an object is satisfied defined rules for schema, and false if not
     */
    public boolean isValid(Object obj) {
        for (Predicate p : rules) {
            if (!p.test(obj)) {
                return false;
            }
        }
        return true;
    }

    protected final void addRules(Predicate<T> p) {
        rules.add(p);
    }
}
