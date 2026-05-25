package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class rest {
    public static void main(String[] args) throws Exception{
        String studentCode = "B22DCCN692";
        String serverIp = "36.50.135.242";
        String qCode = "";
        String url = "http://" + serverIp + ":2230/api/rest/data?studentCode=" + studentCode + "&qCode=" + qCode;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        String requestId = json.get("requestId").getAsString();
//        JsonArray arr = json.getAsJsonArray("data");
//
//        double capturedTotal = 0.0;
//        double refundedTotal = 0.0;
//        int failedCount = 0;
//        for (JsonElement e: arr){
//            JsonObject obj = e.getAsJsonObject();
//            String status = obj.get("status").getAsString();
//            double amount = obj.get("amount").getAsDouble();
//            if("CAPTURED".equals(status)){
//                capturedTotal += amount;
//            }
//            else if("REFUNDED".equals(status)){
//                refundedTotal += amount;
//            }
//            else if("FAILED".equals(status)) {
//                failedCount++;
//            }
//        }
//        Double netTotal = capturedTotal - refundedTotal;
//
//        JsonObject answer = new JsonObject();
//        answer.addProperty("capturedTotal", Math.round(capturedTotal * 100.0) / 100.0);
//        answer.addProperty("refundedTotal", Math.round(refundedTotal * 100.0) / 100.0);
//        answer.addProperty("netTotal", Math.round(netTotal * 100.0) / 100.0);
//        answer.addProperty("failedCount", failedCount);
//
        JsonObject body = new JsonObject();
//        body.addProperty("studentCode", studentCode);
//        body.addProperty("qCode", qCode);
//        body.addProperty("requestId", requestId);
//        body.add("answer", answer);
//        System.out.println(body);

        url = "http://" + serverIp + ":2230/api/rest/data/submit";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
