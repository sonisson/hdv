package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class _HTkTVBZ1 {

    public static void main(String[] arrgs) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "HTkTVBZ1";
        String examIp = "36.50.135.242";
        String url = "http://" + examIp + ":2230" + "/api/rest/character?studentCode=" + studentCode + "&qCode=" + qCode;

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

        String[] logs = data.split("\\|\\|");
        for(int i = 0; i < logs.length; i++){
            String[] couples = logs[i].split("\\s");
            for(int j = 0; j < couples.length; j++) {
                String[] elm = couples[j].split("=");
                if(elm.length > 1) {
                    if ("user".equals(elm[0])) {
                        elm[1] = "[EMAIL]";
                    } else if ("phone".equals(elm[0]) && elm[1].length() == 10 && elm[1].charAt(0) == '0') {
                        elm[1] = "[PHONE]";
                    } else if ("token".equals(elm[0])) {
                        elm[1] = "[TOKEN]";
                    }
                }
                couples[j] = String.join("=", elm);
            }
            logs[i] = String.join(" ", couples);
            System.out.println(logs[i]);;
        }
        String answer = String.join("||", logs);

        body = new JsonObject();
        body.addProperty("studentCode", studentCode);
        body.addProperty("qCode", qCode);
        body.addProperty("requestId", requestId);
        body.addProperty("answer", answer);
        System.out.println(body);

        url = "http://" + examIp + ":2230/api/rest/character/submit";
        request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }
}
