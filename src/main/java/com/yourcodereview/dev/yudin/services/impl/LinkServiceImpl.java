package com.yourcodereview.dev.yudin.services.impl;

import com.yourcodereview.dev.yudin.core.linkgenerator.Resolver;
import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;
import com.yourcodereview.dev.yudin.services.LinkResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LinkServiceImpl implements LinkResolverService {

    @Autowired
    private Resolver linkResolver;

    @Override
    public ShortedLink getShortLink(String input,
                                    Map<String, String> shortedLinksData,
                                    Map<String, String> originalLinksData,
                                    Map<String, Integer> countData,
                                    List<StatsObject> linksStats) {

        ShortedLink shortedLink = linkResolver.generateShortLink(
                input, shortedLinksData, originalLinksData,
                countData, linksStats);

        return shortedLink;
    }

    @Override
    public String redirectToOriginalLink(String input,
                                         Map<String, String> shortedLinksData,
                                         Map<String, Integer> countData,
                                         List<StatsObject> linksStats) {

       String originalLink = linkResolver.redirectToOriginalLink(input, shortedLinksData, countData, linksStats);

       return originalLink;
    }

    @Override
    public StatsObject showLinkStats(String input,
                                     Map<String, String> shortedLinksData,
                                     Map<String, Integer> countData,
                                     List<StatsObject> linksStats) {

        StatsObject statsObject = linkResolver.showLinkStats(input, linksStats);

        return statsObject;
    }

    @Override
    public List<StatsObject> getSortedList(int page, int size,
                                           List<StatsObject> linksStats) {

        List<StatsObject> statsObjects = linkResolver.getSortedList(page, size, linksStats);

        return statsObjects;
    }
}
