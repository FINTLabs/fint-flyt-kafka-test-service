package no.fintlabs;

import no.fintlabs.flyt.kafka.event.InstanceFlowEventProducer;
import no.fintlabs.flyt.kafka.event.InstanceFlowEventProducerFactory;
import no.fintlabs.flyt.kafka.event.InstanceFlowEventProducerRecord;
import no.fintlabs.flyt.kafka.headers.InstanceFlowHeaders;
import no.fintlabs.kafka.event.topic.EventTopicNameParameters;
import no.fintlabs.kafka.event.topic.EventTopicService;
import no.fintlabs.model.KafkaTestMessage;
import org.springframework.stereotype.Service;

@Service
public class KafkaTestMessageProducerService {


    private final InstanceFlowEventProducer<KafkaTestMessage> kafkaTestMessageInstanceFlowEventProducer;
    private final EventTopicNameParameters eventTopicNameParameters;

    public KafkaTestMessageProducerService(
            InstanceFlowEventProducerFactory instanceFlowEventProducerFactory,
            EventTopicService eventTopicService

    ) {
        this.kafkaTestMessageInstanceFlowEventProducer = instanceFlowEventProducerFactory.createProducer(KafkaTestMessage.class);
        this.eventTopicNameParameters = EventTopicNameParameters.builder()
                .eventName("instance-dispatched")
                .build();
        eventTopicService.ensureTopic(eventTopicNameParameters, 0);

    }


    public void publish(
            InstanceFlowHeaders instanceFlowHeaders,
            KafkaTestMessage kafkaTestMessage
    ) {
        kafkaTestMessageInstanceFlowEventProducer.send(
                InstanceFlowEventProducerRecord.<KafkaTestMessage>builder()
                        .topicNameParameters(eventTopicNameParameters)
                        .instanceFlowHeaders(instanceFlowHeaders)
                        .value(kafkaTestMessage)
                        .build()
        );

    }
}