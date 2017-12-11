package com.caej.product.service.field;

import java.util.List;

import javax.inject.Inject;

import com.caej.product.service.client.EnumBeneficiaryTypeWebServiceClient;
import com.google.common.collect.Lists;

import io.sited.form.EnumConstant;
import io.sited.form.FieldOption;
import io.sited.form.type.AbstractFieldType;
import io.sited.http.exception.BadRequestException;

/**
 * @author miller
 */
public class BenefitTypeFieldType extends AbstractFieldType<String> {
    @Inject
    EnumBeneficiaryTypeWebServiceClient enumBeneficiaryTypeWebService;

    public BenefitTypeFieldType() {
        super(String.class);
    }

    @Override
    public String name() {
        return "BenefitType";
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
        enumBeneficiaryTypeWebService.findAll().list
            .forEach(enumBeneficiaryTypeResponse -> {
                EnumConstant enumConstant = new EnumConstant();
                enumConstant.displayName = enumBeneficiaryTypeResponse.name;
                enumConstant.value = enumBeneficiaryTypeResponse.value;
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
