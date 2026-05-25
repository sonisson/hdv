package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _lTvZRPrC {

    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "lTvZRPrC";
        String examIp = "36.50.135.242";
        String url = "http://" + examIp + ":2230/api/rest/header?studentCode=" + studentCode + "&qCode=" + qCode;

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
        String nonce = data.get("nonce").getAsString();
        String signingKey = data.get("signingKey").getAsString();
        JsonArray events = data.get("events").getAsJsonArray();
        String eventsString = "";
        for(JsonElement element : events){
            String event = element.getAsString();
            eventsString += event + "|";
        }
        eventsString = eventsString.substring(0, eventsString.length() - 1);
        String payload = nonce + ":" + eventsString + ":" + studentCode;
        System.out.println(payload);

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(payload.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hmacBytes) {
            sb.append(String.format("%02x", b));
        }
        String signature = sb.toString();
        System.out.println(signature);

        body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.addProperty("requestId", requestId);
        System.out.println(body);

        url = "http://" + examIp + ":2230/api/rest/header/submit";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .header("X-Signature", signature)
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
