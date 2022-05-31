package org.bardframework.form.field;

import org.bardframework.form.FormTemplate;
import org.bardframework.form.FormUtils;
import org.bardframework.form.common.field.DateTimeField;
import org.bardframework.form.field.base.FormFieldTemplate;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

public class DateTimeFieldTemplate extends FormFieldTemplate<DateTimeField, LocalDateTime> {
    private boolean minIsNow;
    private boolean maxIsNow;

    protected DateTimeFieldTemplate(String name) {
        super(name);
    }

    @Override
    public boolean isValid(DateTimeField field, LocalDateTime value) {
        if (null == value && Boolean.TRUE.equals(field.getDisable())) {
            LOGGER.debug("filed [{}] is required, but it's value is empty", field.getName());
            return false;
        }
        if (null != field.getMinValue() && (null == value || value.isBefore(field.getMinValue()))) {
            LOGGER.debug("filed [{}] min value is [{}], but it's value is less than minimum", field.getName(), field.getMinValue());
            return false;
        }
        if (null != field.getMaxValue() && (null == value || value.isAfter(field.getMaxValue()))) {
            LOGGER.debug("filed [{}] max value is [{}], but it's value is greater than maximum", field.getName(), field.getMaxValue());
            return false;
        }
        return true;
    }

    @Override
    public DateTimeField getEmptyField() {
        return new DateTimeField();
    }

    @Override
    public void fill(FormTemplate formTemplate, DateTimeField field, Map<String, String> args, Locale locale) throws Exception {
        super.fill(formTemplate, field, args, locale);
        field.setMinValue(FormUtils.getLocalDateTimeValue(formTemplate, this.getName(), "minValue", locale, args));
        field.setMaxValue(FormUtils.getLocalDateTimeValue(formTemplate, this.getName(), "maxValue", locale, args));
        if (null == field.getMinValue()) {
            field.setMinValue(this.getMinValue());
        }
        if (null == field.getMaxValue()) {
            field.setMinValue(this.getMaxValue());
        }
    }

    @Override
    public LocalDateTime toValue(String value) {
        return LocalDateTime.parse(value);
    }

    public LocalDateTime getMinValue() {
        return minIsNow ? LocalDateTime.now() : null;
    }

    public LocalDateTime getMaxValue() {
        return maxIsNow ? LocalDateTime.now() : null;
    }

    public boolean isMinIsNow() {
        return minIsNow;
    }

    public void setMinIsNow(boolean minIsNow) {
        this.minIsNow = minIsNow;
    }

    public boolean isMaxIsNow() {
        return maxIsNow;
    }

    public void setMaxIsNow(boolean maxIsNow) {
        this.maxIsNow = maxIsNow;
    }
}