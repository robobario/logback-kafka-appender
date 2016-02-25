package com.github.danielwegener.logback.kafka.encoding;

import ch.qos.logback.core.spi.DeferredProcessingAware;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.AbstractFieldJsonProvider;
import net.logstash.logback.composite.FieldNamesAware;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.fieldnames.LogstashCommonFieldNames;

import java.io.IOException;

public class HostnameProvider<Event extends DeferredProcessingAware> extends AbstractFieldJsonProvider<Event>
implements FieldNamesAware<LogstashCommonFieldNames> {

    public HostnameProvider(String hostname) {
        setFieldName(hostname);
    }


    @Override
    public void writeTo(JsonGenerator generator, Event event) throws IOException {
        String fieldName = getFieldName();
        if (getContext() != null && fieldName != null) {
            String hostname = context.getProperty("HOSTNAME");
            if (hostname != null) {
                JsonWritingUtils.writeStringField(generator, fieldName, hostname);
            }
        }
    }


    @Override
    public void setFieldNames(LogstashCommonFieldNames fieldNames) {

    }

}
