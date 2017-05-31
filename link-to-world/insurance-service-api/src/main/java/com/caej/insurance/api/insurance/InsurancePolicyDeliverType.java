package com.caej.insurance.api.insurance;

/**
 * @author chi
 */
@Deprecated
public enum InsurancePolicyDeliverType {
    BD01("纸质保单"), BD02("电子保单"), BD03("保险凭证");

    private String label;

    InsurancePolicyDeliverType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}
