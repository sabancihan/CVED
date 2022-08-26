package com.sabancihan.collectionservice.collector;


import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.zip.GZIPInputStream;


@Slf4j
@Component

@RequiredArgsConstructor
public class Downloader   {

    private final ObjectMapper objectMapper;



    public VulnMapping download(String targetUrl) {



        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(new URL(targetUrl).openStream());
            JsonFactory jsonFactory = new JsonFactory();
            JsonParser parser =  jsonFactory.createParser(gzipInputStream);



            VulnMapping result = objectMapper.readValue(parser, VulnMapping.class);


            parser.close();

            log.info("Vulnerabilities downloaded from {}", targetUrl);
            return result;



        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
