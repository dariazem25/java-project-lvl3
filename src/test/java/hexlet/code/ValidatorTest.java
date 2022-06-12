package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ValidatorTest {
    private final int num1 = -1;
    private final int num2 = 5;
    private final int num3 = -2;
    private final int num4 = 6;
    private final int num5 = 4;
    private final int num6 = 50;
    private final int num7 = 100;
    private final int num8 = 1;

    //StringSchema tests
    @Test
    public void isEmptyStringTest() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();

        // null
        boolean actualResult1 = stringSchema.isValid(null);
        Assertions.assertTrue(actualResult1);

        stringSchema.required();

        // empty string
        boolean actualResult2 = stringSchema.isValid("");
        Assertions.assertFalse(actualResult2);

        // string is not empty
        boolean actualResult3 = stringSchema.isValid("123String");
        Assertions.assertTrue(actualResult3);

        // null
        boolean actualResult4 = stringSchema.isValid(null);
        Assertions.assertFalse(actualResult4);
    }


    @Test
    public void minLengthTest() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();

        // length is less than required
        stringSchema.required().minLength(2);
        boolean actualResult1 = stringSchema.isValid("1");
        Assertions.assertFalse(actualResult1);

        // length is equals to required
        boolean actualResult2 = stringSchema.isValid("12");
        Assertions.assertTrue(actualResult2);

        // length is longer than required
        boolean actualResult3 = stringSchema.isValid("123");
        Assertions.assertTrue(actualResult3);
    }

    @Test
    public void containsTest() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();

        // contains substring
        stringSchema.required().contains("test");
        boolean actualResult1 = stringSchema.isValid("This sentence contains the word \"test\"");
        Assertions.assertTrue(actualResult1);

        // does not contain substring
        stringSchema.required().contains("test");
        boolean actualResult2 = stringSchema.isValid("This sentence does not contain particular word");
        Assertions.assertFalse(actualResult2);

        // empty string does not contain substring
        stringSchema.required().contains("test");
        boolean actualResult3 = stringSchema.isValid("");
        Assertions.assertFalse(actualResult3);
    }

    //NumberSchema tests
    @Test
    public void anyNumberTest() {
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();

        // null
        boolean actualResult1 = numberSchema.isValid(null);
        Assertions.assertTrue(actualResult1);

        numberSchema.required();

        // max long
        boolean actualResult2 = numberSchema.isValid(Integer.MAX_VALUE);
        Assertions.assertTrue(actualResult2);

        // min long
        boolean actualResult3 = numberSchema.isValid(Integer.MIN_VALUE);
        Assertions.assertTrue(actualResult3);

        // null
        boolean actualResult4 = numberSchema.isValid(null);
        Assertions.assertFalse(actualResult4);

        // 0
        boolean actualResult5 = numberSchema.isValid(0);
        Assertions.assertTrue(actualResult5);
    }

    @Test
    public void positiveNumberTest() {
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();
        numberSchema.required();
        numberSchema.positive();

        // positive number
        boolean actualResult1 = numberSchema.isValid(num2);
        Assertions.assertTrue(actualResult1);

        // negative number
        boolean actualResult2 = numberSchema.isValid(-num2);
        Assertions.assertFalse(actualResult2);

        // 0
        boolean actualResult5 = numberSchema.isValid(0);
        Assertions.assertFalse(actualResult5);
    }

    @Test
    public void rangeNumberTest() {

        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();
        numberSchema.required();
        numberSchema.range(num1, num2);

        // boundaries
        boolean actualResult1 = numberSchema.isValid(num1);
        Assertions.assertTrue(actualResult1);

        boolean actualResult2 = numberSchema.isValid(num2);
        Assertions.assertTrue(actualResult2);

        boolean actualResult3 = numberSchema.isValid(num3);
        Assertions.assertFalse(actualResult3);

        boolean actualResult4 = numberSchema.isValid(num4);
        Assertions.assertFalse(actualResult4);
    }

    @Test
    public void positiveAndNegativeRangeNumberTest() {
        Validator validator = new Validator();
        NumberSchema numberSchema = validator.number();
        numberSchema.required();
        numberSchema.positive();
        numberSchema.range(num1, num2);

        // number in range but negative
        boolean actualResult1 = numberSchema.isValid(num1);
        Assertions.assertFalse(actualResult1);
    }

    @Test
    public void twoValidatorsTest() {
        Validator validator = new Validator();
        StringSchema stringSchema = validator.string();
        stringSchema.required();

        // empty string
        boolean actualResult2 = stringSchema.isValid("");
        Assertions.assertFalse(actualResult2);

        // string is not empty
        boolean actualResult3 = stringSchema.isValid("123String");
        Assertions.assertTrue(actualResult3);

        // number validator
        NumberSchema numberSchema = validator.number();
        numberSchema.required();
        boolean actualResult4 = numberSchema.isValid(num1);
        Assertions.assertTrue(actualResult4);
    }

    //MapSchema tests
    @Test
    public void isMapTest() {
        Validator validator = new Validator();
        MapSchema mapSchema = validator.map();

        // null
        boolean actualResult1 = mapSchema.isValid(null);
        Assertions.assertTrue(actualResult1);

        // map type
        mapSchema.required();
        boolean actualResult2 = mapSchema.isValid(new HashMap<>());
        Assertions.assertTrue(actualResult2);

        // null
        boolean actualResult3 = mapSchema.isValid(null);
        Assertions.assertFalse(actualResult3);
    }

    @Test
    public void sizeOfMap() {
        Validator validator = new Validator();
        MapSchema mapSchema = validator.map();
        mapSchema.required();
        mapSchema.sizeOf(2);

        // empty map
        boolean actualResult1 = mapSchema.isValid(new HashMap<>());
        Assertions.assertFalse(actualResult1);

        // size = 1
        Map<String, String> map1 = Map.of("key", "value");
        boolean actualResult2 = mapSchema.isValid(map1);
        Assertions.assertFalse(actualResult2);

        // size = 2
        Map<String, String> map2 = Map.of("key1", "value1", "key2", "value2");
        boolean actualResult3 = mapSchema.isValid(map2);
        Assertions.assertTrue(actualResult3);
    }

    //nested validation

    @Test
    public void nestedValidationTest() {
        Validator validator = new Validator();
        MapSchema schema = validator.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required().minLength(num5).contains("a"));
        schemas.put("age", validator.number().positive().range(num6, num7));
        schema.shape(schemas);

        // valid name and valid age
        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", num7);
        boolean actualResult1 = schema.isValid(human1);
        Assertions.assertTrue(actualResult1);

