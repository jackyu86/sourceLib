package app.dealer.api.product;

import org.bson.types.ObjectId;

/**
 * @author Jonathan.Guo
 */
public class SearchProductPermissionRequest {
    public ObjectId dealerId;
    public String productId;
}
