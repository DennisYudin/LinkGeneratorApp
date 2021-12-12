package com.yourcodereview.dev.yudin.core.linkgenerator;

import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Log4j
@Component
public class LinkResolver implements Resolver {
    private static final String SYMBOLS_FOR_CHOOSE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int AMOUNT_SYMBOLS = 10;

    private Random random = new Random();

    @Override
    public ShortedLink generateShortLink(String input,
                                         Map<String, String> shortedLinksData,
                                         Map<String, String> originalLinksData,
                                         Map<String, Integer> countData,
                                         List<StatsObject> linksStats) {
        log.debug("Call method generateShortedLink()");

        if (shortedLinksData.containsValue(input)) {

            String shortedLink = originalLinksData.get(input);

            String shortedLinkWithPrefix = "/1/" + shortedLink;

            log.debug(shortedLinkWithPrefix);
            return new ShortedLink(shortedLinkWithPrefix);
        } else {
            String shortedLink;
            String shortedLinkWithPrefix;
            do {
                shortedLink = generateShortLink();
                log.debug("Created short name: " + shortedLink);

            } while (shortedLinksData.containsKey(shortedLink));

            shortedLinksData.put(shortedLink, input);

            originalLinksData.put(input, shortedLink);

            int count = 0;
            countData.put(shortedLink, count);

            int rank = 0;
            int amountCalls = countData.get(shortedLink);
            StatsObject statsObject = new StatsObject(shortedLink, input, rank, amountCalls);

            linksStats.add(statsObject);

            shortedLinkWithPrefix = "/1/" + shortedLink;
            log.debug("shortedLinkWithPrefix" + shortedLinkWithPrefix);

            return new ShortedLink(shortedLinkWithPrefix);
        }
    }

    private String generateShortLink() {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < AMOUNT_SYMBOLS; i++) {
            int symbolSampleSize = SYMBOLS_FOR_CHOOSE.length();
            int randomSymbol = random.nextInt(symbolSampleSize);
            char symbol = SYMBOLS_FOR_CHOOSE.charAt(randomSymbol);

            result.append(symbol);
        }
        return result.toString();
    }

    @Override
    public String redirectToOriginalLink(String input,
                                         Map<String, String> shortedLinksData,
                                         Map<String, Integer> countData,
                                         List<StatsObject> linksStats) {
        log.debug("Call method redirectLink()");

        if (shortedLinksData.containsKey(input)) {
            int count = countData.get(input) + 1;
            log.debug("Link: " + input + " Count: " + count);

            countData.put(input, count);
            if (log.isDebugEnabled()) {
                log.debug(countData);
            }

            String originalLink = getOriginalLink(input, count, linksStats);

            return originalLink;
        }
        log.error("There is no original link");
        throw new IllegalArgumentException("There is no original link");
    }

    private String getOriginalLink(String input, int count,
                                   List<StatsObject> linksStats) {

        log.debug("Call method getOriginalLink()");
        for (StatsObject object : linksStats) {

            String shortedLink = object.getLink();
            String inputWithPrefix = "/1/" + input;

            if (shortedLink.equals(input) || shortedLink.equals(inputWithPrefix)) {
                object.setCount(count);
                log.debug("Object: " + object + " has New count: " + count);

                String originalLink = object.getOriginal();
                log.debug("Short link: " + input);
                log.debug("Original link: " + originalLink);

                return originalLink;
            }
        }
        log.error("There is no original link");
        throw new IllegalArgumentException("There is no original link");
    }

    @Override
    public StatsObject showLinkStats(String input,
                                     List<StatsObject> linksStats) {
        log.debug("Call method showLinkStats() for link " + input);

        for (StatsObject object : linksStats) {
            log.debug("Object: " + object);

            String shortedLink = object.getLink();
            String inputWithPrefix = "/1/" + input;

            if (shortedLink.equals(input) || shortedLink.equals(inputWithPrefix)) {

                if (!shortedLink.contains("/1/")) {
                    String linkWithPrefix = "/1/" + shortedLink;
                    object.setLink(linkWithPrefix);

                    if (log.isDebugEnabled()) {
                        log.debug("Stats object: " + object);
                    }
                }
                return object;
            }
        }
        log.error("There is no stats for link: " + input);
        throw new IllegalArgumentException("There is no stats for link: " + input);
    }

    @Override
    public List<StatsObject> getSortedList(int page, int size,
                                           List<StatsObject> linksStats) {

        List<StatsObject> result = new ArrayList<>();

        Collections.sort(linksStats, Collections.reverseOrder());

        int offset = (page - 1) * size;
        log.debug("Offset: " + offset);

        int amount = offset + size;
        log.debug("Amount elements to get: " + amount);

        if (offset > linksStats.size() || amount > linksStats.size()) {
            log.error("There is no so many elements. We have only " + linksStats.size() + " elements.");
            throw new IllegalArgumentException(
                    "There is no so many elements. We have only " + linksStats.size() + " elements.");
        } else {
            for (int i = offset; i < amount; i++) {

                StatsObject statsObject = linksStats.get(i);
                log.debug("Stats object: " + statsObject);

                statsObject.setRank(i + 1);
                log.debug("Rank: " + statsObject.getRank());

                result.add(statsObject);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("List objects: " + result);
        }
        return result;
    }
}

