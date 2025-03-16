package dev.notyouraverage.project.one.http.asynchronous_http_server.dtos.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedRequestResponse {
    private String requestId;
    private String name;
    private Integer count;
}
