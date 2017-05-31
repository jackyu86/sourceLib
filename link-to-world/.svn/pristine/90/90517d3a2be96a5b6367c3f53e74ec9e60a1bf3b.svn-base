package app.dealer.dealer.service;

import app.dealer.api.dealer.CreateDealerUserRequest;
import app.dealer.api.dealer.DealerUserQuery;
import app.dealer.api.dealer.UpdateDealerUserRequest;
import app.dealer.api.dealer.UpdatePayPasswordRequest;
import app.dealer.dealer.domain.DealerUser;
import app.dealer.dealer.domain.DealerUserStatus;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.db.Query;
import io.sited.http.exception.BadRequestException;
import org.bson.Document;

import javax.inject.Inject;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author miller
 */
public class DealerUserService {
    @Inject
    MongoRepository<DealerUser> repository;

    public void updatePayPassword(String userId, UpdatePayPasswordRequest request) {
        DealerUser dealerUser = getByUserId(userId);
        dealerUser.hashedPayPassword = new PasswordHasher(dealerUser.salt, request.payPassword).hash(dealerUser.iteration);
        dealerUser.updatedBy = request.requestBy;
        dealerUser.updatedTime = LocalDateTime.now();
        repository.update(dealerUser.id, dealerUser);
    }

    public DealerUser create(CreateDealerUserRequest request) {
        if (getOptionalByUserId(request.userId).isPresent()) {
            throw new BadRequestException("userId", "dealer.error.dealerExists");
        }
        DealerUser dealerUser = new DealerUser();
        dealerUser.dealerId = request.dealerId;
        dealerUser.userId = request.userId;
        dealerUser.salt = salt();
        dealerUser.iteration = (int) (System.currentTimeMillis() % 4 + 1);
        if (request.payPassword != null) {
            dealerUser.hashedPayPassword = new PasswordHasher(dealerUser.salt, request.payPassword).hash(dealerUser.iteration);
        }
        dealerUser.roles = request.roles;
        dealerUser.status = DealerUserStatus.valueOf(request.status.name());
        dealerUser.createdBy = request.requestBy;
        dealerUser.updatedBy = request.requestBy;
        dealerUser.createdTime = LocalDateTime.now();
        dealerUser.updatedTime = LocalDateTime.now();
        dealerUser.channelId = request.channelId;
        dealerUser.source = request.source;
        repository.insert(dealerUser);
        return dealerUser;
    }

    public Optional<DealerUser> getOptionalByDealerId(String dealerId) {
        Document filter = new Document();
        filter.put("dealer_id", dealerId);
        filter.put("roles", Lists.newArrayList("dealerAdmin"));
        return repository.query(filter).findOne();
    }

    public DealerUser getByDealerId(String dealerId) {
        return getOptionalByDealerId(dealerId).orElseThrow(() -> new BadRequestException("dealerId", null, "Dealer not exits, dealerId={}", dealerId));
    }

    public Optional<DealerUser> getOptionalByUserId(String userId) {
        return repository.query("user_id", userId).findOne();
    }

    public DealerUser getByUserId(String userId) {
        return repository.query("user_id", userId).findOne().orElseThrow(() -> new BadRequestException("userId", null, "Dealer not exits, userId={}", userId));
    }

    public void delete(String userId) {
        repository.delete(getByUserId(userId).id);
    }

    public void updateStatus(String userId, DealerUserStatus status) {
        DealerUser dealerUser = getByUserId(userId);
        dealerUser.status = status;
        dealerUser.updatedTime = LocalDateTime.now();
        repository.update(dealerUser.id, dealerUser);
    }

    public void updateStatusByDealer(String dealerId, DealerUserStatus status) {
        repository.collection().updateMany(new Document("dealer_id", dealerId), new Document("status", status));
    }

    public DealerUser update(String userId, UpdateDealerUserRequest request) {
        DealerUser dealerUser = getByUserId(userId);
        dealerUser.roles = request.roles;
        dealerUser.updatedBy = request.requestBy;
        dealerUser.updatedTime = LocalDateTime.now();
        repository.update(dealerUser.id, dealerUser);
        return dealerUser;
    }

    public FindView<DealerUser> find(DealerUserQuery query) {
        Document filter = new Document();
        if (null != query.dealerId) {
            filter.put("dealer_id", query.dealerId);
        }
        if (query.dealerIds != null) {
            filter.put("dealer_id", new Document("$in", query.dealerIds));
        }
        if (null != query.roles) {
            filter.put("roles", query.roles);
        }
        Query<DealerUser> dealerUserQuery = repository.query(filter);
        if (query.page != null) {
            dealerUserQuery.page(query.page).limit(query.limit);
        }
        if (query.order != null) {
            dealerUserQuery.sort(query.order, query.desc);
        }
        return dealerUserQuery.find();
    }

    private String salt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return Hashing.md5().hashBytes(bytes).toString();
    }

}
