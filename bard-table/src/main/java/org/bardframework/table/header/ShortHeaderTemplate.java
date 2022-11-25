package org.bardframework.table.header;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class ShortHeaderTemplate extends HeaderTemplate<StringHeader, Short> {

    @Override
    public String format(Short value, Locale locale, MessageSource messageSource) {
        if (null == value) {
            return null;
        }
        return value.toString();
    }
}