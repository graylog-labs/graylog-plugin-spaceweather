package org.graylog.plugins.spaceweather.parsers.ace;

import org.joda.time.DateTime;

public class SWEPAMMessage {

    public enum Status {
        NOMINAL,
        BAD_DATA,
        NO_DATA
    }

    // Metadata and status.
    protected final DateTime timestamp;
    protected final Status status;

    // Solar Wind.
    protected final double protonDensity;
    protected final double bulkSpeed;
    protected final double ionTemperature;

    public SWEPAMMessage(DateTime timestamp, Status status, double protonDensity, double bulkSpeed, double ionTemperature) {
        this.timestamp = timestamp;
        this.status = status;
        this.protonDensity = protonDensity;
        this.bulkSpeed = bulkSpeed;
        this.ionTemperature = ionTemperature;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder("ACE-SWEPAM reading - ")
                .append("proton density: ").append(getProtonDensity()).append("p/ccm, ")
                .append("bulk speed: ").append(getBulkSpeed()).append("km/s, ")
                .append("ion temp: ").append(getIonTemperature()).append("K");

        return sb.toString();
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public Status getStatus() {
        return status;
    }

    public double getProtonDensity() {
        return protonDensity;
    }

    public double getBulkSpeed() {
        return bulkSpeed;
    }

    public double getIonTemperature() {
        return ionTemperature;
    }

}
