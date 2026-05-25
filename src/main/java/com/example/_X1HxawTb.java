package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;

public class _X1HxawTb {

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("36.50.135.242", 2240)
                .usePlaintext()
                .build();

        GRPC.JudgeServiceGrpc.JudgeServiceBlockingStub stub = GRPC.JudgeServiceGrpc.newBlockingStub(channel);

        GRPC.JudgeRequest request = GRPC.JudgeRequest.newBuilder()
                .setStudentCode("B22DCCN692")
                .setQuestionAlias("X1HxawTb")
                .build();

        GRPC.JudgeResponse response = stub.request(request);
        String requestId = response.getRequestId();
        String data = response.getData();
        System.out.println(data);

        JsonArray transactions = JsonParser.parseString(data).getAsJsonArray();
        List<String> ids = new ArrayList<>();
        int reviewCount = 0;
        double totalHighRiskAmount = 0.0;
        for(JsonElement element : transactions){
            JsonObject transaction = element.getAsJsonObject();
            Double amount = transaction.get("amount").getAsDouble();
            int chargebackCount = transaction.get("chargebackCount").getAsInt();
            boolean newDevice = transaction.get("newDevice").getAsBoolean();
            String country = transaction.get("country").getAsString();
            if(amount >= 5000 || chargebackCount >= 2 || (newDevice && !"VN".equals(country))){
                String transactionId = transaction.get("transactionId").toString();
                transactionId = transactionId.substring(1, transactionId.length() - 1);
                ids.add(transactionId);
                reviewCount ++;
                totalHighRiskAmount += amount;
            }
        }
        String idsString  = String.join(", ", ids);
        String answer = "{ids=[" + idsString + "], reviewCount=" + reviewCount + ", total=" + Math.round(totalHighRiskAmount * 100.0) / 100.0 + "}";
        System.out.println(answer);

        GRPC.SubmitRequest submitRequest = GRPC.SubmitRequest.newBuilder()
                .setStudentCode("B22DCCN692")
                .setQuestionAlias("X1HxawTb")
                .setRequestId(requestId)
                .setAnswer(answer)
                .build();

        GRPC.SubmitResponse submitResponse = stub.submit(submitRequest);
        System.out.println(submitResponse.getStatus() + " - " + submitResponse.getMessage());

        channel.shutdown();
    }
}


//{ids=[TXR-216398, TXR-101844, TXR-841654, TXR-743882, TXR-679237, TXR-737113, TXR-960588, TXR-364915, TXR-973771], reviewCount=9, total=51388.97}
//{ids=[TXR-216398, TXR-101844, TXR-841654, TXR-743882, TXR-679237, TXR-737113, TXR-960588, TXR-364915, TXR-973771], reviewCount=9, total=51388.97}
