package com.sabancihan.collectionservice.collector;


import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.time.LocalDateTime;
import java.util.List;
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



        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
