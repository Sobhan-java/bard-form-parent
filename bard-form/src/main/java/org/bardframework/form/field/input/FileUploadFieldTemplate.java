package org.bardframework.form.field.input;

import org.bardframework.form.FormTemplate;
import org.bardframework.form.FormUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

public class FileUploadFieldTemplate extends InputFieldTemplate<FileUploadField, String> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(FileUploadFieldTemplate.class);

    private final String uploadAction;
    private final String downloadAction;

    public FileUploadFieldTemplate(String name, String uploadAction, String downloadAction) {
        super(name, true);
        this.uploadAction = uploadAction;
        this.downloadAction = downloadAction;
    }

    @Override
    public void fill(FormTemplate formTemplate, FileUploadField field, Map<String, String> args, Locale locale, HttpServletRequest httpRequest) throws Exception {
        super.fill(formTemplate, field, args, locale, httpRequest);
        field.setMinSize(FormUtils.getFieldIntegerProperty(formTemplate, this, "minSize", locale, args, this.getDefaultValue().getMinSize()));
        field.setMaxSize(FormUtils.getFieldIntegerProperty(formTemplate, this, "maxSize", locale, args, this.getDefaultValue().getMaxSize()));
        field.setContentTypes(FormUtils.getFieldListProperty(formTemplate, this, "contentTypes", locale, args, this.getDefaultValue().getContentTypes()));
        field.setUploadAction(this.getUploadAction());
        field.setDownloadAction(this.getDownloadAction());
    }

    @Override
    public boolean isValid(FileUploadField field, String value) {
        //TODO not implement
        return true;
    }

    @Override
    public String toValue(String value) {
        return value;
    }

    public String getDownloadAction() {
        return downloadAction;
    }

    public String getUploadAction() {
        return uploadAction;
    }
}
