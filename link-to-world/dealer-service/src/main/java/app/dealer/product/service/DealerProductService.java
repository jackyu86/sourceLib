package app.dealer.product.service;

import app.dealer.api.product.SearchDealerProductRequest;
import app.dealer.product.domain.DealerProduct;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.sited.db.FindView;
import io.sited.db.MongoRepository;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author miller
 */
public class DealerProductService {
    @Inject
    MongoRepository<DealerProduct> repository;

    public void deleteByDealerId(String dealerId) {
        repository.collection().deleteMany(new Document("dealer_id", dealerId));
    }

    public List<DealerProduct> get(String dealerId) {
        List<DealerProduct> list = new ArrayList<>();
        return repository.collection().find(new Document("dealer_id", dealerId)).into(list);
    }

    public DealerProduct create(DealerProduct dealerProduct) {
        repository.insert(dealerProduct);
        return dealerProduct;
    }

    public FindView<DealerProduct> search(List<String> dealerIds) {
        return repository.query(new Document("dealer_id", new Document("$in", dealerIds))).find();
    }

    public FindView<DealerProduct> search(String dealerId, SearchDealerProductRequest request) {
        Document filter = new Document("dealer_id", dealerId);
        if (null != request.categoryIds && !request.categoryIds.isEmpty()) {
            filter.put("insurance_category_ids", new Document("$in", request.categoryIds));
        }
        return repository.query(filter)
                .page(request.page).limit(request.limit)
                .find();
    }

    public Set<ObjectId> category(String dealerId) {
        Document filter = new Document("dealer_id", dealerId);
        Set<ObjectId> set = Sets.newHashSet();
        List<DealerProduct> list = Lists.newArrayList();
        repository.collection().find(filter).into(list);
        list.forEach(dealerProduct -> {
            set.addAll(dealerProduct.insuranceCategoryIds);
        });
        return set;
    }

    public Optional<DealerProduct> get(String dealerId, String productName) {
        return repository.query(new Document("dealer_id", dealerId).append("product_name", productName)).findOne();
    }
}
