package app.dealer.api.dealer;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Jonathan.Guo
 */
public enum DealerLevelView {
    LEVEL1("一级"), LEVEL2("二级"), LEVEL3("三级"), LEVEL4("四级");

    public String display;

    DealerLevelView(String display) {
        this.display = display;
    }

    public List<DealerLevelView> child() {
        switch (this) {
            case LEVEL1:
                return Lists.newArrayList(LEVEL2, LEVEL3, LEVEL4);
            case LEVEL2:
                return Lists.newArrayList(LEVEL3, LEVEL4);
            case LEVEL3:
                return Lists.newArrayList(LEVEL4);
            default:
                return Lists.newArrayList();
        }
    }
}
