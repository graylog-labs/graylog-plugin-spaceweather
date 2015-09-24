package org.graylog.plugins.spaceweather.parsers.ace;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SWEPAMParser {
    private static final Logger LOG = LoggerFactory.getLogger(SWEPAMParser.class);

    // http://services.swpc.noaa.gov/text/ace-swepam.txt

    public static SWEPAMMessage parse(String line) {
        String[] parts = line.replaceAll("\\s+", " ").split(" ");

        if (parts == null) {
            throw new RuntimeException("SWEPAM message does not have expected fields.");
        }

        if (parts.length != 10) {
            throw new RuntimeException("SWEPAM message does not have expected length/fields. (expected [10], got [" + parts.length + "])");
        }

        String sYear = parts[0];
        String sMonth = parts[1];
        String sDay = parts[2];
        String sTime = parts[3];
        String sStatus = parts[6];
        String sProtons = parts[7];
        String sSpeed = parts[8];
        String sIonTemp = parts[9];

        if (sProtons.equals("-9999.9") || sSpeed.equals("-9999.9") || sIonTemp.equals("-1.00e+05")) {
            LOG.debug("One of the SWEPAM readings indicated a no data status. Not parsing this data point.");
            return null;
        }

        DateTime timestamp = new DateTime(
                Integer.parseInt(sYear),
                Integer.parseInt(sMonth),
                Integer.parseInt(sDay),
                Integer.parseInt(sTime.substring(0, 2)),
                Integer.parseInt(sTime.substring(2, 4)),
                DateTimeZone.UTC
        );

        SWEPAMMessage.Status status;
        switch(Integer.parseInt(sStatus)) {
            case 0:
                status = SWEPAMMessage.Status.NOMINAL;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                status = SWEPAMMessage.Status.BAD_DATA;
                break;
            case 9:
                status = SWEPAMMessage.Status.NO_DATA;
                break;
            default:
                throw new RuntimeException("Unrecognized SWEPAM message status: " + sStatus);
        }

        return new SWEPAMMessage(
                timestamp,
                status,
                Double.parseDouble(sProtons),
                Double.parseDouble(sSpeed),
                Double.parseDouble(sIonTemp)
        );
    }

}
