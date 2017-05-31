package app.dealer.credit.service;

import app.dealer.api.credit.CreditTrackingQuery;
import app.dealer.credit.domain.CreditTracking;
import com.google.common.collect.Lists;
import io.sited.db.FindView;
import io.sited.db.JDBCRepository;
import io.sited.db.Query;

import javax.inject.Inject;

/**
 * @author Hubery.Chen
 */
public class CreditTrackingService {
    @Inject
    JDBCRepository<CreditTracking> repository;

    public FindView<CreditTracking> find(String creditId, CreditTrackingQuery query) {
        Query<CreditTracking> trackingQuery = repository.query(" credit_id = ? ", Lists.newArrayList(creditId).toArray()).sort("created_time", true);
        if (query.page != null) {
            trackingQuery.page(query.page).limit(query.limit);
        }
        return trackingQuery.find();
    }
}
