package app.dealer.api;

import app.dealer.api.credit.CreditTrackingQuery;
import app.dealer.api.credit.CreditTrackingResponse;
import io.sited.db.FindView;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

/**
 * @author Jonathan.Guo
 */
public interface CreditTrackingWebService {
    @PUT
    @Path("/api/dealer/:dealerId/credit/tracking")
    FindView<CreditTrackingResponse> find(@PathParam("dealerId") String dealerId, CreditTrackingQuery query);
}
