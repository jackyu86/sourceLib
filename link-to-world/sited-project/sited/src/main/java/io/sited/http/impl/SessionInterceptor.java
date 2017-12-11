package io.sited.http.impl;

import io.sited.cache.Cache;
import io.sited.http.Interceptor;
import io.sited.http.Invocation;

import javax.inject.Inject;

/**
 * @author chi
 */
public class SessionInterceptor implements Interceptor {
    @Inject
    Cache<SessionImpl.SessionData> cache;

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Object response = invocation.proceed();
        SessionImpl session = (SessionImpl) invocation.request().session();
        if (session.invalidate) {
            cache.invalidate(session.id);
        } else {
            cache.put(session.id, session.data);
        }
        return response;
    }
}
