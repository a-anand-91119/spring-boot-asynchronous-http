package dev.notyouraverage.project.one.http.asynchronous_http_server.services;

import dev.notyouraverage.project.one.http.asynchronous_http_server.dtos.response.ProcessedRequestResponse;
import jakarta.validation.constraints.NotBlank;

public interface MainService {

    ProcessedRequestResponse processRequest(String requestId, @NotBlank String name);
}
