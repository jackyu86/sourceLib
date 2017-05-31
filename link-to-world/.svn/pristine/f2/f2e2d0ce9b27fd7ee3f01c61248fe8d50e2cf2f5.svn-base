package com.caej.site.customer.web;

/**
 * @author Hubery.Chen
 */
public enum GenderModel {
    MALE("男", "M"), FEMALE("女", "F"), UNDEFINED("保密", "N");

    public String display;
    public String value;

    GenderModel(String display, String value) {
        this.display = display;
        this.value = value;
    }

    public static GenderModel display(String value) {
        for (GenderModel genderModel : values()) {
            if (genderModel.display.equals(value)) {
                return genderModel;
            }
        }
        return GenderModel.UNDEFINED;
    }

    public static GenderModel value(String value) {
        for (GenderModel genderModel : values()) {
            if (genderModel.value.equals(value)) {
                return genderModel;
            }
        }
        return GenderModel.UNDEFINED;
    }
}
