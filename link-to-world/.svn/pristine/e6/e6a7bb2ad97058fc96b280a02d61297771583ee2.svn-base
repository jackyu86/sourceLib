package app.dealer.credit.service;

import app.dealer.api.credit.CheckoutRequest;
import app.dealer.api.credit.ResetRequest;
import app.dealer.api.credit.UpdateRequest;
import app.dealer.credit.domain.Credit;
import app.dealer.credit.domain.CreditStatus;
import app.dealer.credit.domain.CreditTracking;
import app.dealer.credit.domain.UpdateCreditType;
import com.google.common.base.Strings;
import io.sited.StandardException;
import io.sited.db.JDBCConfig;
import io.sited.db.JDBCRepository;
import io.sited.db.Transaction;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @author miller
 */
public class CreditService {
    @Inject
    JDBCConfig jdbcConfig;
    @Inject
    JDBCRepository<Credit> repository;
    @Inject
    JDBCRepository<CreditTracking> trackingRepository;

    public Credit create(String dealerId, UpdateRequest request) {
        Credit credit = newCredit(dealerId, request.quota, request.requestBy);
        Transaction transaction = jdbcConfig.transaction();
        try {
            if (request.parentDealerId != null) {
                Optional<Credit> parentCreditOptional = get(request.parentDealerId);
                validateParent(parentCreditOptional);
                Credit parentCredit = parentCreditOptional.get();
                if (parentCredit.totalCredits < request.quota) {
                    throw new BadRequestException("quota", "dealer.error.parentTotalCreditsNoneEnough");
                }
                parentCredit.totalCredits = parentCredit.totalCredits - request.quota;
                parentCredit.quota = parentCredit.quota - request.quota;
                repository.update(parentCredit.id, parentCredit);
                trackingRepository.insert(tracking(parentCredit, request, UpdateCreditType.INIT));
            }
            repository.insert(credit);
            trackingRepository.insert(tracking(credit, request, UpdateCreditType.INIT));
            transaction.commit();
        } catch (SQLException e) {
            transaction.rollback();
            throw new StandardException(e);
        }
        return credit;
    }

    public Optional<Credit> get(String dealerId) {
        return repository.query("dealer_id=?", dealerId).findOne();
    }

    public Credit checkout(String dealerId, CheckoutRequest request) {
        Credit credit = getByDealerId(dealerId);
        validate(credit);
        double newCredits = credit.totalCredits - request.amount;
        if (newCredits < 0) {
            throw new BadRequestException("dealerId", "dealer.error.totalCreditsNoneEnough");
        }
        credit.totalCredits = newCredits;
        credit.updatedBy = request.requestBy;
        credit.updatedTime = LocalDateTime.now();
        Transaction transaction = jdbcConfig.transaction();
        try {
            repository.update(credit.id, credit);
            CreditTracking tracking = tracking(credit, request, UpdateCreditType.CHECKOUT);
            tracking.totalCredits = credit.totalCredits;
            trackingRepository.insert(tracking);
            transaction.commit();
        } catch (SQLException e) {
            transaction.rollback();
            throw new StandardException(e);
        }
        return credit;
    }

    public CreditStatus update(String dealerId) {
        Credit credit = get(dealerId).get();
        if (credit.status.equals(CreditStatus.ACTIVE)) {
            credit.status = CreditStatus.INACTIVE;
        } else {
            credit.status = CreditStatus.ACTIVE;
        }
        repository.update(credit.id, credit);
        return credit.status;
    }

    public Credit update(String dealerId, UpdateRequest request) {
        if (request.parentDealerId == null) {
            return rootUpdate(dealerId, request);
        }
        Credit parentCredit = parentCredit(request.parentDealerId);
        Optional<Credit> creditOptional = get(dealerId);
        Credit credit;
        Transaction transaction = jdbcConfig.transaction();
        try {
            if (creditOptional.isPresent()) {
                credit = creditOptional.get();
                double changeQuota = request.quota - credit.quota;
                if (parentCredit.totalCredits < changeQuota) {
                    throw new BadRequestException("quota", "dealer.error.parentTotalCreditsNoneEnough");
                }
                parentCredit.totalCredits = parentCredit.totalCredits - changeQuota;
                parentCredit.quota = parentCredit.quota - changeQuota;
                credit = afterUpdateCredit(credit, request.quota, request.requestBy);
                repository.update(credit.id, credit);
                trackingRepository.insert(tracking(credit, request, UpdateCreditType.UPDATE));
            } else {
                if (parentCredit.totalCredits < request.quota) {
                    throw new BadRequestException("quota", "dealer.error.parentTotalCreditsNoneEnough");
                }
                credit = newCredit(dealerId, request.quota, request.requestBy);
                repository.insert(credit);
                parentCredit.totalCredits = parentCredit.totalCredits - request.quota;
                parentCredit.quota = parentCredit.quota - request.quota;
                trackingRepository.insert(tracking(credit, request, UpdateCreditType.INIT));
            }
            repository.update(parentCredit.id, parentCredit);
            trackingRepository.insert(tracking(parentCredit, request, UpdateCreditType.UPDATE));
            transaction.commit();
        } catch (SQLException e) {
            transaction.rollback();
            throw new StandardException(e);
        }
        return credit;
    }

