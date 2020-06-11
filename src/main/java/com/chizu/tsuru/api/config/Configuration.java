package com.chizu.tsuru.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {

    @Getter
    @Value("${com.chizu.tsuru.api.geocoding.apikey}")
    private String apiKey;

    @Getter
    @Value("${com.chizu.tsuru.api.geocoding.apiurl}")
    private String apiUrl;

    @Getter
    @Value("${com.chizu.tsuru.api.lucene.folder}")
    private String luceneFolder;

    @Getter
    @Value("${com.chizu.tsuru.api.lucene.result.count}")
    private int luceneResultCount;

}
