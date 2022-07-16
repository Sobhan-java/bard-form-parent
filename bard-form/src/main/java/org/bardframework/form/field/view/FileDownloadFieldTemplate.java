package org.bardframework.form.field.view;

import org.bardframework.form.FormTemplate;
import org.bardframework.form.FormUtils;
import org.bardframework.form.field.FieldTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

public class FileDownloadFieldTemplate extends FieldTemplate<FileDownloadField> {

    public FileDownloadFieldTemplate(String name) {
        super(name);
    }

    @Override
    public void fill(FormTemplate formTemplate, FileDownloadField field, Map<String, String> args, Locale locale, HttpServletRequest httpRequest) throws Exception {
        super.fill(formTemplate, field, args, locale, httpRequest);
        field.setUrl(FormUtils.getFieldStringProperty(formTemplate, this, "url", locale, args, this.getDefaultValue().getUrl()));
        field.setFileName(FormUtils.getFieldStringProperty(formTemplate, this, "fileName", locale, args, this.getDefaultValue().getFileName()));
        field.setContentType(FormUtils.getFieldStringProperty(formTemplate, this, "contentType", locale, args, this.getDefaultValue().getContentType()));
        field.setSize(FormUtils.getFieldIntegerProperty(formTemplate, this, "size", locale, args, this.getDefaultValue().getSize()));
    }

}
