package no.fintlabs.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class KafkaTestMessage {
    private final String sysId;
    private final String tableName;

}
