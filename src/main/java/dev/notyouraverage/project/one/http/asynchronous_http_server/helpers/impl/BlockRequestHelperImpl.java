package dev.notyouraverage.project.one.http.asynchronous_http_server.helpers.impl;

import dev.notyouraverage.project.base.annotations.ServiceHelper;
import dev.notyouraverage.project.core.dtos.kafka.ProcessedResponsePayload;
import dev.notyouraverage.project.one.http.asynchronous_http_server.helpers.BlockRequestHelper;

import java.util.concurrent.ConcurrentHashMap;

@ServiceHelper
public class BlockRequestHelperImpl implements BlockRequestHelper {

    private final ConcurrentHashMap<String, Object> lockMap;

    private final ConcurrentHashMap<String, ProcessedResponsePayload> responseDataMap;

    public BlockRequestHelperImpl() {
        this.lockMap = new ConcurrentHashMap<>();
        this.responseDataMap = new ConcurrentHashMap<>();
    }

    @Override
    public void initializeLock(String requestId) {
        this.lockMap.put(requestId, new Object());
    }

    @Override
    public void lockThread(String requestId) throws InterruptedException {
        Object lockObject = this.lockMap.get(requestId);
        if (lockObject == null)
            throw new RuntimeException("Cannot lock thread '" + requestId + "'. Lock not initialized");
        synchronized (lockObject) {
            lockObject.wait();
        }
    }

    @Override
    public void unlockThread(String requestId) {
        Object lockObject = this.lockMap.get(requestId);
        if (lockObject == null)
            throw new RuntimeException("Cannot unlock thread '" + requestId + "'. Lock already released");
        synchronized (lockObject) {
            lockObject.notify();
        }
    }

    @Override
    public void setResponsePayload(String requestId, ProcessedResponsePayload processedResponsePayload) {
        this.responseDataMap.put(requestId, processedResponsePayload);
        this.unlockThread(requestId);
    }

    @Override
    public ProcessedResponsePayload removeResponsePayload(String requestId) {
        return this.responseDataMap.remove(requestId);
    }
}
