package com.yourcodereview.dev.yudin.core.linkgenerator;

import com.yourcodereview.dev.yudin.entities.ShortedLink;
import com.yourcodereview.dev.yudin.entities.StatsObject;

import java.util.List;
import java.util.Map;

public interface Resolver {

    ShortedLink generateShortLink(String input,
                                  Map<String, String> shortedLinksData,
                                  Map<String, String> originalLinksData,
                                  Map<String, Integer> countData,
                                  List<StatsObject> linksStats);

    String redirectToOriginalLink(String input,
                                  Map<String, String> shortedLinksData,
                                  Map<String, Integer> countData,
                                  List<StatsObject> linksStats);

    StatsObject showLinkStats(String input,
                              List<StatsObject> linksStats);

    List<StatsObject> getSortedList(int page, int size,
                                    List<StatsObject> linksStats);
}
