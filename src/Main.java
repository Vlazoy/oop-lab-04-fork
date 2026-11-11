import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

class StreamTasks {

    // 1. Среднее значение списка целых чисел.
    // Для пустого списка выбрасывается исключение.
    public static double average(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new NoSuchElementException(
                        "Нельзя вычислить среднее пустого списка"
                ));
    }

    // 2. Верхний регистр и префикс "new".
    // Например: "hello" -> "newHELLO".
    public static List<String> uppercaseWithPrefix(List<String> strings) {
        return strings.stream()
                .map(text -> "new" + text.toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());
    }

    // 3. Квадраты элементов, встречающихся ровно один раз.
    // Сохраняется порядок их появления в исходном списке.
    public static List<Long> squaresOfUnique(List<Integer> numbers) {
        return numbers.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1L)
                .map(entry -> {
                    long number = entry.getKey();
                    return number * number;
                })
                .collect(Collectors.toList());
    }

    // 4. Последний элемент коллекции.
    // Для пустой коллекции выбрасывается исключение.
    public static <T> T lastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((previous, current) -> current)
                .orElseThrow(() -> new NoSuchElementException(
                        "Коллекция пуста"
                ));
    }

    // 5. Сумма чётных чисел массива.
    // long позволяет избежать переполнения при сложении int.
    public static long sumOfEven(int[] numbers) {
        return Arrays.stream(numbers)
                .filter(number -> number % 2 == 0)
                .asLongStream()
                .sum();
    }

    // 6. Первый символ строки — ключ, оставшаяся часть — значение.
    // Пустые строки пропускаются.
    // Если ключ повторяется, сохраняется значение первой строки.
    public static Map<Character, String> stringsToMap(
            List<String> strings
    ) {
        return strings.stream()
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toMap(
                        text -> text.charAt(0),
                        text -> text.substring(1),
                        (first, second) -> first,
                        LinkedHashMap::new
                ));
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("=== 1. Среднее значение ===");

        List<Integer> numbers = Arrays.asList(2, 4, 6, 8);

        System.out.println("Исходный список: " + numbers);
        System.out.println(
                "Среднее: " + StreamTasks.average(numbers)
        );


        System.out.println("\n=== 2. Верхний регистр и префикс ===");

        List<String> words = Arrays.asList("hello", "java", "мир");

        System.out.println("Исходный список: " + words);
        System.out.println(
                "Результат: " + StreamTasks.uppercaseWithPrefix(words)
        );


        System.out.println("\n=== 3. Квадраты уникальных элементов ===");

        List<Integer> repeatedNumbers =
                Arrays.asList(2, 3, 2, 4, 5, 5, -6);

        System.out.println("Исходный список: " + repeatedNumbers);
        System.out.println(
                "Результат: "
                        + StreamTasks.squaresOfUnique(repeatedNumbers)
        );


        System.out.println("\n=== 4. Последний элемент ===");

        List<String> languages =
                Arrays.asList("Java", "Python", "C++");

        System.out.println("Исходная коллекция: " + languages);
        System.out.println(
                "Последний элемент: "
                        + StreamTasks.lastElement(languages)
        );

        try {
            StreamTasks.lastElement(Collections.emptyList());
        } catch (NoSuchElementException exception) {
            System.out.println(
                    "Для пустой коллекции: " + exception.getMessage()
            );
        }


        System.out.println("\n=== 5. Сумма чётных чисел ===");

        int[] array = {1, 2, 3, 4, 5, 6};
        int[] oddArray = {1, 3, 5};

        System.out.println("Исходный массив: " + Arrays.toString(array));
        System.out.println(
                "Сумма чётных: " + StreamTasks.sumOfEven(array)
        );

        System.out.println(
                "Массив без чётных: " + Arrays.toString(oddArray)
        );
        System.out.println(
                "Сумма чётных: " + StreamTasks.sumOfEven(oddArray)
        );


        System.out.println("\n=== 6. Преобразование строк в Map ===");

        List<String> fruits =
                Arrays.asList("apple", "banana", "cherry", "apricot");

        System.out.println("Исходный список: " + fruits);
        System.out.println(
                "Результат: " + StreamTasks.stringsToMap(fruits)
        );
    }
}