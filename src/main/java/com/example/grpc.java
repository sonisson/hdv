package com.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class grpc {
    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("36.50.135.242", 2240)
                .usePlaintext()
                .build();

        GRPC.TypedJudgeServiceGrpc.TypedJudgeServiceBlockingStub stub = GRPC.TypedJudgeServiceGrpc.newBlockingStub(channel);
        GRPC.TypedJudgeRequest request = GRPC.TypedJudgeRequest.newBuilder()
                .setStudentCode("B22DCCN692")
                .setQuestionAlias("AtqTMJv1")
                .build();

        GRPC.TypedJudgeResponse response = stub.requestTyped(request);
        String requestId = response.getRequestId();
        System.out.println(response.getSensorTelemetry());

        GRPC.SensorTelemetryData telemetry = response.getSensorTelemetry();
        double threshold = telemetry.getThreshold();
        double average = 0.0;
        int anomalyCount = 0;
        List<Double> values = new ArrayList<>();
        List<GRPC.SensorReading> readings = telemetry.getReadingsList();
        for(GRPC.SensorReading reading : readings){
            double value = reading.getValue();
            average += value;
            values.add(value);
            if(value > threshold) anomalyCount ++;
        }
        int n = values.size();
        average = Math.round(average / n * 100.0) / 100.0;
        Collections.sort(values);
        int index = (int) (Math.ceil(n * 0.95) - 1);
        double p95 = values.get(index);

        GRPC.SensorTelemetryAnswer answer = GRPC.SensorTelemetryAnswer.newBuilder()
                .setAverage(average)
                .setP95(p95)
                .setAnomalyCount(anomalyCount)
                .build();

        GRPC.TypedSubmitRequest submitRequest = GRPC.TypedSubmitRequest.newBuilder()
                .setStudentCode("B22DCCN692")
                .setQuestionAlias("AtqTMJv1")
                .setRequestId(requestId)
                .setSensorTelemetryAnswer(answer)
                .build();

        GRPC.TypedSubmitResponse submitResponse = stub.submitTyped(submitRequest);
        System.out.println(submitResponse);
    }
}
