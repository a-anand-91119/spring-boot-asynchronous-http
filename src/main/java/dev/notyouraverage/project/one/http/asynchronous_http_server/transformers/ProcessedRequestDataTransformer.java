package dev.notyouraverage.project.one.http.asynchronous_http_server.transformers;

import dev.notyouraverage.project.base.annotations.Transformer;
import dev.notyouraverage.project.core.dtos.kafka.ProcessedResponsePayload;
import dev.notyouraverage.project.one.http.asynchronous_http_server.dtos.response.ProcessedRequestResponse;

@Transformer
public class ProcessedRequestDataTransformer {

    public ProcessedRequestResponse convertToProcessedRequestResponse(ProcessedResponsePayload processedResponsePayload) {
        return ProcessedRequestResponse.builder()
                .requestId(processedResponsePayload.getRequestId())
                .count(processedResponsePayload.getCount())
                .name(processedResponsePayload.getName())
                .build();
    }
}
