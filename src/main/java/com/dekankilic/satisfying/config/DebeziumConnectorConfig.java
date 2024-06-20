package com.dekankilic.satisfying.config;

import io.debezium.config.Configuration;
import org.springframework.context.annotation.Bean;

//@org.springframework.context.annotation.Configuration
public class DebeziumConnectorConfig {

    //@Bean
    public Configuration configuration(){
        return Configuration.create()
                .with("name", "mysql-connector") // Debezium konektorunun adini belirtir.
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("tasks.max", "1") // Eszamanli gorev sayisini belirtir.
                .with("database.hostname", "mysql-db") // MySQL veritabaninin ana bilgisayar adini belirtir.
                .with("database.port", "3306") // MySQL veritabaninin baglanti noktasini belirtir.
                .with("database.user", "root") // MySQL veritabani kullanici adini belirtir.
                .with("database.password", "s3cret") // MySQL veritabani kullanici parolasini belirtir
                .with("database.server.id", "184058")
                .with("topic.prefix", "test") // Kafka topiclerinin on ekini belirtir.
                .with("database.include.list", "satisfyingDB")
                .with("skipped.operations", "t,d") // Atlanan islem turlerini belirtir. // c for inserts/create, u for updates, d for deletes, t for truncates, and none to not skip any operations
                .with("database.allowPublicKeyRetrieval", "true")
                .with("schema.history.internal.kafka.bootstrap.servers", "kafka:9092")
                .with("schema.history.internal.kafka.topic", "restaurants")
                .build();
    }
}
