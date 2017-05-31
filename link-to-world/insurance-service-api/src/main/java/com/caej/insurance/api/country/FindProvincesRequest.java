package com.caej.insurance.api.country;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Hubery.Chen
 */
public class FindProvincesRequest {
    public List<String> shortNames = Lists.newArrayList();
    public List<String> names = Lists.newArrayList();
}
