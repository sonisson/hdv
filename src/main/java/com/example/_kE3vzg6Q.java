package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest.BodyPublishers;

public class _kE3vzg6Q {

    public static void main(String[] args) throws Exception{
        String studentCode = "B22DCCN692";
        String qCode = "kE3vzg6Q";
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
        int maxEtaDays = data.get("maxEtaDays").getAsInt();
        JsonArray quotes = data.getAsJsonArray("quotes");
        double weightKg = data.get("weightKg").getAsDouble();
        JsonObject answer = new JsonObject();
        double minTotalFee = Double.MAX_VALUE;
        double maxReliability = Double.MIN_VALUE;
        for(JsonElement element : quotes){
            JsonObject quote = element.getAsJsonObject();
            int etaDays = quote.get("etaDays").getAsInt();
            if(etaDays <= maxEtaDays){
                String carrier = quote.get("carrier").getAsString();
                double baseFee = quote.get("baseFee").getAsDouble();
                double perKgFee = quote.get("perKgFee").getAsDouble();
                double reliability = quote.get("reliability").getAsDouble();
                double totalFee = baseFee + weightKg * perKgFee;
                if(totalFee < minTotalFee || (totalFee == minTotalFee && reliability > maxReliability)){
                    minTotalFee = totalFee;
                    maxReliability = reliability;
                    answer.addProperty("carrier", carrier);
                    answer.addProperty("totalFee", Math.round(totalFee * 100.0) / 100.0);
                    answer.addProperty("etaDays", etaDays);
                }
            }
        }

        body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.addProperty("requestId", requestId);
        body.add("answer", answer);
        System.out.println(body);

        url = "http://" + examIp + ":2230/api/rest/object/submit";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
