package com.github.danielwegener.logback.kafka.encoding;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Context;
import net.logstash.logback.LogstashFormatter;
import net.logstash.logback.composite.ContextJsonProvider;
import net.logstash.logback.composite.JsonProviders;
import net.logstash.logback.composite.loggingevent.ContextNameJsonProvider;

import java.io.IOException;

public class LogstashKafkaMessageEncoder<E> extends KafkaMessageEncoderBase<E> {

    public static final byte[] EMPTY = new byte[] {};

    private LogstashFormatter formatter = new LogstashFormatter(this, true);


    public LogstashKafkaMessageEncoder() {
        formatter.getFieldNames().setContext("app");
        formatter.setIncludeContext(false);
        formatter.getProviders().addProvider(new ContextNameJsonProvider());
        formatter.getProviders().addProvider(new HostnameProvider<ILoggingEvent>("hostname"));
        formatter.setIncludeMdc(false);
        formatter.setTimeZone("UTC");
    }


    @Override
    public void setContext(Context context) {
        super.setContext(context);
        formatter.setContext(context);
    }


    @Override
    public byte[] doEncode(Object event) {
        if (event instanceof ILoggingEvent) {
            try {
                return formatter.writeEventAsBytes((ILoggingEvent) event);
            }
            catch (IOException e) {
                // add a counter
                return EMPTY;
            }
        }
        else {
            return EMPTY;
        }
    }


    @Override
    public void start() {
        formatter.start();
        super.start();
    }


    @Override
    public void stop() {
        formatter.stop();
        super.stop();
    }

}
