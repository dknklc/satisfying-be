package com.dekankilic.satisfying.util;

import com.dekankilic.satisfying.service.RestaurantOutboxService;
import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static io.debezium.data.Envelope.FieldName.AFTER;
import static io.debezium.data.Envelope.FieldName.OPERATION;

@Component
@Slf4j
public class DebeziumSourceEventListener {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final EmbeddedEngine debeziumEngine;
    private final RestaurantOutboxService outboxService;

    @PostConstruct
    public void start() {
        executor.execute(debeziumEngine);
    }

    @PreDestroy
    public void stop() {
        if (debeziumEngine != null) {
            debeziumEngine.stop();
        }
    }

    public DebeziumSourceEventListener(Configuration configuration, RestaurantOutboxService outboxService) {
        this.debeziumEngine = EmbeddedEngine.create()
                .using(configuration)
                .notifying(this::handleEvent)
                .build();
        this.outboxService = outboxService;
    }


    private void handleEvent(SourceRecord sourceRecord) {

        Struct sourceRecordValue = (Struct) sourceRecord.value();
        var crudOperation = (String) sourceRecordValue.get(OPERATION);

        if (sourceRecordValue != null && (crudOperation == "c" || crudOperation == "u")) {
            Struct struct = (Struct) sourceRecordValue.get(AFTER);

            log.info(struct.toString());
            // Call Elasticsearch's handleEvent method

//            Struct struct = (Struct) sourceRecordValue.get(AFTER);
//            Map<String, Object> payload = struct.schema().fields().stream()
//                    .filter(field -> struct.get(field) != null)
//                    .collect(Collectors.toMap(Field::name, field -> struct.get(field)));

            // outboxService.debeziumDatabaseChange(payload);
        }
    }
}
