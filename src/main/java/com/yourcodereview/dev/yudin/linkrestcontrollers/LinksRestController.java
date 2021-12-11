package com.yourcodereview.dev.yudin.linkrestcontrollers;

import com.yourcodereview.dev.yudin.entities.OriginalLink;
import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;
import com.yourcodereview.dev.yudin.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class LinksRestController {

    private Map<String, String> shortedOriginalLinks = new HashMap<>();
    private Map<String, String> originalShortedLinks = new HashMap<>();

    private Map<String, Integer> linksCountData = new HashMap<>();

    private List<StatsObject> linksAllStats = new ArrayList<>();

    {
        linksAllStats.add(new StatsObject("RAZLQzKQqh", "https://stackoverflow.com/questions/64923043/cannot", 0, 3));
        linksAllStats.add(new StatsObject("VS8LjLrl11", "https://www.codebyamir.com/blog/sort", 0, 10));
        linksAllStats.add(new StatsObject("6gTWhGP0Ci", "https://www.google.com/webhp?hl", 0, 5));
        linksAllStats.add(new StatsObject("xpWGpnIwtC", "https://translate.yandex.ru/?utm_source=main", 0, 19));
        linksAllStats.add(new StatsObject("LcioJdpL5g", "https://yandex.ru/", 0, 7));
    }

    @Autowired
    public LinkService linkService;

    @PostMapping("/generate")
    public ShortedLink generateNewLink(@RequestBody OriginalLink link) {

        String originalLink = link.getOriginal();

        ShortedLink shortedLink = linkService.getShortedLink(
                originalLink, shortedOriginalLinks,
                originalShortedLinks, linksCountData, linksAllStats);

        return shortedLink;
    }

    @GetMapping("/1/{link}")
    public String redirectToOriginalLink(@PathVariable String link) {

        String originalLink = linkService.redirectLink(link, shortedOriginalLinks, linksCountData);

        return originalLink;
    }

    @GetMapping("/stats/{link}")
    public StatsObject showLinkStats(@PathVariable String link) {

        StatsObject statsObject = linkService.showLinkStats(
                link, shortedOriginalLinks,
                linksCountData, linksAllStats);

        return statsObject;
    }

    @GetMapping("/stats")
    public List<StatsObject> showSortedLinkList(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(value = "count", required = false, defaultValue = "2") Integer pageSize) {

        List<StatsObject> statsObjects = linkService.getSortedStatsList(currentPage, pageSize, linksAllStats);

        return statsObjects;
    }
}

