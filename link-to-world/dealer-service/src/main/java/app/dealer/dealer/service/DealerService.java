package app.dealer.dealer.service;

import app.dealer.api.dealer.CreateDealerRequest;
import app.dealer.api.dealer.DealerLevelView;
import app.dealer.api.dealer.DealerQuery;
import app.dealer.api.dealer.SearchDealerRequest;
import app.dealer.api.dealer.UpdateDealerRequest;
import app.dealer.dealer.domain.Dealer;
import app.dealer.dealer.domain.DealerLevel;
import app.dealer.dealer.domain.DealerStatus;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import io.sited.db.Query;
import io.sited.http.exception.BadRequestException;
import io.sited.http.exception.NotFoundException;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author miller
 */
public class DealerService {
    @Inject
    MongoRepository<Dealer> repository;

    public Dealer create(CreateDealerRequest request) {
        if (null != request.parentDealerId && !isDealerExist(request.parentDealerId)) {
            throw new NotFoundException("parentDealerId", "dealer.error.parentDealerNoneExists");
        }
        Dealer dealer = new Dealer();
        if (null != request.parentDealerId) {
            List<String> parentDealerIds = Lists.newArrayList();
            handleParent(parentDealerIds, request.parentDealerId);
            dealer.parentDealerIds = parentDealerIds;
        }
        dealer.name = request.name;
        dealer.email = request.email;
        dealer.contactName = request.contactName;
        dealer.contactIdNumber = request.contactIdNumber;
        dealer.cellphone = request.cellphone;
        dealer.state = request.state;
        dealer.city = request.city;
        dealer.ward = request.ward;
        dealer.parentDealerId = request.parentDealerId;
        dealer.level = DealerLevel.valueOf(request.level.name());
        dealer.status = DealerStatus.valueOf(request.status.name());
        dealer.updatedBy = request.requestBy;
        dealer.createdBy = request.requestBy;
        dealer.createdTime = LocalDateTime.now();
        dealer.updatedTime = LocalDateTime.now();
        dealer.businessLicence = request.businessLicence;
        dealer.autoAllocate = false;
        repository.insert(dealer);
        return dealer;
    }

    public Dealer update(String id, UpdateDealerRequest request) {
        Optional<Dealer> original = get(id);
        if (!original.isPresent()) {
            throw new NotFoundException("dealerId", "dealer.error.dealerNoneExists");
        }
        Dealer dealer = original.get();
        dealer.name = request.name;
        dealer.email = request.email;
        dealer.contactName = request.contactName;
        dealer.contactIdNumber = request.contactIdNumber;
        dealer.cellphone = request.cellphone;
        if (!Strings.isNullOrEmpty(request.state)) {
            dealer.state = request.state;
        }
        if (!Strings.isNullOrEmpty(request.city)) {
            dealer.city = request.city;
        }
        if (!Strings.isNullOrEmpty(request.ward)) {
            dealer.ward = request.ward;
        }
        dealer.level = DealerLevel.valueOf(request.level.name());
        dealer.updatedBy = request.requestBy;
        dealer.updatedTime = LocalDateTime.now();
        dealer.businessLicence = request.businessLicence;
        repository.update(dealer.id, dealer);
        return dealer;
    }

    public Optional<Dealer> get(String id) {
        return Optional.ofNullable(repository.get(new ObjectId(id)));
    }

    public void delete(String id) {
        repository.delete(new ObjectId(id));
    }

    public FindView<Dealer> adminSearch(SearchDealerRequest request) {
        Document filter = filter(request);
        if (request.level != null && !request.level.equals(DealerLevelView.LEVEL1)) {
            filter.put("status", new Document("$ne", DealerStatus.AUDITING));
        } else {
            filter.put("$or", Lists.newArrayList(new Document("level", DealerLevel.LEVEL1),
                    new Document("$and", Lists.newArrayList(new Document("status", new Document("$ne", DealerStatus.AUDITING))
                            , new Document("level", new Document("$ne", DealerLevel.LEVEL1))))));
        }
        Query<Dealer> query = repository.query(filter);
        return search(query, request);
    }

    public FindView<Dealer> search(SearchDealerRequest request) {
        Query<Dealer> query = repository.query(filter(request));
        return search(query, request);
    }

    public List<Dealer> children(String id) {
        Document filter = new Document("status", new Document("$ne", DealerStatus.DELETED));
        filter.put("parent_dealer_ids", new Document("$in", Lists.newArrayList(id)));
        return repository.query(filter).findMany();
    }

    public Dealer root(String dealerId) {
        Optional<Dealer> dealerOptional = get(dealerId);
        Dealer dealer = dealerOptional.get();
        if (dealer.level.equals(DealerLevel.LEVEL1)) return dealer;
        return root(dealer.parentDealerId);
    }

    public FindView<Dealer> find(DealerQuery query) {
        Document filter = new Document();
        if (!Strings.isNullOrEmpty(query.name)) {
            filter.put("name", query.name);
        }
        if (query.states != null) {
            filter.put("state", new Document("$in", query.states));
        }
        if (query.level != null) {
            filter.put("level", DealerLevel.valueOf(query.level.name()));
        }
        return repository.query(filter).find();
    }

    public void updateStatus(String id, DealerStatus status) {
        Dealer dealer = get(id).orElseThrow(() -> new BadRequestException("dealerId", "dealer.error.dealerNoneExists"));
        dealer.status = status;
        dealer.updatedTime = LocalDateTime.now();
        repository.update(new ObjectId(id), dealer);
    }

    public void switchAutoAllocate(Dealer dealer) {
        dealer.updatedTime = LocalDateTime.now();
        dealer.autoAllocate = !dealer.autoAllocate;
        repository.update(dealer.id, dealer);
    }

    private FindView<Dealer> search(Query<Dealer> query, SearchDealerRequest request) {
        if (null != request.page) {
            query = query.page(request.page);
        }
        if (null != request.limit) {
            query = query.limit(request.limit);
        }
        return query.sort("created_time", true).find();
    }

    private Document filter(SearchDealerRequest request) {
        Document filter = new Document("status", new Document("$ne", DealerStatus.DELETED));
        if (request.currentParentId != null) {
            filter.put("parent_dealer_id", request.currentParentId);
        }
        if (request.parentId != null) {
            filter.put("parent_dealer_ids", new Document("$in", Lists.newArrayList(request.parentId)));
        }
        if (!Strings.isNullOrEmpty(request.name)) {
            filter.put("name", Pattern.compile(request.name));
        }
        if (!Strings.isNullOrEmpty(request.contactName)) {
            filter.put("contact_name", Pattern.compile(request.contactName));
        }
        if (request.states != null) {
            filter.put("state", new Document("$in", request.states));
        }
        if (!Strings.isNullOrEmpty(request.city)) {
            filter.put("city", Pattern.compile(request.city));
        }
        if (null != request.level) {
            filter.put("level", request.level);
        }
        return filter;
    }

    private void handleParent(List<String> parentDealerIds, String parentId) {
        Dealer parent = get(parentId).get();
        parentDealerIds.add(parent.id.toHexString());
        if (null != parent.parentDealerId) handleParent(parentDealerIds, parent.parentDealerId);
    }

    private boolean isDealerExist(String id) {
        return get(id).isPresent();
    }
}
