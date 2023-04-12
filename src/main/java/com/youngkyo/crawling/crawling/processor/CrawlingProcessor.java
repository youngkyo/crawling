package com.youngkyo.crawling.crawling.processor;

import com.google.common.collect.Lists;
import com.youngkyo.crawling.crawling.service.CrawlingService;
import com.youngkyo.crawling.crawling.utils.CrawlingStringUtils;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CrawlingProcessor {
    private final CrawlingService crawlingService;
    private final List<String> urls =
        Lists.newArrayList("https://www.genesis.com", "https://shop.hyundai.com", "https://www.kia.com");

    public String process() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        String merge = forkJoinPool
            .submit(() -> urls.parallelStream().map(crawlingService::crawling)).join()
            .collect(Collectors.joining(""));

        String parse = CrawlingStringUtils.parse(merge);
        String sort = CrawlingStringUtils.sort(parse);
        return CrawlingStringUtils.cross(sort);
    }
}
