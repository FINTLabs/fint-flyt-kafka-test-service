package no.fintlabs;

import no.fintlabs.flyt.kafka.headers.InstanceFlowHeaders;
import no.fintlabs.model.KafkaTestMessage;
import no.fintlabs.model.KafkaTestMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static no.fintlabs.resourceserver.UrlPaths.INTERNAL_CLIENT_API;

@RestController
@RequestMapping(INTERNAL_CLIENT_API + "/message")
public class KafkaTestMessageController {

    private final KafkaTestMessageProducerService kafkaTestMessageProducerService;
    private final KafkaTestInstanceProducerService kafkaTestInstanceProducerService;

    public KafkaTestMessageController(KafkaTestMessageProducerService kafkaTestMessageProducerService, KafkaTestInstanceProducerService kafkaTestInstanceProducerService) {
        this.kafkaTestMessageProducerService = kafkaTestMessageProducerService;
        this.kafkaTestInstanceProducerService = kafkaTestInstanceProducerService;
    }

    @PostMapping("instance-dispatched")
    public Mono<ResponseEntity<?>> publishOnTopicInstanceDispathced(
            @RequestBody KafkaTestMessageDto kafkaTestProducerDto
    ) {

        InstanceFlowHeaders instanceFlowHeaders = InstanceFlowHeaders.builder()
                .sourceApplicationId(kafkaTestProducerDto.getHeaders().getSourceApplicationId())
                .sourceApplicationInstanceId(kafkaTestProducerDto.getHeaders().getSourceApplicationInstanceId())
                .archiveInstanceId(kafkaTestProducerDto.getHeaders().getArchiveInstanceId())
                .correlationId(kafkaTestProducerDto.getHeaders().getCorrelationId())
                .build();

        KafkaTestMessage kafkaTestMessage = KafkaTestMessage.builder().build();
        kafkaTestMessageProducerService.publish(instanceFlowHeaders, kafkaTestMessage);

        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).build());
    }
    @PostMapping("egrunnerverv-case-instance")
    public Mono<ResponseEntity<?>> publishOnTopicEgrunnervervCaseInstance(
            @RequestBody KafkaTestMessageDto kafkaTestProducerDto
    ) {

        KafkaTestMessage kafkaTestMessage = KafkaTestMessage.builder()
                .sysId(kafkaTestProducerDto.getBody().getSysId())
                .tableName(kafkaTestProducerDto.getBody().getTableName())
                .build();
        kafkaTestInstanceProducerService.publish(kafkaTestMessage);

        return Mono.just(ResponseEntity.status(HttpStatus.CREATED).build());
    }

}
