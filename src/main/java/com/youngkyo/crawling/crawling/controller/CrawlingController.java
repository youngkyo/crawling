package com.youngkyo.crawling.crawling.controller;

import com.youngkyo.crawling.crawling.controller.dto.ResponseDTO;
import com.youngkyo.crawling.crawling.processor.CrawlingProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CrawlingController {

    private final CrawlingProcessor crawlingProcessor;

    @GetMapping("/crawling")
    @ResponseBody
    public ResponseDTO crawling() {
        try {
            String process = crawlingProcessor.process();
            return new ResponseDTO(HttpStatus.OK.value(), process);
        } catch (Exception e) {
            log.error("crawling fail. {}", e.getMessage());
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), Strings.EMPTY);
        }
    }
}
