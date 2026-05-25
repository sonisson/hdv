package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class _0XTGnksC {

    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "0XTGnksC";
        String examIp = "36.50.135.242";
        String url = "http://" + examIp + ":2230/api/rest/character?studentCode=" + studentCode + "&qCode=" + qCode;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
        String requestId = body.get("requestId").getAsString();
        String data = body.get("data").getAsString();

        String[] words = data.split("\\s");
        Arrays.sort(words);
        String answer = "";
        for(String word:words){
            answer += word + " ";
        }
        answer = answer.substring(0, answer.length() - 1);

        body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.addProperty("requestId", requestId);
        body.addProperty("answer", answer);
        System.out.println(body);

        url = "http://" + examIp + ":2230" + "/api/rest/character/submit";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