    public void reset(String dealerId, ResetRequest request) {
        if (request.parentDealerId == null) {
            rootReset(dealerId, request);
            return;
        }

        Credit parentCredit = parentCredit(request.parentDealerId);
        Optional<Credit> creditOptional = get(dealerId);
        if (!creditOptional.isPresent()) {
            return;
        }

        Transaction transaction = jdbcConfig.transaction();
        try {
            Credit credit = creditOptional.get();
            double changeQuota = credit.quota - credit.totalCredits;
            if (parentCredit.totalCredits < changeQuota) {
                throw new BadRequestException("quota", "dealer.error.parentTotalCreditsNoneEnough");
            }
            parentCredit.totalCredits = parentCredit.totalCredits - changeQuota;

            credit.totalCredits = credit.totalCredits + changeQuota;
            credit.updatedBy = request.requestBy;
            credit.updatedTime = LocalDateTime.now();
            repository.update(credit.id, credit);
            trackingRepository.insert(tracking(credit, request, UpdateCreditType.RESET));
            repository.update(parentCredit.id, parentCredit);
            trackingRepository.insert(tracking(parentCredit, request, UpdateCreditType.UPDATE));
            transaction.commit();
        } catch (SQLException e) {
            transaction.rollback();
            throw new StandardException(e);
        }
    }

    private void validate(Credit credit) {
        if (!credit.status.equals(CreditStatus.ACTIVE)) {
            throw new BadRequestException("status", null, "Credit not active, status={}", credit.status);
        }
    }

    private Credit parentCredit(String parentDealerId) {
        Optional<Credit> parentCreditOptional = get(parentDealerId);
        validateParent(parentCreditOptional);
        return parentCreditOptional.get();
    }

    private void rootReset(String dealerId, ResetRequest request) {
        Optional<Credit> creditOptional = get(dealerId);
        if (creditOptional.isPresent()) {
            Credit credit = creditOptional.get();
            credit.totalCredits = credit.quota;
            repository.update(credit.id, credit);
            trackingRepository.insert(tracking(credit, request, UpdateCreditType.RESET));
        }
    }

    private Credit rootUpdate(String dealerId, UpdateRequest request) {
        Optional<Credit> creditOptional = get(dealerId);
        Credit credit;
        if (creditOptional.isPresent()) {
            credit = afterUpdateCredit(creditOptional.get(), request.quota, request.requestBy);
            repository.update(credit.id, credit);
            trackingRepository.insert(tracking(credit, request, UpdateCreditType.UPDATE));
        } else {
            credit = newCredit(dealerId, request.quota, request.requestBy);
            repository.insert(credit);
            trackingRepository.insert(tracking(credit, request, UpdateCreditType.INIT));
        }
        return credit;
    }

    private CreditTracking tracking(Credit credit, CheckoutRequest request, UpdateCreditType type) {
        CreditTracking tracking = tracking(credit, type, request.requestBy);
        tracking.paymentId = request.paymentId;
        if (!Strings.isNullOrEmpty(request.operator)) tracking.operator = request.operator;
        return tracking;
    }

    private CreditTracking tracking(Credit credit, ResetRequest request, UpdateCreditType type) {
        CreditTracking tracking = tracking(credit, type, request.requestBy);
        if (!Strings.isNullOrEmpty(request.operator)) tracking.operator = request.operator;
        return tracking;
    }

    private CreditTracking tracking(Credit credit, UpdateRequest request, UpdateCreditType type) {
        CreditTracking tracking = tracking(credit, type, request.requestBy);
        if (!Strings.isNullOrEmpty(request.operator)) tracking.operator = request.operator;
        return tracking;
    }

    private CreditTracking tracking(Credit credit, UpdateCreditType type, String requestBy) {
        CreditTracking creditTracking = new CreditTracking();
        creditTracking.id = UUID.randomUUID().toString();
        creditTracking.creditId = credit.id;
        creditTracking.quota = credit.quota;
        creditTracking.type = type;
        creditTracking.createdBy = requestBy;
        creditTracking.createdTime = LocalDateTime.now();
        return creditTracking;
    }

    private Credit afterUpdateCredit(Credit credit, Double quota, String requestBy) {
        double changeQuota = quota - credit.quota;
        credit.quota = quota;
        credit.totalCredits = credit.totalCredits + changeQuota;
        if (credit.totalCredits < 0) {
            throw new BadRequestException("quota", "dealer.error.totalCreditsNoneEnough");
        }
        credit.updatedBy = requestBy;
        credit.updatedTime = LocalDateTime.now();
        return credit;
    }

    private Credit getByDealerId(String dealerId) {
        return repository.query("dealer_id=?", dealerId).findOne().orElseThrow(() -> new NotFoundException("dealerId", "dealer.error.creditNoneExists"));
    }

    private Credit newCredit(String dealerId, Double quota, String requestBy) {
        Credit credit = new Credit();
        credit.id = UUID.randomUUID().toString();
        credit.dealerId = dealerId;
        credit.totalCredits = quota;
        credit.quota = quota;
        credit.status = CreditStatus.ACTIVE;
        credit.createdBy = requestBy;
        credit.updatedBy = requestBy;
        credit.createdTime = LocalDateTime.now();
        credit.updatedTime = LocalDateTime.now();
        return credit;
    }

    private void validateParent(Optional<Credit> parentCreditOptional) {
        if (!parentCreditOptional.isPresent()) {
            throw new BadRequestException("quota", "dealer.error.parentQuotaNoneEnough");
        }
    }
}
