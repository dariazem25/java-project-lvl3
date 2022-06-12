### Hexlet tests and linter status:
[![Actions Status](https://github.com/dariazem25/java-project-lvl3/workflows/hexlet-check/badge.svg)](https://github.com/dariazem25/java-project-lvl3/actions)
![Java CI](https://github.com/dariazem25/java-project-lvl3/workflows/Java%20CI/badge.svg)
[![Maintainability](https://api.codeclimate.com/v1/badges/062f53d8cdbb0d68047b/maintainability)](https://codeclimate.com/github/dariazem25/java-project-lvl3/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/062f53d8cdbb0d68047b/test_coverage)](https://codeclimate.com/github/dariazem25/java-project-lvl3/test_coverage)

#Data Validator
Data Validator - is a library that validates correctness of data.

#How to use
Schema - a structure with rules for data validation.
There are three schemas:
1. StringSchema
2. NumberSchema
3. MapSchema

## StringSchema
StringSchema consists of:
1. required validator - any not empty string
2. minLength validator - a string length is longer or equal to specified length
3. contains validator - a string contains specified substring

```
import hexlet.code.Validator;
import hexlet.code.schemas.StringSchema;

Validator v = new Validator();

StringSchema schema = v.string();

schema.isValid(""); // true
schema.isValid(null); // true

schema.required();

schema.isValid("what does the fox say"); // true
schema.isValid("hexlet"); // true
schema.isValid(null); // false
schema.isValid("");; // false

schema.contains("what").isValid("what does the fox say"); // true
schema.contains("whatthe").isValid("what does the fox say"); // false

schema.isValid("what does the fox say"); // false
```

## NumberSchema
NumberSchema consists of:
1. required validator - any number including 0
2. positive validator - positive number
3. range validator - numbers should be in the range including boundaries.

```
import hexlet.code.Validator;
import hexlet.code.schemas.NumberSchema;

Validator v = new Validator();

NumberSchema schema = v.number();

schema.isValid(null); // true

schema.required();

schema.isValid(null); // false
schema.isValid(10) // true
schema.isValid("5"); // false

schema.positive().isValid(10); // true
schema.isValid(-10); // false

schema.range(5, 10);

schema.isValid(5); // true
schema.isValid(10); // true
schema.isValid(4); // false
schema.isValid(11); // false
```

## MapSchema
MapSchema consists of:
1. required validator - requires Map type
2. sizeOf validator - size of Map should be equal to specified value

```
import hexlet.code.Validator;
import hexlet.code.schemas.MapSchema;

Validator v = new Validator();

MapSchema schema = v.map();

schema.isValid(null); // true

schema.required();

schema.isValid(null) // false
schema.isValid(new HashMap()); // true
Map<String, String> data = new HashMap<>();
data.put("key1", "value1");
schema.isValid(data); // true

schema.sizeof(2);

schema.isValid(data);  // false
data.put("key2", "value2");
schema.isValid(data); // true
```

## Nested validation
Map values can be also validated by schemas: StringSchema, NumberSchema.
```
import hexlet.code.Validator;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.BaseSchema;

Validator v = new Validator();

MapSchema schema = v.map();

// shape - allow set validation rulles for Map values by keys
Map<String, BaseSchema> schemas = new HashMap<>();
schemas.put("name", v.string().required());
schemas.put("age", v.number().positive());
schema.shape(schemas);

Map<String, Object> human1 = new HashMap<>();
human1.put("name", "Kolya");
human1.put("age", 100);
schema.isValid(human1); // true

Map<String, Object> human2 = new HashMap<>();
human2.put("name", "Maya");
human2.put("age", null);
schema.isValid(human2); // true

Map<String, Object> human3 = new HashMap<>();
human3.put("name", "");
human3.put("age", null);
schema.isValid(human3); // false

Map<String, Object> human4 = new HashMap<>();
human4.put("name", "Valya");
human4.put("age", -5);
schema.isValid(human4); // false
```