package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _VPCGpQZW {

    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "VPCGpQZW";
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
        JsonElement firstElement = data.get(0);
        JsonObject firstInvoice = firstElement.getAsJsonObject();
        int invoiceId = firstInvoice.get("id").getAsInt();

        url = "http://" + examIp + ":2230/api/rest/path/" + invoiceId + "?studentCode=" + studentCode + "&qCode=" + qCode + "&requestId=" + requestId + "&currency=USD";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
