package com.caej.product.service.field;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import com.caej.insurance.api.insurance.InsurancePeriodUnit;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.domain.Product;
import com.caej.product.service.FormContext;

import io.sited.form.Form;
import io.sited.form.type.AbstractFieldType;
import io.sited.http.exception.BadRequestException;

/**
 * @author chi
 */
public class BirthDateFieldType extends AbstractFieldType<LocalDate> {
    public BirthDateFieldType() {
        super(LocalDate.class);
    }

    @Override
    public String name() {
        return "BirthDate";
    }

    @Override
    public boolean validate(Object instance, Context context) {
        Form form = (Form) context.root;
        Form.Group group = (Form.Group) context.current;

        FormContext formContext = form.context(FormContext.class);
        Product product = formContext.product;
        LocalDate birthDate = (LocalDate) instance;

//        if (!super.validate(instance, context)) {
//            return false;
//        }

        if (group.fieldGroup.name.equals("insured")) {
            return validate(birthDate, product.insuredMinAge, product.insuredMaxAge, context);
        } else if (group.fieldGroup.name.equals("policy-holder")) {
            return validate(birthDate, product.policyHolderMinAge, product.policyHolderMaxAge, context);
        } else {
            return true;
        }
    }

    private boolean validate(LocalDate birthDate, InsurancePeriod start, InsurancePeriod end, Context context) {
        LocalDate now = LocalDate.now();
        boolean valid = true;
        if (start != null && start.unit == InsurancePeriodUnit.AGE_DAY) {
            long days = Period.between(birthDate, now).get(ChronoUnit.DAYS);
            if (days < start.value) {
                valid = false;
                context.invalidFields.add(BadRequestException.InvalidField.of(context.path, null, "年龄必须大于" + start.displayName));
            }
        } else if (start != null && start.unit == InsurancePeriodUnit.AGE) {
            long years = Period.between(birthDate, now).get(ChronoUnit.YEARS);
            if (years < start.value) {
                valid = false;
                context.invalidFields.add(BadRequestException.InvalidField.of(context.path, null, "年龄必须大于" + start.displayName));
            }
        }

        if (end != null && end.unit == InsurancePeriodUnit.AGE_DAY) {
            long days = Period.between(birthDate, now).get(ChronoUnit.DAYS);
            if (days > end.value) {
                valid = false;
                context.invalidFields.add(BadRequestException.InvalidField.of(context.path, null, "年龄必须小于" + end.displayName));
            }
        } else if (end != null && end.unit == InsurancePeriodUnit.AGE) {
            long years = Period.between(birthDate, now).get(ChronoUnit.YEARS);
            if (years > end.value) {
                valid = false;
                context.invalidFields.add(BadRequestException.InvalidField.of(context.path, null, "年龄必须小于" + end.displayName));
            }
        }
        return valid;
    }
}
