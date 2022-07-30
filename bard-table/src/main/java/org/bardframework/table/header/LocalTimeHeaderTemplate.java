package org.bardframework.table.header;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeHeaderTemplate extends HeaderTemplate<TimeHeader, LocalTime> {

    private final DateTimeFormatter formatter;

    public LocalTimeHeaderTemplate(String formatterPattern) {
        this.formatter = DateTimeFormatter.ofPattern(formatterPattern);
    }

    @Override
    public LocalTime parse(String value, Locale locale) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return LocalTime.parse(value);
    }

    @Override
    public Object format(LocalTime value, Locale locale, MessageSource messageSource) {
        if (null == value) {
            return null;
        }
        return this.getFormatter().withLocale(locale).format(value);
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
