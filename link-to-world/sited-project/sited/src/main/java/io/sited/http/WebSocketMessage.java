package io.sited.http;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author chi
 */
public class WebSocketMessage {
    public String fromClientId;
    public List<String> toClientIds = Lists.newArrayList();
    public Map<String, Object> context = Maps.newHashMap();
    public Object content;
}
