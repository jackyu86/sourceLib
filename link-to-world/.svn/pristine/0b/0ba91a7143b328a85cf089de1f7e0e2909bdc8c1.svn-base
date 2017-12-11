package app.dealer.credit.web;


import app.dealer.api.CreditWebService;
import app.dealer.api.credit.CheckoutRequest;
import app.dealer.api.credit.CreditResponse;
import app.dealer.api.credit.CreditStatusView;
import app.dealer.api.credit.ResetRequest;
import app.dealer.api.credit.UpdateRequest;
import app.dealer.credit.domain.Credit;
import app.dealer.credit.service.CreditService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * @author miller
 */
public class CreditWebServiceImpl implements CreditWebService {
    @Inject
    CreditService creditService;

    @Override
    public CreditResponse init(String dealerId, UpdateRequest request) {
        return response(creditService.create(dealerId, request));
    }

    @Override
    public Optional<CreditResponse> get(String dealerId) {
        Optional<Credit> credit = creditService.get(dealerId);
        return credit.isPresent() ? Optional.of(response(credit.get())) : Optional.empty();
    }

    @Override
    public void update(String dealerId, UpdateRequest request) {
        creditService.update(dealerId, request);
    }

    @Override
    public CreditStatusView updateStatus(String dealerId) {
        return CreditStatusView.valueOf(creditService.update(dealerId).name());
    }

    @Override
    public CreditResponse checkout(String dealerId, CheckoutRequest request) {
        return response(creditService.checkout(dealerId, request));
    }

    @Override
    public void reset(String dealerId, ResetRequest request) {
        creditService.reset(dealerId, request);
    }

    private CreditResponse response(Credit credit) {
        CreditResponse response = new CreditResponse();
        response.id = credit.id;
        response.dealerId = credit.dealerId;
        response.quota = format(credit.quota);
        response.totalCredits = format(credit.totalCredits);
        response.status = CreditStatusView.valueOf(credit.status.name());
        response.createdBy = credit.createdBy;
        return response;
    }

    private Double format(Double value) {
        return value == null ? null : BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
