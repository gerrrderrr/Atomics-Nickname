import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final int three = 3;
    public static final int four = 4;
    public static final int five = 5;

    public static AtomicInteger threeLetterCount = new AtomicInteger(0);
    public static AtomicInteger fourLetterCount = new AtomicInteger(0);
    public static AtomicInteger fiveLetterCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    addToCount(text);
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    addToCount(text);
                }
            }
        }).start();

        new Thread(() -> {
            for (String text : texts) {
                if (isAscendingABC(text)) {
                    addToCount(text);
                }
            }
        }).start();


        Thread.sleep(600);
        System.out.println(threeLetterCount);
        System.out.println(fourLetterCount);
        System.out.println(fiveLetterCount);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String word) {
        StringBuilder sb = new StringBuilder();
        String fromLastToFirst = sb.append(word).reverse().toString();
        return fromLastToFirst.equals(word);
    }

    public static boolean isSameLetter(String word) {
        int countOfMatchingChar = 0;
        for (char character : word.toCharArray()) {
            if (character == word.charAt(0)) {
                countOfMatchingChar++;
            }
        }
        return countOfMatchingChar == word.length();
    }

    public static boolean isAscendingABC(String word) {
        int firstIsBigger = 0;
        char[] x = word.toCharArray();
        for (int i = 0; i < x.length - 1; i++) {
            if (x[i] < x[i + 1]) {
                firstIsBigger++;
            } else {
                return false;
            }
        }
        return firstIsBigger == word.length() - 1;
    }

    public static void addToCount(String text) {
        if (three == text.length()) {
            threeLetterCount.getAndIncrement();
        } else if (four == text.length()) {
            fourLetterCount.getAndIncrement();
        } else if (five == text.length()) {
            fiveLetterCount.getAndIncrement();
        }
    }
}
