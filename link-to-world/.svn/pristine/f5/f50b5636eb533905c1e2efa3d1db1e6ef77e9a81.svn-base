package io.sited.http.impl;

import io.sited.http.Session;
import io.sited.util.JSON;

import java.util.HashMap;
import java.util.Optional;

public class SessionImpl implements Session {
    public final String id;
    public final SessionData data;
    boolean invalidate = false;

    public SessionImpl(String id, SessionData data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<String> get(String key) {
        return get(key, String.class);
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        String json = data.get(key);
        if (json != null) {
            return Optional.of(JSON.fromJSON(json, type));
        }
        return Optional.empty();
    }

    @Override
    public <T> Session set(String key, T value) {
        if (value != null) {
            data.put(key, JSON.toJSON(value));
        }
        return this;
    }

    @Override
    public Session remove(String key) {
        data.remove(key);
        return this;
    }

    @Override
    public void invalidate() {
        data.clear();
        invalidate = true;
    }

    public static class SessionData extends HashMap<String, String> {
    }
}