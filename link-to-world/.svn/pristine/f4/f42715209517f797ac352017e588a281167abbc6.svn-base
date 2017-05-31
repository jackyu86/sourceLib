package io.sited.test;

import io.sited.Module;
import io.sited.test.impl.MockClient;
import io.sited.test.impl.MockRequestBuilder;
import org.junit.rules.ExternalResource;

/**
 * @author chi
 */
public final class SiteRule extends ExternalResource {
    private final MockSite site;
    private final MockClient client = MockClient.get();

    public SiteRule(Module... modules) {
        site = new MockSite();
        for (Module module : modules) {
            site.install(module);
        }
    }

    public MockClient client() {
        return client;
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
        site.stop();
    }
}
