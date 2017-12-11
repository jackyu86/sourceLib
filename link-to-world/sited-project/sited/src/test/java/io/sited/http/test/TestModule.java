package io.sited.http.test;

import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.http.HttpModule;

@ModuleInfo(name = "test", require = HttpModule.class)
public class TestModule extends Module {
    @Override
    protected void configure() throws Exception {
//        require(HttpConfig.class)
//            .controller(TestController.class, new TestController());
    }
}