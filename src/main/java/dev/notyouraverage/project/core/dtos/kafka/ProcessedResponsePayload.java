package dev.notyouraverage.project.core.dtos.kafka;

import dev.notyouraverage.project.core.JsonSerializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedResponsePayload implements JsonSerializable {
    private String requestId;

    private String name;

    private Integer count;
}
