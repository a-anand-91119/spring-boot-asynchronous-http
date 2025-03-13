package dev.notyouraverage.project.one.http.asynchronous_http_server.services;

import jakarta.validation.constraints.NotBlank;

public interface MainService {

    void processRequest(String requestId, @NotBlank String name);
}
