package com.youngkyo.crawling.crawling.service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CrawlingServiceImpl implements CrawlingService {

    @Value ("${retry.timeout}")
    private Integer timeout;

    @Override
    @Retryable (backoff = @Backoff (delay = 500), value = SocketTimeoutException.class)
    @Cacheable(key = "#url", value = "crawling")
    public String crawling(String url) {
        log.info("crawling url: {}", url);
        try {
            Document document = Jsoup.connect(url).timeout(timeout).get();
            StringBuilder sb = new StringBuilder();
            parseNode(sb, document.childNodes());
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings ("unused")
    @Recover
    public String recover(Exception exception, String url) {
        log.error("crawling url: {} retry fail. message: {}", url, exception.getMessage());
        return Strings.EMPTY;
    }

    private void parseNode(StringBuilder sb, List<Node> childNodes) {
        for (Node childNode : childNodes) {
            if (childNode instanceof TextNode) {
                sb.append(((TextNode) childNode).text());
            } else if(childNode instanceof Element) {
                sb.append(((Element) childNode).tag().getName());
                for (Attribute attribute : childNode.attributes()) {
                    sb.append(attribute.getKey());
                    sb.append(attribute.getValue());
                }
                parseNode(sb, childNode.childNodes());
            }
        }
    }
}
