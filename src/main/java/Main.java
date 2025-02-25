import java.util.*;
import java.util.stream.Collectors;


public class Main {

    static Map<String, Integer> allMaps = new HashMap<>();
    static int count = 0;

    private static final String startText = "Введите текст. Для завершения напишите Exit";
    private static final String allStats = "Статистика по всем введенным текстам:";
    private static final String againText = "Введите еще один текст и напишите Exit";
    private static final String textMenu = """ 
                
                Нажмите:
                1 = Чтобы ввести еще один текст для подсчета
                2 = Чтобы посмотреть общую статистику всех введенных текстов
                Любая другая клавиша завершит работу программы с потерей всех сохраненных подсчетов!
                
                """;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        splitText(startText);

    }

    private static void againCount() {
        System.out.println(textMenu);
        int button = sc.nextInt();
        switch (button) {
            case 1 -> splitText(againText);
            case 2 -> printResult(allMaps, allStats);
            default -> System.out.println("До свидания!");
        }

    }

    private static void splitText(String text) {
        System.out.println(text);
        List<String> list = new ArrayList<>();

        String exit = sc.nextLine();
        while (!exit.equals("Exit")) {
            String[] arr = Arrays.stream(exit.replaceAll("\\p{Punct}", "")
                    .trim()
                    .split("\\s+"))
                    .filter(w -> !w.isEmpty())
                    .toArray(String[]::new);
            list.addAll(Arrays.asList(arr));
            exit = sc.nextLine();
        }

        countThisWords(list);
    }

    private static void countThisWords(List<String> list) {
        count++;
        Map<String, Integer> tempMaps = new HashMap<>();
        for (String s : list) {
            tempMaps.put(s, tempMaps.getOrDefault(s, 0) + 1);
            allMaps.put(s, allMaps.getOrDefault(s, 0) + 1);
        }

        printResult(tempMaps, "\n\nТекущий результат:\n");
    }

    private static void printResult(Map<String, Integer> map, String text) {
        System.out.println(text);

        Map<String, Integer> resultMap = map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new
                ));

        for (Map.Entry<String, Integer> me : resultMap.entrySet()) {
            System.out.println(me.getKey() + " || " + me.getValue());
        }

        againCount();
    }



}
