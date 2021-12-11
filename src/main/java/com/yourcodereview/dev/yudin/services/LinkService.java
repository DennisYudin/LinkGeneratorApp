package com.yourcodereview.dev.yudin.services;

import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;

import java.util.List;
import java.util.Map;

public interface LinkService {

    ShortedLink getShortedLink(String input, Map<String, String> shortedLinksData,
                               Map<String, String> originalLinksData,
                               Map<String, Integer> countData,
                               List<StatsObject> linksStats);

    String redirectLink(String input, Map<String, String> shortedLinksData,
                        Map<String, Integer> countData);

    StatsObject showLinkStats(String input, Map<String, String> shortedLinksData,
                              Map<String, Integer> countData, List<StatsObject> linksStats);

    List<StatsObject> getSortedStatsList(int page, int size, List<StatsObject> linksStats);
}
