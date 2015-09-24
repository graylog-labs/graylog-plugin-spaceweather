package org.graylog.plugins.spaceweather;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog.plugins.spaceweather.parsers.ace.SWEPAMMessage;
import org.graylog.plugins.spaceweather.parsers.ace.SWEPAMParser;
import org.graylog2.plugin.Message;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.inputs.annotations.Codec;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;
import org.graylog2.plugin.inputs.codecs.AbstractCodec;
import org.graylog2.plugin.journal.RawMessage;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Codec(name="spaceweather", displayName = "Space Weather")
public class SpaceweatherCodec extends AbstractCodec {
    private static final Logger LOG = LoggerFactory.getLogger(SpaceweatherCodec.class);

    public static String NAME = "spaceweather";

    @AssistedInject
    protected SpaceweatherCodec(@Assisted Configuration configuration,
                        MetricRegistry metricRegistry) {
        super(configuration);
    }

    @Nullable
    @Override
    public Message decode(RawMessage rawMessage) {
        String response = new String(rawMessage.getPayload());
        String[] lines = response.split("\n");

        if (lines == null) {
            LOG.warn("Could nor parse space weather payload.");
            return null;
        }

        // The data is always in the second last line of data points.
        String dataLine = lines[lines.length-2];
        if(dataLine == null || dataLine.isEmpty()) {
            LOG.warn("Space weather payload line not found.");
            return null;
        }

        SWEPAMMessage message = SWEPAMParser.parse(dataLine);

        if(message.getStatus() != SWEPAMMessage.Status.NOMINAL) {
            LOG.debug("SWEPAM message status is no [NOMINAL] but [{}]. Skipping data point.", message.getStatus().toString());
            return null;
        }

        Message log = new Message(message.getSummary(), "solardata", DateTime.now(DateTimeZone.UTC));
        log.addField("satellite", "ace");
        log.addField("instrument", "swepam");
        log.addField("data_integrity", message.getStatus().toString().toLowerCase());
        log.addField("reading_timestamp", message.getTimestamp().toString());
        log.addField("proton_density", message.getProtonDensity());
        log.addField("bulk_speed", message.getBulkSpeed());
        log.addField("ion_temp", message.getIonTemperature());

        return log;
    }

    @FactoryClass
    public interface Factory extends AbstractCodec.Factory<SpaceweatherCodec> {
        @Override
        SpaceweatherCodec create(Configuration configuration);

        @Override
        Config getConfig();
    }

    @ConfigClass
    public static class Config extends AbstractCodec.Config {
        @Override
        public ConfigurationRequest getRequestedConfiguration() {
            final ConfigurationRequest cr = super.getRequestedConfiguration();

            return cr;
        }

        @Override
        public void overrideDefaultValues(@Nonnull ConfigurationRequest cr) {
        }
    }

}
