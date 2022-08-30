package com.sabancihan.collectionservice.collector;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;


@Slf4j
@Component

@RequiredArgsConstructor
public class Downloader   {

    private final ObjectMapper objectMapper;
    private final Requester requester;




    public VulnMapping download(String targetUrl) {



        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(new URL(targetUrl).openStream());
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser parser =  jsonFactory.createParser(gzipInputStream);



            VulnMapping result = objectMapper.readValue(parser, VulnMapping.class);


            parser.close();

            log.info("Vulnerabilities downloaded from {}", targetUrl);
            return result;



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
