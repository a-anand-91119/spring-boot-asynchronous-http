package dev.notyouraverage.project.one.http.asynchronous_http_server.dtos.kafka.output;

import dev.notyouraverage.project.one.http.asynchronous_http_server.core.JsonSerializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessRequestPayload implements JsonSerializable {
    private String requestId;

    private String name;
}
