package com.caej.product.service.field;

import java.util.List;

import javax.inject.Inject;

import com.caej.product.service.client.InsuranceCountryWebServiceClient;
import com.google.common.collect.Lists;

import io.sited.form.EnumConstant;
import io.sited.form.FieldOption;
import io.sited.form.type.AbstractFieldType;
import io.sited.http.exception.BadRequestException;

/**
 * @author miller
 */
public class NationalityFieldType extends AbstractFieldType<String> {
    @Inject
    InsuranceCountryWebServiceClient insuranceCountryWebService;

    public NationalityFieldType() {
        super(String.class);
    }

    @Override
    public String name() {
        return "Nationality";
    }

    @Override
    public boolean validate(Object value, Context context) {
        if (!super.validate(value, context)) {
            return false;
        }

        String enumValue = (String) value;
        if (enumValue == null) {
            return true;
        }

        List<EnumConstant> enumConstants = Lists.newArrayList();
        insuranceCountryWebService.country().list
            .forEach(insuranceCountryResponse -> {
                EnumConstant enumConstant = new EnumConstant();
                enumConstant.displayName = insuranceCountryResponse.name;
                enumConstant.value = insuranceCountryResponse.countryCode;
                enumConstants.add(enumConstant);
            });
        for (EnumConstant enumConstant : enumConstants) {
            if (enumValue.equals(enumConstant.value)) {
                return true;
            }
        }

        FieldOption<String> message = option("enumMessage");
        context.invalidFields.add(BadRequestException.InvalidField.of(context.path, "validator.error.enum", message.value(context.options), enumValue));
        return false;
    }
}
