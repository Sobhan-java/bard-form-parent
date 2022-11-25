package org.bardframework.form.field.view;

import jakarta.servlet.http.HttpServletRequest;
import org.bardframework.form.FormTemplate;
import org.bardframework.form.FormUtils;
import org.bardframework.form.field.FieldTemplate;

import java.util.Locale;
import java.util.Map;

public class MessageFieldTemplate extends FieldTemplate<MessageField> {

    public MessageFieldTemplate(String name) {
        super(name);
    }

    @Override
    public void fill(FormTemplate formTemplate, MessageField field, Map<String, String> args, Locale locale, HttpServletRequest httpRequest) throws Exception {
        super.fill(formTemplate, field, args, locale, httpRequest);
        field.setMessage(FormUtils.getFieldStringProperty(formTemplate, this, "message", locale, args, this.getDefaultValues().getMessage()));
    }

}
