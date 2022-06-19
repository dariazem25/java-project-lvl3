package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addRules(s -> !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int length) {
        addRules(s -> s.length() >= length);
        return this;
    }

    public StringSchema contains(String substring) {
        addRules(s -> s.contains(substring));
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        return (value == null || value instanceof String) && super.isValid(value);
    }
}
