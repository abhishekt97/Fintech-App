package com.abhishek.lendingengine;

import com.abhishek.lendingengine.application.model.LoanRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class LoanIntegrationTest {

    private static final String JOHN = "john";
    private static final Gson GSON = new Gson();

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void givenLoanRequestIsMadeLoanAppGetCreated() throws Exception{

         final String baseUrl = "http://localhost:" + serverPort + "/loan";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, JOHN);

        HttpEntity<LoanRequest> requestHttpEntity =
                new HttpEntity<>(new LoanRequest(50, 10, 10));

        restTemplate.postForEntity(baseUrl+"/request", requestHttpEntity, String.class );
    }

}
