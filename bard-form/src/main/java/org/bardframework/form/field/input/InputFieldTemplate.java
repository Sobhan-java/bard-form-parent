package org.bardframework.form.field.input;

import org.bardframework.form.FormTemplate;
import org.bardframework.form.FormUtils;
import org.bardframework.form.exception.FormDataValidationException;
import org.bardframework.form.field.FieldTemplate;
import org.bardframework.form.field.value.FieldValueProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

public abstract class InputFieldTemplate<F extends InputField<T>, T> extends FieldTemplate<F> {

    protected boolean persistentValue = true;
    protected FieldValueProvider<F, T> valueProvider;

    protected InputFieldTemplate(String name) {
        super(name);
    }

    public InputFieldTemplate(String name, boolean persistentValue) {
        this(name);
        this.persistentValue = persistentValue;
    }

    public void validate(FormTemplate formTemplate, Map<String, String> values, Locale locale, HttpServletRequest httpRequest, FormDataValidationException ex)
            throws Exception {
        F formField = this.toField(formTemplate, values, locale, httpRequest);
        if (Boolean.TRUE.equals(formField.getDisable())) {
            return;
        }
        String stringValue = values.get(this.getName());
        if (!this.isValid(formField, this.toValue(stringValue))) {
            ex.addFiledError(this.getName(), formField.getErrorMessage());
        }
    }

    public abstract boolean isValid(F field, T value);

    public abstract T toValue(String value);

    @Override
    public void fill(FormTemplate formTemplate, F field, Map<String, String> args, Locale locale, HttpServletRequest httpRequest) throws Exception {
        super.fill(formTemplate, field, args, locale, httpRequest);
        field.setName(this.getName());
        field.setPlaceholder(FormUtils.getFieldStringProperty(formTemplate, this, "placeholder", locale, args, null));
        field.setRequired(FormUtils.getFieldBooleanProperty(formTemplate, this, "required", locale, args, null));
        field.setFocused(FormUtils.getFieldBooleanProperty(formTemplate, this, "focused", locale, args, null));
        field.setDisable(FormUtils.getFieldBooleanProperty(formTemplate, this, "disable", locale, args, null));
        field.setErrorMessage(FormUtils.getFieldStringProperty(formTemplate, this, "errorMessage", locale, args, null));
        if (null != valueProvider) {
            field.setValue(valueProvider.getValue(field));
        }
    }

    public boolean isPersistentValue() {
        return persistentValue;
    }

    public void setPersistentValue(boolean persistentValue) {
        this.persistentValue = persistentValue;
    }

    public FieldValueProvider<F, T> getValueProvider() {
        return valueProvider;
    }

    public void setValueProvider(FieldValueProvider<F, T> valueProvider) {
        this.valueProvider = valueProvider;
    }
}