package org.bardframework.form.field.value;

import jakarta.servlet.http.HttpServletRequest;
import org.bardframework.form.field.input.InputField;

public interface FieldValueProvider<F extends InputField<T>, T> {

    T getValue(F field, HttpServletRequest httpRequest);
}
