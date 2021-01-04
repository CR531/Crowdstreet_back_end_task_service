package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@RestController
public class SampleServiceController {
    @PostMapping("/request")
    public String post(@RequestBody SampleInput request) {
        System.out.println("Successfully received the message to the service " + request.body);
        final String uri = "http://localhost:8090/callback/1";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("STARTED.......");
        restTemplate.postForObject(uri, "STARTED", String.class);
        System.out.println("PROCESSING.......");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SampleServiceResponse sampleServiceResponse = new SampleServiceResponse();
        sampleServiceResponse.status = "PROCESSED";
        sampleServiceResponse.detail = "Successfully processed the message";
        System.out.println("PROCESSED.......");
        restTemplate.put(uri, sampleServiceResponse);
        return "Successfully received the message to the service " + request.body;
    }
}
