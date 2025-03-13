package dev.notyouraverage.project.dtos.kafka;

import dev.notyouraverage.project.one.http.asynchronous_http_server.core.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessRequestPayload implements JsonSerializable {
    private String requestId;

    private String name;
}
