package io.sited.admin.impl.service;

import io.sited.admin.Console;
import io.sited.util.JSON;

import java.util.stream.Collectors;

/**
 * @author chi
 */
public class ConsoleScriptBuilder {
    private final Console console;

    public ConsoleScriptBuilder(Console console) {
        this.console = console;
    }

    public String build() {
        StringBuilder b = new StringBuilder();
        b.append("var Console={modules:");
        b.append(JSON.toJSON(console.modules().stream().map(consoleModule -> consoleModule.name).collect(Collectors.toList())));
        for (int i = 0; i < console.modules().size(); i++) {
            b.append(',');
            Console.ConsoleModule consoleModule = console.modules().get(i);
            b.append(consoleModule.name);
            b.append(':');
            if (consoleModule.options == null) {
                b.append("{}");
            } else {
                b.append(JSON.toJSON(consoleModule.options));
            }
        }
        b.append("};");
        return b.toString();
    }
}
