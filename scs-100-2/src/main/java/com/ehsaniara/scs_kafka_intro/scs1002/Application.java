package com.ehsaniara.scs_kafka_intro.scs1002;

import org.apache.kafka.common.serialization.Serde;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.stream.binder.kafka.streams.KafkaStreamsRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application {

    final static String STATE_STORE_NAME = "scs-100-2-order-events";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    ApplicationListener<ApplicationReadyEvent> runnerListener(KafkaStreamsRegistry registry)
    {
    	return (event) -> {
    		registry.streamsBuilderFactoryBeans().stream().forEach((streamsBuilderFB) -> {
    			log.info("Topology\r\n {}",streamsBuilderFB.getTopology().describe());
    		});
    	};
    }

    @Bean
    public Serde<Order> orderJsonSerde() {
        return new JsonSerde<>(Order.class, new ObjectMapper());
    }

}
