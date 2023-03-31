package no.fintlabs;

import no.fintlabs.kafka.event.EventProducer;
import no.fintlabs.kafka.event.EventProducerFactory;
import no.fintlabs.kafka.event.EventProducerRecord;
import no.fintlabs.kafka.event.topic.EventTopicNameParameters;
import no.fintlabs.kafka.event.topic.EventTopicService;
import no.fintlabs.model.KafkaTestMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SimpleSakInstanceProducerService {
    @Value("${fint.flyt.egrunnerverv.retentionTimeInDays:30}")
    private Long retentionTimeInDays;

    private final EventProducer<KafkaTestMessage> simpleJournalpostInstanceEventProducer;

    private final EventTopicNameParameters topicNameParameters;

    public SimpleSakInstanceProducerService(
            EventProducerFactory eventProducerFactory,
            EventTopicService eventTopicService
    ) {
        this.simpleJournalpostInstanceEventProducer = eventProducerFactory.createProducer(KafkaTestMessage.class);
        this.topicNameParameters = EventTopicNameParameters.builder()
                .eventName("egrunnerverv-sak-instance")
                .build();
        if (retentionTimeInDays != null) {
            eventTopicService.ensureTopic(topicNameParameters, Duration.ofDays(retentionTimeInDays).toMillis());
        }
    }


    public void publish(KafkaTestMessage simpleJournalpostInstance) {
        simpleJournalpostInstanceEventProducer.send(
                EventProducerRecord.<KafkaTestMessage>builder()
                        .topicNameParameters(topicNameParameters)
                        .value(simpleJournalpostInstance)
                        .build()
        );
    }


}


