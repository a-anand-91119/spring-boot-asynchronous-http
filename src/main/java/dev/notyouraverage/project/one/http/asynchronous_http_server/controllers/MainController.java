package dev.notyouraverage.project.one.http.asynchronous_http_server.controllers;

import dev.notyouraverage.project.base.configurations.RequestContext;
import dev.notyouraverage.project.base.dtos.response.wrapper.ResponseWrapper;
import dev.notyouraverage.project.one.http.asynchronous_http_server.dtos.response.ProcessedRequestResponse;
import dev.notyouraverage.project.one.http.asynchronous_http_server.services.MainService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/request")
public class MainController {

    private final MainService mainService;

    private final RequestContext requestContext;

    @GetMapping("send")
    public ResponseEntity<ResponseWrapper<ProcessedRequestResponse>> send(@RequestParam("name") @NotBlank String name) {
        ProcessedRequestResponse response = mainService.processRequest(requestContext.getRequestId(), name);
        return ResponseEntity.ok(ResponseWrapper.success(response));
    }
}
