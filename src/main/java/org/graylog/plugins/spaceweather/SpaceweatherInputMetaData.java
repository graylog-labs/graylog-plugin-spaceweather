package org.graylog.plugins.spaceweather;

import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.Version;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

public class SpaceweatherInputMetaData implements PluginMetaData {

    @Override
    public String getUniqueId() {
        return "org.graylog.plugins.spaceweather.SpaceweatherInputPlugin";
    }

    @Override
    public String getName() {
        return "Space weather input";
    }

    @Override
    public String getAuthor() {
        return "Lennart Koopmann <lennart@graylog.com>";
    }

    @Override
    public URI getURL() {
        return URI.create("https://www.graylog.org/");
    }

    @Override
    public Version getVersion() {
        return new Version(1, 1, 0);
    }

    @Override
    public String getDescription() {
        return "Ever needed a proof that a solar storm made a bit flip and your code crash? Now you can!";
    }

    @Override
    public Version getRequiredVersion() {
        return new Version(2, 0, 0);
    }

    @Override
    public Set<ServerStatus.Capability> getRequiredCapabilities() {
        return Collections.emptySet();
    }

}
