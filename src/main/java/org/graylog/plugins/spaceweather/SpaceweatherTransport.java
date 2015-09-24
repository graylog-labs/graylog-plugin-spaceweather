package org.graylog.plugins.spaceweather;

import com.codahale.metrics.MetricSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.MisfireException;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;
import org.graylog2.plugin.inputs.codecs.CodecAggregator;
import org.graylog2.plugin.inputs.transports.Transport;
import org.graylog2.plugin.journal.RawMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpaceweatherTransport implements Transport {
    private static final Logger LOG = LoggerFactory.getLogger(SpaceweatherTransport.class);

    public static String NAME = "spaceweather";

    protected final URL urlACESWEPAM;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @AssistedInject
    public SpaceweatherTransport(@Assisted final Configuration configuration) {
        try {
            this.urlACESWEPAM = new URL("http://services.swpc.noaa.gov/text/ace-swepam.txt");
        } catch (MalformedURLException e) {
            // Cannot happen(tm)
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setMessageAggregator(CodecAggregator codecAggregator) {
        // nope
    }

    @Override
    public void launch(MessageInput messageInput) throws MisfireException {
        scheduler.scheduleAtFixedRate(readDataFromNOAA(messageInput), 0, 60, TimeUnit.SECONDS);
    }

    private Runnable readDataFromNOAA(final MessageInput messageInput) {
        return new Runnable() {
            @Override
            public void run() {
                StringBuilder response = new StringBuilder();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlACESWEPAM.openStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                    in.close();
                } catch (Exception e) {
                    LOG.error("Could not read space weather information from NOAA.", e);
                    return;
                }

                messageInput.processRawMessage(new RawMessage(response.toString().getBytes()));
            }
        };
    }

    @Override
    public void stop() {
        // no longer get ze data here
    }

    @Override
    public MetricSet getMetricSet() {
        return null;
    }

    @FactoryClass
    public interface Factory extends Transport.Factory<SpaceweatherTransport> {
        @Override
        SpaceweatherTransport create(Configuration configuration);

        @Override
        Config getConfig();
    }

    @ConfigClass
    public static class Config implements Transport.Config {

        @Override
        public ConfigurationRequest getRequestedConfiguration() {
            return new ConfigurationRequest();
        }

    }


}
