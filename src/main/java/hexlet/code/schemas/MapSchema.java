package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public final class MapSchema extends BaseSchema<Map> {

    public MapSchema required() {
        addRules(Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        addRules(m -> m.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        addRules(p -> {
            for (Object k : p.keySet()) {
                if (!(schemas.get(k).isValid(p.get(k)))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        return (value == null || value instanceof Map) && super.isValid(value);
    }
}
