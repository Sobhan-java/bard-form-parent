package org.bardframework.form.field;

import org.bardframework.form.common.Field;

public class PaymentField extends Field {

    private Long amount;
    private String description;

    public PaymentField() {
    }

    @Override
    public FieldType getType() {
        return FieldType.PAYMENT;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}