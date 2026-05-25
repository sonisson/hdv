package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _Eki9gCWg {

    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "Eki9gCWg";
        String examIp = "36.50.135.242";
        String url = "http://" + examIp + ":2230/api/rest/path?studentCode=" + studentCode + "&qCode=" + qCode;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        JsonObject body = JsonParser.parseString(response.body()).getAsJsonObject();
        String requestId = body.get("requestId").getAsString();
        JsonArray data = body.get("data").getAsJsonArray();
        int maxOverdueMount = Integer.MIN_VALUE;
        String customerId = "";
        int page = 0;
        for(JsonElement element : data) {
            JsonObject customer = element.getAsJsonObject();
            String status = customer.get("status").getAsString();
            if ("OVERDUE".equals(status)) {
                int overdueAmount = customer.get("overdueAmount").getAsInt();
                if(overdueAmount > maxOverdueMount){
                    maxOverdueMount = overdueAmount;
                    customerId = customer.get("customerId").getAsString();
                    page = customer.get("page").getAsInt();
                }
            }
        }

        url = "http://" + examIp + ":2230/api/rest/path/" + customerId + "?studentCode=" + studentCode + "&qCode=" + qCode + "&requestId=" + requestId + "&status=OVERDUE&page=" + page;
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
