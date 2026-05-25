package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class _XEvIpo9b {

    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "XEvIpo9b";
        String examIp = "36.50.135.242";
        String url = "http://" + examIp + ":2230/api/rest/object?studentCode=" + studentCode + "&qCode=" + qCode;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.body());

        JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
        String requestId = body.get("requestId").getAsString();
        JsonObject data = body.get("data").getAsJsonObject();
        double price = data.get("price").getAsDouble();
        double taxRate = data.get("taxRate").getAsDouble();
        double discount = data.get("discount").getAsDouble();
        double finalPrice = price * (1 + taxRate / 100) * (1 - discount / 100);
        data.addProperty("finalPrice", Math.round(finalPrice * 100.0) / 100.0);

        body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.addProperty("requestId", requestId);
        body.add("answer", data);

        url = "http://" + examIp + ":2230/api/rest/object/submit";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
