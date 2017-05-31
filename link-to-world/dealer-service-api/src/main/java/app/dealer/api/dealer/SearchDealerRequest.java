package app.dealer.api.dealer;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class SearchDealerRequest {
    public String name;
    public String contactName;
    public List<String> states;
    public String city;
    public DealerLevelView level;
    public String parentId;
    public String currentParentId;
    public Integer page;
    public Integer limit;
}
