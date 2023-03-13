package no.fintlabs;

import no.fintlabs.kafka.event.EventProducer;
import no.fintlabs.kafka.event.EventProducerFactory;
import no.fintlabs.kafka.event.EventProducerRecord;
import no.fintlabs.kafka.event.topic.EventTopicNameParameters;
import no.fintlabs.kafka.event.topic.EventTopicService;
import no.fintlabs.model.KafkaTestMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaTestInstanceProducerService {
    @Value("${fint.flyt.egrunnerverv.retentionTimeInDays:30}")
    private Long retentionTimeInDays;

    private final EventProducer<KafkaTestMessage> simpleCaseInstanceEventProducer;

    private final EventTopicNameParameters topicNameParameters;

    public KafkaTestInstanceProducerService(
            EventProducerFactory eventProducerFactory,
            EventTopicService eventTopicService
    ) {
        this.simpleCaseInstanceEventProducer = eventProducerFactory.createProducer(KafkaTestMessage.class);
        this.topicNameParameters = EventTopicNameParameters.builder()
                .eventName("egrunnerverv-case-instance")
                .build();
        eventTopicService.ensureTopic(topicNameParameters, 0);

    }


    public void publish(KafkaTestMessage simpleCaseInstance) {
        simpleCaseInstanceEventProducer.send(
                EventProducerRecord.<KafkaTestMessage>builder()
                        .topicNameParameters(topicNameParameters)
                        .value(simpleCaseInstance)
                        .build()
        );
    }


}


