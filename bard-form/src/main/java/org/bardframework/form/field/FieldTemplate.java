package org.bardframework.form.field;

import lombok.extern.slf4j.Slf4j;
import org.bardframework.commons.utils.ReflectionUtils;
import org.bardframework.form.FormTemplate;
import org.bardframework.form.FormUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Locale;
import java.util.Map;

@Slf4j
public abstract class FieldTemplate<F extends Field> {

    protected final Class<F> fieldClazz;
    protected final String name;
    protected Expression showExpression = null;
    protected F defaultValues;

    protected FieldTemplate(String name) {
        this.name = name;
        this.fieldClazz = ReflectionUtils.getGenericArgType(this.getClass(), 0);
        this.defaultValues = ReflectionUtils.newInstance(this.fieldClazz);
    }

    public F toField(FormTemplate formTemplate, Map<String, String> args, Locale locale) throws Exception {
        F field = this.getEmptyField();
        this.fill(formTemplate, field, args, locale);
        return field;
    }

    protected void fill(FormTemplate formTemplate, F field, Map<String, String> args, Locale locale) throws Exception {
        field.setTitle(FormUtils.getFieldStringProperty(formTemplate, this, "title", locale, args, this.getDefaultValues().getTitle()));
        field.setDescription(FormUtils.getFieldStringProperty(formTemplate, this, "description", locale, args, this.getDefaultValues().getDescription()));
    }

    protected F getEmptyField() {
        return ReflectionUtils.newInstance(this.fieldClazz);
    }

    public String getName() {
        return name;
    }

    public void setShowExpression(String showExpression) {
        this.showExpression = new SpelExpressionParser(new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null)).parseExpression(showExpression);
    }

    public boolean mustShow(Map<String, String> args) {
        return null == showExpression || Boolean.TRUE.equals(showExpression.getValue(new StandardEvaluationContext(args), Boolean.class));
    }

    public F getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(F defaultValues) {
        this.defaultValues = defaultValues;
    }
}
