package app.dealer.api;

import app.dealer.api.product.DealerProductCategoryResponse;
import app.dealer.api.product.DealerProductResponse;
import app.dealer.api.product.DealerProductView;
import app.dealer.api.product.SearchDealerProductRequest;
import app.dealer.api.product.UpdateDealerProductRequest;
import io.sited.db.FindView;
import io.sited.http.GET;
import io.sited.http.POST;
import io.sited.http.PUT;
import io.sited.http.Path;
import io.sited.http.PathParam;

import java.util.Optional;

/**
 * @author Jonathan.Guo
 */
public interface DealerProductWebService {
    @PUT
    @Path("/api/dealer/:dealerId/product")
    DealerProductResponse update(@PathParam("dealerId") String dealerId, UpdateDealerProductRequest request);

    @GET
    @Path("/api/dealer/:dealerId/product")
    Optional<DealerProductResponse> get(@PathParam("dealerId") String dealerId);

    @POST
    @Path("/api/dealer/:dealerId/search")
    FindView<String> search(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request);

    @POST
    @Path("/v2/api/dealer/:dealerId/search")
    FindView<String> searchV2(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request);

    @POST
    @Path("/api/dealer/:dealerId/list")
    FindView<DealerProductView> list(@PathParam("dealerId") String dealerId, SearchDealerProductRequest request);

    @GET
    @Path("/api/dealer/:dealerId/category")
    DealerProductCategoryResponse category(@PathParam("dealerId") String dealerId);

    @GET
    @Path("/api/dealer/:dealerId/product/:productName")
    Optional<DealerProductView> getByDealerIdAndProductName(@PathParam("dealerId") String dealerId, @PathParam("productName") String productName);
}
