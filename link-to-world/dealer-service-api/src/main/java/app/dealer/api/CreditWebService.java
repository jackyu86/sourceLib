package app.dealer.api;

import app.dealer.api.credit.CheckoutRequest;
import app.dealer.api.credit.CreditResponse;
import app.dealer.api.credit.CreditStatusView;
import app.dealer.api.credit.ResetRequest;
import app.dealer.api.credit.UpdateRequest;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

import java.util.Optional;

/**
 * @author Jonathan.Guo
 */
public interface CreditWebService {
    @POST
    @Path("/api/dealer/:dealerId/init")
    CreditResponse init(@PathParam("dealerId") String dealerId, UpdateRequest request);

    @GET
    @Path("/api/dealer/:dealerId/credit")
    Optional<CreditResponse> get(@PathParam("dealerId") String dealerId);

    @PUT
    @Path("/api/dealer/:dealerId/credit")
    void update(@PathParam("dealerId") String dealerId, UpdateRequest request);

    @PUT
    @Path("/api/dealer/:dealerId/credit/status")
    CreditStatusView updateStatus(@PathParam("dealerId") String dealerId);

    @POST
    @Path("/api/dealer/:dealerId/credit/checkout")
    CreditResponse checkout(@PathParam("dealerId") String dealerId, CheckoutRequest request);

    @POST
    @Path("/api/dealer/:dealerId/credit/reset")
    void reset(@PathParam("dealerId") String dealerId, ResetRequest request);
}
