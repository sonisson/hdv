package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _PHCeYUHS {

    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "PHCeYUHS";
        String examIp = "36.50.135.242";
        String url = "http://" + examIp + ":2230/api/rest/method?studentCode=" + studentCode + "&qCode=" + qCode;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
        String requestId = body.get("requestId").getAsString();
        JsonObject data = body.get("data").getAsJsonObject();
        String etag = data.get("etag").getAsString();

        JsonObject answer = new JsonObject();
        answer.addProperty("status", "RESOLVED");
        body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.add("answer", answer);
        System.out.println(body);

        url = "http://" + examIp + ":2230/api/rest/method/" + requestId;
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers("Content-Type", "application/json")
                .header("If-Match", etag)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
