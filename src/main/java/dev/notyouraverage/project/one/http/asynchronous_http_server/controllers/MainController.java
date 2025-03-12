package dev.notyouraverage.project.one.http.asynchronous_http_server.controllers;

import dev.notyouraverage.project.base.configurations.RequestContext;
import dev.notyouraverage.project.base.dtos.response.wrapper.ResponseWrapper;
import dev.notyouraverage.project.one.http.asynchronous_http_server.services.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/request")
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    private final RequestContext requestContext;

    @GetMapping("send")
    public ResponseEntity<ResponseWrapper<String>> send() {
        mainService.processRequest(requestContext.getRequestId());
        return ResponseEntity.ok(ResponseWrapper.success(requestContext.getRequestId()));
    }
}
