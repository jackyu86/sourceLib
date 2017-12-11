package com.caej.product.service.field;

/**
 * @author miller
 */
public class EffectiveInsurance {
    public FillField fill1;
    public FillField fill2;
    public FillField fill3;
    public FillField fill4;
    public FillField fill5;

    public static class FillField {
        public String company;
        public String amount;

        @Override
        public String toString() {
            return company + ":" + amount;
        }
    }
}
