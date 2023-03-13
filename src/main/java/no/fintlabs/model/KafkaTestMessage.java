package no.fintlabs.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class KafkaTestMessage {
    private final String sysId;
    private final String tableName;

}
