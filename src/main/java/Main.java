import java.util.*;
import java.util.Map.Entry;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static final String ALPHABET = "RLRFR";

    public static void main(String[] args) {
        String[] texts = new String[100];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateRoute(ALPHABET, texts.length);
        }

        for (String text : texts) {
            new Thread(() -> {
                int counter = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == 'R') {
                        ++counter;
                    }
                }

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(counter)) {
                        sizeToFreq.put(counter, sizeToFreq.get(counter) + 1);
                    } else {
                        sizeToFreq.put(counter, 1);
                    }
                }
            }).start();
        }

        Optional<Entry<Integer, Integer>> maxValueEntry = sizeToFreq.entrySet()
                .stream()
                .max(Comparator.comparing(Entry::getValue)
                );
        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n",
                maxValueEntry.get().getKey(),
                maxValueEntry.get().getValue()
        );
        System.out.println("Все размеры:");
        for (Entry<Integer, Integer> size : sizeToFreq.entrySet()) {
            System.out.printf("- %d (%d раз)\n", size.getKey(), size.getValue());
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
