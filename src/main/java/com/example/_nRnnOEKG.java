package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _nRnnOEKG {

    public static void main(String[] args) throws Exception{
        String studentCode = "B22DCCN692";
        String qCode = "nRnnOEKG";
        String examIp = "36.50.135.242";
        String url = "http://" + examIp + ":2230/api/rest/header?studentCode=" + studentCode + "&qCode=" + qCode;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String checkSum = response.headers().firstValue("X-Checksum").get();
        System.out.println(response.body());
        System.out.println(checkSum);

        JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
        String requestId = body.get("requestId").getAsString();

        body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.addProperty("requestId", requestId);

        url = "http://" + examIp + ":2230/api/rest/header/submit";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .header("X-Checksum", checkSum)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }
}
