package homework;

import java.util.*;

import static java.util.stream.Collectors.*;

public class ComplexExamples {

    static class Person {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Person person)) return false;
            return getId() == person.getId() && getName().equals(person.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"), // дубликат
            new Person(1, "Harry"), // тёзка
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };
        /*  Raw data:

        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

        **************************************************

        Duplicate filtered, grouped by name, sorted by name and id:

        Amelia:
        1 - Amelia (5)
        2 - Amelia (6)
        3 - Amelia (7)
        4 - Amelia (8)
        Emily:
        1 - Emily (3)
        Harry:
        1 - Harry (0)
        2 - Harry (1)
        3 - Harry (2)
        Jack:
        1 - Jack (4)
     */

    /**
     * prints the result of task1 (english variation)
     */
    public static void task1Eng() {
        if (RAW_DATA == null) {
            System.out.println("RAW_DATA must be not null");
            return;
        }

        Arrays.stream(RAW_DATA)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.comparingInt(Person::getId))
                .collect(groupingBy(Person::getName, TreeMap::new, mapping(Person::getId, toList())))
                .forEach((personName, personIdList) -> {
                    int i = 0;
                    System.out.println(personName);
                    for (var personId:personIdList) {
                        System.out.printf("%d - %s (%d)\n", ++i, personName, personId);
                    }
                });
    }

    /**
     * prints the result of task1 (russian variation)
     */
    public static void task1Rus() {
        if (RAW_DATA == null) {
            System.out.println("RAW_DATA must be not null");
            return;
        }

        Arrays.stream(RAW_DATA)
                .filter(Objects::nonNull)
                .distinct()
                .collect(groupingBy(Person::getName, TreeMap::new, counting()))
                .forEach((personName, personIdCount) ->
                        System.out.printf("Key: %s\nValue:%d\n", personName, personIdCount)
                );
    }

    /**
     *
     * @param array - array to be checked
     * @param sum - exact sum of 2 elements
     * @return string in format "[1, 2]" or empty string
     */
    public static String arrayFirstSumOfTwoCheck(int[] array, int sum) {
        System.out.printf("%s, %d -> ", Arrays.toString(array), sum);
        String res = "";

        if(array == null) {
            System.out.println("There is no result");
            return res;
        }

        Set<Integer> set = new HashSet<>();
        int len = array.length;
        for (int j : array) {
            if (set.contains(sum - j)) {
                res = String.format("[%d, %d]", sum - j, j);
                System.out.println(res);
                return res;
            }
            set.add(j);
        }

        System.out.println("There is no result");
        return res;
    }

    /**
     *
     * @param subtext - subtext to check
     * @param text - text to check
     * @return result of fuzzy search of subtext in text, false if null is anywhere
     */
    public static boolean fuzzySearch(String subtext, String text) {
        if(subtext == null || text == null) {
            System.out.println(false);
            return false;
        }

        StringBuilder sb = new StringBuilder(".*");
        for (int i = 0; i < subtext.length(); i++) {
            sb.append("\\Q");
            sb.append(subtext.charAt(i));
            sb.append("\\E.*");
        }

        System.out.println(text.matches(sb.toString()));
        return text.matches(sb.toString());
    }

    public static void assertion(String factdata, String testdata) {
        assert testdata.equals(factdata) : String.format("Expected: %s, in fact: %s", testdata, factdata);
    }

    public static void assertion(boolean factdata, boolean testdata) {
        assert testdata == factdata : String.format("Expected: %s, in fact: %s", testdata, factdata);
    }
    public static void main(String[] args) {
        System.out.println("Raw data:");
        System.out.println();

        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();
        System.out.println("Duplicate filtered, grouped by name, sorted by name and id:");
        System.out.println();

        //Duplicate filtered, grouped by name, sorted by name and id:
        task1Eng();
        System.out.println("**************************************************");
        //Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени
        task1Rus();
        System.out.println("**************************************************");

        //[3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
        assertion(arrayFirstSumOfTwoCheck(new int[] {3, 4, 2, 7, 6}, 10), "[3, 7]");
        assertion(arrayFirstSumOfTwoCheck(new int[] {3, 4, 2, 7, 6}, 11), "[4, 7]");
        System.out.println("**************************************************");

        //Реализовать функцию нечеткого поиска
        assertion(fuzzySearch("car", "ca6$$#_rtwheel"), true);
        assertion(fuzzySearch("cwhl", "cartwheel"), true);
        assertion(fuzzySearch("c\\whee", "car\\twheel"), true);
        assertion(fuzzySearch("cartwheel", "cartwheel"), true);
        assertion(fuzzySearch("cwheeel", "cartwheel"), false);
        assertion(fuzzySearch("lw", "cartwheel"), false);
        assertion(fuzzySearch("lw", null), false);

        /*
        Task1
            Убрать дубликаты, отсортировать по идентификатору, сгруппировать по имени
            Что должно получиться
                Key:Amelia
                Value:4
                Key: Emily
                Value:1
                Key: Harry
                Value:3
                Key: Jack
                Value:1
         */

        /*
        Task2
            [3, 4, 2, 7], 10 -> [3, 7] - вывести пару менно в скобках, которые дают сумму - 10
         */

        /*
        Task3
            Реализовать функцию нечеткого поиска
                    fuzzySearch("car", "ca6$$#_rtwheel"); // true
                    fuzzySearch("cwhl", "cartwheel"); // true
                    fuzzySearch("cwhee", "cartwheel"); // true
                    fuzzySearch("cartwheel", "cartwheel"); // true
                    fuzzySearch("cwheeel", "cartwheel"); // false
                    fuzzySearch("lw", "cartwheel"); // false
         */
    }
}
