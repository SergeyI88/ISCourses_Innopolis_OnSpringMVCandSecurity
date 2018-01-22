package db.logger;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Date;

public class MyLayoutJSON extends PatternLayout {
    @Override
    public String format(LoggingEvent event) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{ level : ");
        stringBuffer.append(event.getLevel().toString());
        stringBuffer.append(", message : ");
        stringBuffer.append(event.getMessage().toString());
        stringBuffer.append(", location : ");
        stringBuffer.append(event.getLocationInformation().fullInfo);
        stringBuffer.append(", time : ");
        stringBuffer.append(new Date(event.timeStamp).toString());
        stringBuffer.append(" }");

        return stringBuffer.toString();
    }
}
