package com.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Arrays;

public class _jpXqeznF {

    public static void main(String[] args) throws Exception {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("36.50.135.242", 2240)
                .usePlaintext()
                .build();

        GRPC.JudgeServiceGrpc.JudgeServiceBlockingStub stub = GRPC.JudgeServiceGrpc.newBlockingStub(channel);

        GRPC.JudgeRequest req = GRPC.JudgeRequest.newBuilder()
                .setStudentCode("B22DCCN692")
                .setQuestionAlias("jpXqeznF")
                .build();

        GRPC.JudgeResponse resp = stub.request(req);
        String requestId = resp.getRequestId();
        String data = resp.getData();

        int sum = Arrays.stream(data.split(","))
                .mapToInt(Integer::parseInt)
                .sum();

        GRPC.SubmitRequest submitReq = GRPC.SubmitRequest.newBuilder()
                .setStudentCode("B22DCCN692")
                .setQuestionAlias("jpXqeznF")
                .setRequestId(requestId)
                .setAnswer(String.valueOf(sum))
                .build();

        GRPC.SubmitResponse submitResp = stub.submit(submitReq);
        System.out.println(submitResp.getStatus() + " - " + submitResp.getMessage());

        channel.shutdown();

    }
}
