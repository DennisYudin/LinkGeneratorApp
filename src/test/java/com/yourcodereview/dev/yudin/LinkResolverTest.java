package com.yourcodereview.dev.yudin;

import com.yourcodereview.dev.yudin.core.linkgenerator.LinkResolver;
import com.yourcodereview.dev.yudin.core.linkgenerator.Resolver;
import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkResolverTest {
    private Map<String, String> shortedOriginalLinks = new HashMap<>();
    private Map<String, String> originalShortedLinks = new HashMap<>();

    private Map<String, Integer> linksCountData = new HashMap<>();

    private List<StatsObject> linksAllStats = new ArrayList<>();

    {
        linksAllStats.add(new StatsObject("/1/RAZLQzKQqh", "https://stackoverflow.com/questions/64923043/cannot", 0, 3));
        linksAllStats.add(new StatsObject("/1/VS8LjLrl11", "https://www.codebyamir.com/blog/sort", 0, 10));
        linksAllStats.add(new StatsObject("/1/6gTWhGP0Ci", "https://www.google.com/webhp?hl", 0, 5));
        linksAllStats.add(new StatsObject("/1/xpWGpnIwtC", "https://translate.yandex.ru/?utm_source=main", 0, 19));
        linksAllStats.add(new StatsObject("/1/LcioJdpL5g", "https://yandex.ru/", 0, 7));
    }

    {
        shortedOriginalLinks.put("LcioJdpL5g", "https://yandex.ru/");
    }

    {
        originalShortedLinks.put("https://yandex.ru/", "LcioJdpL5g");
    }

    {
        linksCountData.put("LcioJdpL5g", 0);
    }

    Resolver linkResolver = new LinkResolver();

    @Test
    void generateShortLink_ShouldGenerateShortName_WhenInputIsOriginalLink() {

        String originalLink = "https://some-server.com/some/url?some_param=1";

        ShortedLink shortedLink = linkResolver.generateShortLink(
                originalLink,
                shortedOriginalLinks,
                originalShortedLinks,
                linksCountData,
                linksAllStats);

        int expectedSize = 13;
        int actualSize = shortedLink.getLink().length();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void generateShortLink_ShouldReturnExistedShortLink_WhenInputIsExistedOriginalLink() {

        String originalLink = "https://yandex.ru/";

        ShortedLink shortedLink = linkResolver.generateShortLink(
                originalLink,
                shortedOriginalLinks,
                originalShortedLinks,
                linksCountData, linksAllStats);

        String expected = "/1/LcioJdpL5g";
        String actual = shortedLink.getLink();

        int expectedSize = 13;
        int actualSize = shortedLink.getLink().length();

        assertEquals(expected, actual);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void redirectToOriginalLink_ShouldReturnOriginalLink_WhenInputIsShortLink() {

        String shortedLink = "LcioJdpL5g";

        String originalString = linkResolver.redirectToOriginalLink(
                shortedLink,
                shortedOriginalLinks,
                linksCountData,
                linksAllStats);

        String expected = "https://yandex.ru/";
        String actual = originalString;

        assertEquals(expected, actual);
    }

    @Test
    void redirectToOriginalLink_ShouldThrowException_WhenInputIsDoesNotExistLink() {

        String doesNotExistShortLink = "ksjvbks";

        assertThrows(IllegalArgumentException.class,
                () -> linkResolver.redirectToOriginalLink(
                        doesNotExistShortLink,
                        shortedOriginalLinks,
                        linksCountData,
                        linksAllStats));
    }

    @Test
    void showLinkStats_ShouldReturnStatsObject_WhenInputIsShortedLink() {

        String shortedLink = "RAZLQzKQqh";

        StatsObject actual = linkResolver.showLinkStats(shortedLink, linksAllStats);

        StatsObject expected = new StatsObject("/1/RAZLQzKQqh", "https://stackoverflow.com/questions/64923043/cannot", 0, 3);

        assertEquals(expected, actual);
    }

    @Test
    void showLinkStats_ShouldThrowException_WhenInputIsDoesNotExistLink() {

        String doesNotExistShortLink = "ksjvbks";

        assertThrows(IllegalArgumentException.class,
                () -> linkResolver.showLinkStats(doesNotExistShortLink, linksAllStats));
    }

    @Test
    void getSortedList_ShouldReturnSortedListStatsObjects_WhenInputIsPageSize() {

        int page = 1;
        int size = 5;

        List<StatsObject> statsObjects = linkResolver.getSortedList(page, size, linksAllStats);

        int expectedSize = statsObjects.size();
        int actualSize = statsObjects.size();

        assertEquals(expectedSize, actualSize);

        int expectedRank = 1;
        for (StatsObject statsObject : statsObjects) {
            int actualRank = statsObject.getRank();

            assertEquals(expectedRank, actualRank);
            expectedRank++;
        }
    }
}
