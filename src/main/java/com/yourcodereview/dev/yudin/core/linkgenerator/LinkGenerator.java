package com.yourcodereview.dev.yudin.core.linkgenerator;

import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j
@Component
public class LinkGenerator implements Generator{
    private static final String SYMBOLS_FOR_CHOOSE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int AMOUNT_SYMBOLS = 10;

    private Random random = new Random();

    @Override
    public ShortedLink generateShortedLink(String input, Map<String, String> shortedLinksData,
                                           Map<String, String> originalLinksData,
                                           Map<String, Integer> countData) {

        if (shortedLinksData.containsValue(input)) {

            String shortedLink = originalLinksData.get(input);

            String shortedLinkWithPrefix = "/1/" + shortedLink;

            log.debug(shortedLinkWithPrefix);
            return new ShortedLink(shortedLinkWithPrefix);
        } else {
            String shortedLink;
            String shortedLinkWithPrefix;
            do {
                shortedLink = generateNewLink();

            } while (shortedLinksData.containsKey(shortedLink));

            shortedLinksData.put(shortedLink, input);
            originalLinksData.put(input, shortedLink);

            int count = 0;
            countData.put(shortedLink, count);

            shortedLinkWithPrefix = "/1/" + shortedLink;
            log.debug(shortedLinkWithPrefix);

            return new ShortedLink(shortedLinkWithPrefix);
        }
    }

    @Override
    public StatsObject showLinkStats(String input, Map<String, String> shortedLinksData,
                                     Map<String, Integer> countData, List<StatsObject> linksStats) {

        if (shortedLinksData.containsKey(input)) {
            String shortedLink = "/1/" + input;
            String originalLink = shortedLinksData.get(input);

            int rank = 0;
            for (StatsObject statsObject : linksStats) {
                if (statsObject.getLink().equals(input)) {
                    rank = statsObject.getRank();
                }
            }
            int count = countData.get(input);

            StatsObject statsObject = new StatsObject(shortedLink, originalLink, rank, count);

            linksStats.add(statsObject);

            return statsObject;
        } else {
            throw new IllegalArgumentException("There is no such link: " + input);
        }
    }

    @Override
    public List<StatsObject> getSortedStatsList(int page, int size, List<StatsObject> linksStats) {

        List<StatsObject> result = new ArrayList<>();

        Collections.sort(linksStats, Collections.reverseOrder());

        int offset = (page - 1) * size;
        int amount = offset + size;

        if (offset > linksStats.size() || amount > linksStats.size()) {
            throw new IllegalArgumentException("There is no so many elements. We have " + linksStats.size() + " elements.");
        } else {
            for (int i = offset; i < amount; i++) {
                linksStats.get(i).setRank(i + 1);

                result.add(linksStats.get(i));
            }
        }
        return result;
    }

    private String generateNewLink() {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < AMOUNT_SYMBOLS; i++) {
            int symbolSampleSize = SYMBOLS_FOR_CHOOSE.length();
            int randomSymbol = random.nextInt(symbolSampleSize);
            char symbol = SYMBOLS_FOR_CHOOSE.charAt(randomSymbol);

            result.append(symbol);
        }
        return result.toString();
    }
}

