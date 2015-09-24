package org.graylog.plugins.spaceweather;

import org.graylog2.plugin.Plugin;
import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.PluginModule;

import java.util.Arrays;
import java.util.Collection;

public class SpaceweatherInputPlugin implements Plugin {
    @Override
    public PluginMetaData metadata() {
        return new SpaceweatherInputMetaData();
    }

    @Override
    public Collection<PluginModule> modules () {
        return Arrays.<PluginModule>asList(new SpaceweatherInputModule());
    }
}
