package no.fintlabs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
public class KafkaTestMessageDto {
    @JsonProperty("headers")
    private Headers headers;

    @JsonProperty("body")
    private Body body;


    @Getter
    public static class Headers {
        @JsonProperty("sourceApplicationId")
        private Long sourceApplicationId;

        @JsonProperty("sourceApplicationIntegrationId")
        private String sourceApplicationIntegrationId;

        @JsonProperty("sourceApplicationInstanceId")
        private String sourceApplicationInstanceId;

        @JsonProperty("correlationId")
        private UUID correlationId;

        @JsonProperty("integrationId")
        private Long integrationId;

        @JsonProperty("instanceId")
        private Long instanceId;

        @JsonProperty("configurationId")
        private Long configurationId;

        @JsonProperty("archiveInstanceId")
        private String archiveInstanceId;
    }
    @Getter
    public static class Body {
        @JsonProperty("sys_id")
        private String sysId;

        @JsonProperty("table")
        private String tableName;
    }

}
