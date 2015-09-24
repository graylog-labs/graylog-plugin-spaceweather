package org.graylog.plugins.spaceweather;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog2.plugin.LocalMetricRegistry;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;

public class SpaceweatherInput extends MessageInput {

    private static final String NAME = "Space Weather";

    @AssistedInject
    public SpaceweatherInput(MetricRegistry metricRegistry,
                        @Assisted Configuration configuration,
                        SpaceweatherTransport.Factory transportFactory,
                        LocalMetricRegistry localRegistry,
                        SpaceweatherCodec.Factory codecFactory,
                        Config config,
                        Descriptor descriptor,
                        ServerStatus serverStatus) {
        super(metricRegistry,
                configuration,
                transportFactory.create(configuration),
                localRegistry,
                codecFactory.create(configuration),
                config, descriptor, serverStatus);
    }

    @FactoryClass
    public interface Factory extends MessageInput.Factory<SpaceweatherInput> {
        @Override
        SpaceweatherInput create(Configuration configuration);

        @Override
        Config getConfig();

        @Override
        Descriptor getDescriptor();
    }

    public static class Descriptor extends MessageInput.Descriptor {
        @Inject
        public Descriptor() {
            super(NAME, true, "");
        }
    }

    @ConfigClass
    public static class Config extends MessageInput.Config {
        @Inject
        public Config(SpaceweatherTransport.Factory transport, SpaceweatherCodec.Factory codec) {
            super(transport.getConfig(), codec.getConfig());
        }
    }

}
