package com.example.client;

import com.example.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserClient {

    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate;
    private String sessionId;

    public UserClient() {
        this.restTemplate = new RestTemplate();
    }


    public List<User> getAllUsers() {
        try {
            ResponseEntity<User[]> response = restTemplate.getForEntity(BASE_URL, User[].class);
            sessionId = extractSessionId(response.getHeaders());
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (HttpStatusCodeException e) {
            System.out.println("Error: " + e.getResponseBodyAsString());
            return null;
        }
    }


    public String createUser(User user) {
        HttpHeaders headers = createHeaders();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, request, String.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            System.out.println("Error: " + e.getResponseBodyAsString());
            return null;
        }
    }


    public String updateUser(User user) {
        HttpHeaders headers = createHeaders();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, request, String.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            System.out.println("Error: " + e.getResponseBodyAsString());
            return null;
        }
    }


    public String deleteUser(Long userId) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/" + userId, HttpMethod.DELETE, request, String.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            System.out.println("Error: " + e.getResponseBodyAsString());
            return null;
        }
    }

    private String extractSessionId(HttpHeaders headers) {
        List<String> cookies = headers.get("Set-Cookie");
        if (cookies != null && !cookies.isEmpty()) {
            return cookies.get(0).split(";")[0];
        }
        return null;
    }


    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (sessionId != null) {
            headers.set("Cookie", sessionId);
        }
        return headers;
    }
}

