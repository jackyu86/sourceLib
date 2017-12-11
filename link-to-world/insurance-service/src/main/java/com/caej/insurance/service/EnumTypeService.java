package com.caej.insurance.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.caej.insurance.api.enumtype.EnumTypeResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author chi
 */
public class EnumTypeService {
    private static final Map<String, EnumTypeResponse> ENUM_TYPE_RESPONSES = Maps.newHashMap();

    static {
        EnumTypeResponse accountType = new EnumTypeResponse();
        accountType.displayName = "帐户类型";
        accountType.name = "enum_account_type";
        accountType.type = "AccountType";
        ENUM_TYPE_RESPONSES.put(accountType.name, accountType);

        EnumTypeResponse bankType = new EnumTypeResponse();
        bankType.displayName = "银行类型";
        bankType.name = "enum_bank_type";
        bankType.type = "enum_bank_type";
        ENUM_TYPE_RESPONSES.put(bankType.name, bankType);

        EnumTypeResponse beneficiaryType = new EnumTypeResponse();
        beneficiaryType.displayName = "受益人类型";
        beneficiaryType.name = "enum_beneficiary_type";
        beneficiaryType.type = "BenefitType";
        ENUM_TYPE_RESPONSES.put(beneficiaryType.name, beneficiaryType);

        EnumTypeResponse cirtiType = new EnumTypeResponse();
        cirtiType.displayName = "证件类型";
        cirtiType.name = "enum_certi_type";
        cirtiType.type = "Id";
        ENUM_TYPE_RESPONSES.put(cirtiType.name, cirtiType);

        EnumTypeResponse chargeType = new EnumTypeResponse();
        chargeType.displayName = "缴费频率";
        chargeType.name = "enum_charge_mode_type";
        chargeType.type = "enum_charge_mode_type";
        ENUM_TYPE_RESPONSES.put(chargeType.name, chargeType);

        EnumTypeResponse chargePeriodType = new EnumTypeResponse();
        chargePeriodType.displayName = "缴费期间";
        chargePeriodType.name = "enum_charge_period_type";
        chargePeriodType.type = "enum_charge_period_type";
        ENUM_TYPE_RESPONSES.put(chargePeriodType.name, chargePeriodType);

        EnumTypeResponse countWayType = new EnumTypeResponse();
        countWayType.displayName = "保费计算方式";
        countWayType.name = "enum_count_way_type";
        countWayType.type = "enum_count_way_type";
        ENUM_TYPE_RESPONSES.put(countWayType.name, countWayType);

        EnumTypeResponse coveragePeriod = new EnumTypeResponse();
        coveragePeriod.displayName = "保障期间类型";
        coveragePeriod.name = "enum_coverage_period_type";
        coveragePeriod.type = "enum_coverage_period_type";
        ENUM_TYPE_RESPONSES.put(coveragePeriod.name, coveragePeriod);

        EnumTypeResponse deliverType = new EnumTypeResponse();
        deliverType.displayName = "保单邮递方式";
        deliverType.name = "enum_deliver_type";
        deliverType.type = "DeliverType";
        ENUM_TYPE_RESPONSES.put(deliverType.name, deliverType);

        EnumTypeResponse genderType = new EnumTypeResponse();
        genderType.displayName = "性别";
        genderType.name = "enum_gender_type";
        genderType.type = "Gender";
        ENUM_TYPE_RESPONSES.put(genderType.name, genderType);

        EnumTypeResponse invoiceDeliverType = new EnumTypeResponse();
        invoiceDeliverType.displayName = "发票邮递方式";
        invoiceDeliverType.name = "enum_invoice_deliver_type";
        invoiceDeliverType.type = "InvoiceDeliverType";
        ENUM_TYPE_RESPONSES.put(invoiceDeliverType.name, invoiceDeliverType);

        EnumTypeResponse legalBeneficiaryType = new EnumTypeResponse();
        legalBeneficiaryType.displayName = "法定受益人类型";
        legalBeneficiaryType.name = "enum_legal_beneficiary_type";
        legalBeneficiaryType.type = "LegalBeneficiary";
        ENUM_TYPE_RESPONSES.put(legalBeneficiaryType.name, legalBeneficiaryType);

        EnumTypeResponse marriageType = new EnumTypeResponse();
        marriageType.displayName = "婚姻状态";
        marriageType.name = "enum_marriage_type";
        marriageType.type = "MarriageType";
        ENUM_TYPE_RESPONSES.put(marriageType.name, marriageType);

        EnumTypeResponse overdueOptionType = new EnumTypeResponse();
        overdueOptionType.displayName = "保费逾期未付";
        overdueOptionType.name = "enum_overdue_option_type";
        overdueOptionType.type = "enum_overdue_option_type";
        ENUM_TYPE_RESPONSES.put(overdueOptionType.name, overdueOptionType);

        EnumTypeResponse overManageType = new EnumTypeResponse();
        overManageType.displayName = "溢缴保费处理方式";
        overManageType.name = "enum_over_manage_type";
        overManageType.type = "enum_over_manage_type";
        ENUM_TYPE_RESPONSES.put(overManageType.name, overManageType);

        EnumTypeResponse payModeType = new EnumTypeResponse();
        payModeType.displayName = "首期支付方式";
        payModeType.name = "enum_pay_mode_type";
        payModeType.type = "enum_pay_mode_type";
        ENUM_TYPE_RESPONSES.put(payModeType.name, payModeType);

        EnumTypeResponse policyRelationType = new EnumTypeResponse();
        policyRelationType.displayName = "与人关系";
        policyRelationType.name = "enum_policy_relation_type";
        policyRelationType.type = "Relation";
        ENUM_TYPE_RESPONSES.put(policyRelationType.name, policyRelationType);

        EnumTypeResponse policyType = new EnumTypeResponse();
        policyType.displayName = "单证类型";
        policyType.name = "enum_policy_type";
        policyType.type = "PolicyType";
        ENUM_TYPE_RESPONSES.put(policyType.name, policyType);
    }

    public List<EnumTypeResponse> findAll() {
        return Lists.newArrayList(ENUM_TYPE_RESPONSES.values());
    }

    public Optional<EnumTypeResponse> get(String name) {
        return Optional.ofNullable(ENUM_TYPE_RESPONSES.get(name));
    }

    public Optional<EnumTypeResponse> getByType(String type) {
        for (Map.Entry<String, EnumTypeResponse> entry : ENUM_TYPE_RESPONSES.entrySet()) {
            if (type.equals(entry.getValue().type)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }
}
