package com.yourcodereview.dev.yudin.services.impl;

import com.yourcodereview.dev.yudin.core.linkgenerator.Generator;
import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;
import com.yourcodereview.dev.yudin.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private Generator linkGenerator;

    @Override
    public ShortedLink getShortedLink(String input, Map<String, String> shortedLinksData,
                                      Map<String, String> originalLinksData,
                                      Map<String, Integer> countData,
                                      List<StatsObject> linksStats) {

        ShortedLink shortedLink = linkGenerator.generateShortedLink(
                input, shortedLinksData, originalLinksData,
                countData);

        return shortedLink;
    }

    @Override
    public String redirectLink(String input, Map<String, String> shortedLinksData,
                               Map<String, Integer> countData) {

        if (shortedLinksData.containsKey(input)) {
            int count = countData.get(input) + 1;

            countData.put(input, count);

            return shortedLinksData.get(input);
        } else {
            throw new IllegalArgumentException("There is no such link: " + input);
        }
    }

    @Override
    public StatsObject showLinkStats(String input, Map<String, String> shortedLinksData,
                                     Map<String, Integer> countData, List<StatsObject> linksStats) {

        StatsObject statsObject = linkGenerator.showLinkStats(
                input, shortedLinksData, countData, linksStats);

        return statsObject;
    }

    @Override
    public List<StatsObject> getSortedStatsList(int page, int size, List<StatsObject> linksStats) {

        List<StatsObject> statsObjects = linkGenerator.getSortedStatsList(page, size, linksStats);

        return statsObjects;
    }
}
