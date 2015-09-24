package org.graylog.plugins.spaceweather;

import org.graylog2.plugin.PluginConfigBean;
import org.graylog2.plugin.PluginModule;

import java.util.Collections;
import java.util.Set;


public class SpaceweatherInputModule extends PluginModule {

    @Override
    public Set<? extends PluginConfigBean> getConfigBeans() {
        return Collections.emptySet();
    }

    @Override
    protected void configure() {
        installCodec(codecMapBinder(), SpaceweatherCodec.NAME, SpaceweatherCodec.class);
        installTransport(transportMapBinder(), SpaceweatherTransport.NAME, SpaceweatherTransport.class);
        installInput(inputsMapBinder(), SpaceweatherInput.class, SpaceweatherInput.Factory.class);
    }

}
