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
public class SimpleJournalpostInstanceProducerService {
    @Value("${fint.flyt.egrunnerverv.retentionTimeInDays:30}")
    private Long retentionTimeInDays;

    private final EventProducer<KafkaTestMessage> simpleSakInstanceEventProducer;

    private final EventTopicNameParameters topicNameParameters;

    public SimpleJournalpostInstanceProducerService(
            EventProducerFactory eventProducerFactory,
            EventTopicService eventTopicService
    ) {
        this.simpleSakInstanceEventProducer = eventProducerFactory.createProducer(KafkaTestMessage.class);
        this.topicNameParameters = EventTopicNameParameters.builder()
                .eventName("egrunnerverv-journalpost-instance")
                .build();
        if (retentionTimeInDays != null) {
            eventTopicService.ensureTopic(topicNameParameters, Duration.ofDays(retentionTimeInDays).toMillis());
        }
    }


    public void publish(KafkaTestMessage simpleSakInstance) {
        simpleSakInstanceEventProducer.send(
                EventProducerRecord.<KafkaTestMessage>builder()
                        .topicNameParameters(topicNameParameters)
                        .value(simpleSakInstance)
                        .build()
        );
    }


}


