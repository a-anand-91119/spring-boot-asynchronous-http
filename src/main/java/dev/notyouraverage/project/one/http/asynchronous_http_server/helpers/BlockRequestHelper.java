package dev.notyouraverage.project.one.http.asynchronous_http_server.helpers;

import dev.notyouraverage.project.core.dtos.kafka.ProcessedResponsePayload;

public interface BlockRequestHelper {
    void initializeLock(String requestId);

    void lockThread(String requestId) throws InterruptedException;

    void unlockThread(String requestId);

    void setResponsePayload(String requestId, ProcessedResponsePayload processedResponsePayload);

    ProcessedResponsePayload removeResponsePayload(String requestId);
}
