package com.caej.underwriting.builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import com.caej.api.underwritting.UnderwritingRequest;
import com.caej.insurance.api.insurance.InsurancePeriodView;
import com.caej.insurance.domain.InsurancePeriod;
import com.caej.product.api.product.ProductPeriodStartTimeType;
import com.caej.product.api.product.ProductResponse;

import io.sited.form.Form;
import io.sited.util.JSON;

/**
 * @author miller
 */
public class UnderwritingInsIntervalBuilder {
    public static UnderwritingRequest.UnderwritingRequestInsInterval build(Form form, ProductResponse product) {
        if (ProductPeriodStartTimeType.USER_INPUT.equals(product.period.startTimeType) || ProductPeriodStartTimeType.DEFAULT.equals(product.period.startTimeType)) {
            return userInput(form);
        }
        if (ProductPeriodStartTimeType.LATEST.equals(product.period.startTimeType)) {
            return latest(form, product);
        }
        return new UnderwritingRequest.UnderwritingRequestInsInterval();
    }

    private static UnderwritingRequest.UnderwritingRequestInsInterval userInput(Form form) {
        UnderwritingRequest.UnderwritingRequestInsInterval insInterval = new UnderwritingRequest.UnderwritingRequestInsInterval();
        Map<String, Object> map = form.value;
        if (map.get("plan") == null) return new UnderwritingRequest.UnderwritingRequestInsInterval();
        Map<String, Object> group = JSON.fromJSON(JSON.toJSON(map.get("plan")), Map.class);
        LocalDate startTime = LocalDate.parse(group.get("startTime").toString());
        insInterval.effectDate = startTime.format(DateTimeFormatter.ofPattern("yyyyMMdd 00:00:00"));
        InsurancePeriod insurancePeriod = JSON.fromJSON(JSON.toJSON(group.get("period")), InsurancePeriod.class);
        switch (insurancePeriod.unit) {
            case DAY:
                insInterval.endDate = startTime.plus(insurancePeriod.value - 1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd 23:59:59"));
                break;
            case MONTH:
                insInterval.endDate = startTime.plus(insurancePeriod.value, ChronoUnit.MONTHS).plus(insurancePeriod.value - 1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd 23:59:59"));
                break;
            case YEAR:
                insInterval.endDate = startTime.plus(insurancePeriod.value, ChronoUnit.YEARS).plus(insurancePeriod.value - 1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd 23:59:59"));
                break;
            //todo age
            case AGE:
            case LIFE:
            case NONE:
            default:
                break;
        }
        return insInterval;
    }

    private static UnderwritingRequest.UnderwritingRequestInsInterval latest(Form form, ProductResponse product) {
        LocalDate localDate = LocalDate.now();
        InsurancePeriodView earliestInsuranceTime = product.period.earliestInsuranceTime;
        if (earliestInsuranceTime == null) {
            //todo throw exception?
            return userInput(form);
        }
        LocalDate startTime = LocalDate.now();
        switch (earliestInsuranceTime.unit) {
            case DAY:
                startTime = localDate.plus(earliestInsuranceTime.value - 1, ChronoUnit.DAYS);
                break;
            case MONTH:
                startTime = localDate.plus(earliestInsuranceTime.value, ChronoUnit.MONTHS);
                break;
            case YEAR:
                startTime = localDate.plus(earliestInsuranceTime.value, ChronoUnit.YEARS);
                break;
            //todo age
            case AGE:
            case LIFE:
            case NONE:
            default:
                break;
        }
        UnderwritingRequest.UnderwritingRequestInsInterval insInterval = new UnderwritingRequest.UnderwritingRequestInsInterval();
        insInterval.effectDate = startTime.format(DateTimeFormatter.ofPattern("yyyyMMdd 00:00:00"));
        Map<String, Object> map = form.value;
        if (map.get("plan") == null) return insInterval;
        Map<String, Object> group = JSON.fromJSON(JSON.toJSON(map.get("plan")), Map.class);
        InsurancePeriod insurancePeriod = JSON.fromJSON(JSON.toJSON(group.get("period")), InsurancePeriod.class);
        switch (insurancePeriod.unit) {
            case DAY:
                insInterval.endDate = startTime.plus(insurancePeriod.value - 1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd 23:59:59"));
                break;
            case MONTH:
                insInterval.endDate = startTime.plus(insurancePeriod.value, ChronoUnit.MONTHS).plus(insurancePeriod.value - 1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd 23:59:59"));
                break;
            case YEAR:
                insInterval.endDate = startTime.plus(insurancePeriod.value, ChronoUnit.YEARS).plus(insurancePeriod.value - 1, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyyMMdd 23:59:59"));
                break;
            //todo age
            case AGE:
            case LIFE:
            case NONE:
            default:
                break;
        }
        return insInterval;
    }
}
