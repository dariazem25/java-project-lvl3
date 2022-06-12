package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema<Map> {

    public MapSchema required() {
        Predicate<Map> p = Objects::nonNull;
        getRules().add(p);
        return this;
    }

    public MapSchema sizeOf(int size) {
        Predicate<Map> p = m -> m.size() == size;
        getRules().add(p);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        getRules().add(p -> {
            for (Object k : p.keySet()) {
                if (!(schemas.get(k).isValid(p.get(k)))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
