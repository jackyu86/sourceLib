package io.sited;

import io.sited.db.DBModule;
import io.sited.db.MongoConfig;
import io.sited.test.MockSite;
import io.sited.test.impl.MockRequestBuilder;
import org.junit.rules.ExternalResource;

/**
 * @author chi
 */
public final class SiteRule extends ExternalResource {
    private final MockSite site;

    public SiteRule(Module... modules) {
        site = new MockSite();
        for (Module module : modules) {
            site.install(module);
        }
    }

    public MockRequestBuilder get(String path) {
        return site.get(path);
    }

    public MockRequestBuilder post(String path) {
        return site.post(path);
    }

    public MockRequestBuilder delete(String path) {
        return site.delete(path);
    }

    public MockRequestBuilder put(String path) {
        return site.put(path);
    }

    public <T extends Module> T module(Class<T> moduleClass) {
        return site.module(moduleClass);
    }

    @Override
    protected void before() throws Throwable {
        site.start();
    }

    @Override
    protected void after() {
        site.module(DBModule.class).require(MongoConfig.class).db().drop();
        site.stop();
    }
}
