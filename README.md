## Graylog Spaceweather plugin (Solar data)

#### (This is not actually providing any value at all - except fun and the possibility to decently nerd out )

**You can also find this plugin on the [Graylog Marketplace](https://marketplace.graylog.org/addons/8adb2876-bdd6-4163-8a39-f218086f6cde).**

This [Graylog](https://www.graylog.org/) input plugin reads data from the [Advanced Composition Explorer (ACE)](https://en.wikipedia.org/wiki/Advanced_Composition_Explorer) spacecraft which was launched in 1997 and is positioned at the so called [L1 Lagrangian point](https://en.wikipedia.org/wiki/Lagrangian_point#L1) where the gravity attraction of the Sun counters the gravity attraction of Earth.

Together with several other missions, the ACE spacecraft is sitting there and constantly recording metrics about the stuff that the Sun is throwing out in the direction of the Earth using the Solar Wind Electron, Proton and Alpha Monitor (SWEPAM). **This plugin is reading data from SWEPAM**.

### What.

Yes. You can now correlate your system health with solar activity and geomagnetic storms. Ever needed a proof that a solar storm made a bit flip and your systems crash? Now you can! Correlate proton density to the response time of your app and the ion temperature to your exception rate. 500% more useful on dashboards!

> The ultimate Friday thing to do.  ¯\\_(ツ)_/¯

### What.

![](https://github.com/Graylog2/graylog-plugin-spaceweather/blob/master/screen1.png)

![](https://github.com/Graylog2/graylog-plugin-spaceweather/blob/master/screen2.png)

## Installation

[Download the plugin](https://github.com/Graylog2/graylog-plugin-spaceweather//releases)
and place the `.jar` file in your Graylog plugin directory. The plugin directory
is the `plugins/` folder relative from your `graylog-server` directory by default
and can be configured in your `graylog.conf` file.

Restart `graylog-server`, start the input using the web interface and you are done.
