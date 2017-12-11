package app.dealer.credit.web;

import app.dealer.api.CreditTrackingWebService;
import app.dealer.api.credit.CreditTrackingQuery;
import app.dealer.api.credit.CreditTrackingResponse;
import app.dealer.credit.domain.Credit;
import app.dealer.credit.domain.CreditTracking;
import app.dealer.credit.service.CreditService;
import app.dealer.credit.service.CreditTrackingService;
import io.sited.db.FindView;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Created by hubery.chen on 2017/1/14.
 */
public class CreditTrackingWebServiceImpl implements CreditTrackingWebService {
    @Inject
    CreditService creditService;
    @Inject
    CreditTrackingService creditTrackingService;

    @Override
    public FindView<CreditTrackingResponse> find(String dealerId, CreditTrackingQuery query) {
        Optional<Credit> credit = creditService.get(dealerId);
        if (!credit.isPresent()) {
            return new FindView<>();
        }
        return FindView.map(creditTrackingService.find(credit.get().id, query), this::response);
    }

    private CreditTrackingResponse response(CreditTracking instance) {
        CreditTrackingResponse response = new CreditTrackingResponse();
        response.id = instance.id;
        response.creditId = instance.creditId;
        response.paymentId = instance.paymentId;
        response.quota = format(instance.quota);
        response.type = CreditTrackingResponse.UpdateCreditType.valueOf(instance.type.name());
        response.totalCredits = format(instance.totalCredits);
        response.operator = instance.operator;
        response.createdTime = instance.createdTime;
        response.createdBy = instance.createdBy;
        return response;
    }

    private Double format(Double value) {
        return value == null ? null : BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
