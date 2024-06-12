package com.dekankilic.satisfying.config;

import io.debezium.config.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class DebeziumConnectorConfig {

    @Bean
    public Configuration configuration(){
        return Configuration.create()
                .with("name", "outbox-mysql") // Debezium konektorunun adini belirtir.
                .with("database.server.name", "outbox-poc") // Kafka tarafinda kullanilacak olan veritabani sunucusunun adini belirtir.
                .with("database.hostname", "localhost") // MySQL veritabaninin ana bilgisayar adini belirtir.
                .with("database.port", "3306") // MySQL veritabaninin baglanti noktasini belirtir.
                .with("database.user", "root") // MySQL veritabani kullanici adini belirtir.
                .with("database.password", "s3cret") // MySQL veritabani kullanici parolasini belirtir.
                .with("database.dbname", "satisfyingDB") // MySQL veritabani adini belirtir.
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector") // MySQL veritabani icin kullanilacak Debezium konektorunu belirtir.
                .with("skipped.operations", "t,d") // Atlanan islem turlerini belirtir. // c for inserts/create, u for updates, d for deletes, t for truncates, and none to not skip any operations
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore") // Offset bilgilerinin saklanacagi depo turunu belirtir.
                .with("offset.storage.file.filename", "offset.dat") // Offset bilgilerinin saklanacagi dosyanin yolunu belirtir.
                .with("offset.flush.interval.ms", 60000) // Offset bilgilerinin ne siklikta kaydedilecegini belirtir (milisaniye cinsinden).
                .with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory")
                .with("schema.history.internal.file.filename", "schistory.dat")
                .with("topic.prefix", "test") // Kafka topiclerinin on ekini belirtir.
                .with("decimal.handling.mode", "string") // Ondalik sayilari isleme modunu belirtir.
                .with("wal_level", "logical") // PostgreSQL'in Write-Ahead Logging (WAL) seviyesini belirtir.
                .with("plugin.name", "pgoutput") // Debezium tarafindan kullanilacak PostgreSQL plugin adini belirtir.
                .with("table.include.list", "public.outboxs") // Izlenmesini istediginiz PostgreSQL tablosunu belirtir.
                .with("tasks.max", "1") // Eszamanli gorev sayisini belirtir.
                .with("tombstones.on.delete", "false") // Silme islemlerini belirli bir sekilde islemenin ayarini belirtir.
                .with("route.topic.regex", "") // Konu yonlendirmesi icin regex deseni belirtir.
                .build();
    }
}
