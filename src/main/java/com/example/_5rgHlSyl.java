package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class _5rgHlSyl {
    public static void main(String[] args) throws Exception {
        String examIp = "36.50.135.242";
        String studentCode = "B22DCCN692";
        String qCode = "5rgHlSyl";

        HttpClient client = HttpClient.newHttpClient();

        // GET
        String getUrl = "http://" + examIp + ":2230/api/rest/data?studentCode="
                + studentCode + "&qCode=" + qCode;
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(getUrl))
                .GET()
                .build();
        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("GET response: " + getResponse.body());

        JsonObject json = JsonParser.parseString(getResponse.body()).getAsJsonObject();
        String requestId = json.get("requestId").getAsString();
        JsonArray arr = json.getAsJsonArray("data");

        int sum = 0;
        for (JsonElement e : arr) {
            sum += e.getAsInt();
        }
        System.out.println("Tổng = " + sum);

        // POST
        JsonObject body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.addProperty("requestId", requestId);
        body.addProperty("answer", sum);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://" + examIp + ":2230/api/rest/data/submit"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("POST response: " + postResponse.body());
    }
}
