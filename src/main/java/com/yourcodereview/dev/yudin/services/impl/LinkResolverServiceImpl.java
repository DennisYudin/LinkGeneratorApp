package com.yourcodereview.dev.yudin.services.impl;

import com.yourcodereview.dev.yudin.core.linkgenerator.Resolver;
import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;
import com.yourcodereview.dev.yudin.services.LinkResolverService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Log4j
@Service
public class LinkResolverServiceImpl implements LinkResolverService {

    @Autowired
    private Resolver linkResolver;

    @Override
    public ShortedLink getShortLink(String input,
                                    Map<String, String> shortedLinksData,
                                    Map<String, String> originalLinksData,
                                    Map<String, Integer> countData,
                                    List<StatsObject> linksStats) {
        log.debug("Call method getShortLink()");

        ShortedLink shortedLink = linkResolver.generateShortLink(
                input, shortedLinksData, originalLinksData,
                countData, linksStats);

        if (log.isDebugEnabled()) {
            log.debug("Shorted link: " + shortedLink);
        }
        return shortedLink;
    }

    @Override
    public String redirectToOriginalLink(String input,
                                         Map<String, String> shortedLinksData,
                                         Map<String, Integer> countData,
                                         List<StatsObject> linksStats) {
        log.debug("Call method redirectToOriginalLink()");

        String originalLink = linkResolver.redirectToOriginalLink(input, shortedLinksData, countData, linksStats);

        log.debug("Original link: " + originalLink);
        return originalLink;
    }

    @Override
    public StatsObject showLinkStats(String input,
                                     Map<String, String> shortedLinksData,
                                     Map<String, Integer> countData,
                                     List<StatsObject> linksStats) {
        log.debug("Call method showLinkStats()");

        StatsObject statsObject = linkResolver.showLinkStats(input, linksStats);

        if (log.isDebugEnabled()) {
            log.debug("StatsObject: " + statsObject);
        }
        return statsObject;
    }

    @Override
    public List<StatsObject> getSortedList(int page, int size,
                                           List<StatsObject> linksStats) {
        log.debug("Call method getSortedList()");

        List<StatsObject> statsObjects = linkResolver.getSortedList(page, size, linksStats);

        if (log.isDebugEnabled()) {
            log.debug("SortedList: " + statsObjects);
        }
        return statsObjects;
    }
}
