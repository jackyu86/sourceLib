package io.sited.admin;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author chi
 */
public class Console {
    private final List<ConsoleModule> modules = Lists.newArrayList();

    public Console install(String name) {
        return install(name, null);
    }

    public Console install(String name, Object options) {
        ConsoleModule consoleModule = new ConsoleModule();
        consoleModule.name = name;
        consoleModule.options = options;
        modules.add(consoleModule);
        return this;
    }

    public List<ConsoleModule> modules() {
        return modules;
    }

    public static class ConsoleModule {
        public String name;
        public Object options;
    }
}