//        /* negative tests */

        // null name and positive age
        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", null);
        human2.put("age", num7);
        boolean actualResult2 = schema.isValid(human2);
        Assertions.assertFalse(actualResult2);

        // String name and negative age
        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "Ivan");
        human3.put("age", -num7);
        boolean actualResult3 = schema.isValid(human3);
        Assertions.assertFalse(actualResult3);

        // String name and null age
        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Vasya");
        human4.put("age", null);
        boolean actualResult4 = schema.isValid(human4);
        Assertions.assertFalse(actualResult4);

        // Name does not contain "a" letter
        Map<String, Object> human5 = new HashMap<>();
        human5.put("name", "Alex");
        human5.put("age", num7);
        boolean actualResult5 = schema.isValid(human5);
        Assertions.assertFalse(actualResult5);

        // Age which exceed the range
        Map<String, Object> human6 = new HashMap<>();
        human6.put("name", "Vova");
        human6.put("age", num8);
        boolean actualResult6 = schema.isValid(human6);
        Assertions.assertFalse(actualResult6);

        // Short name
        Map<String, Object> human7 = new HashMap<>();
        human7.put("name", "Ben");
        human7.put("age", num7);
        boolean actualResult7 = schema.isValid(human7);
        Assertions.assertFalse(actualResult7);
    }
}
