package dev.notyouraverage.project.one.http.asynchronous_http_server.utils;

import dev.notyouraverage.project.base.exceptions.AppException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompletableFutureUtils {
    
    public static <T> T unchekedGet(CompletableFuture<T> completableFuture) {
        try {
            return completableFuture.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AppException(e);
        } catch (ExecutionException e) {
            throw new AppException(e);
        }
    }
}