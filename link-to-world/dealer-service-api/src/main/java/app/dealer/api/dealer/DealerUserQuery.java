package app.dealer.api.dealer;

import java.util.List;

/**
 * @author miller
 */
public class DealerUserQuery {
    public String dealerId;
    public List<String> dealerIds;
    public List<String> roles;
    public String order;
    public boolean desc = true;
    public Integer page;
    public Integer limit;
}
