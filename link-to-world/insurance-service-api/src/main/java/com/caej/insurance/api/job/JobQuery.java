package com.caej.insurance.api.job;

import java.util.List;

/**
 * @author miller
 */
public class JobQuery {
    public Integer page = 1;
    public Integer limit = 10;
    public String displayName;
    public String order;
    public Boolean desc;
    public List<Integer> levels;
}
