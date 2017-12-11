package io.sited.i18n;

import org.slf4j.helpers.MessageFormatter;

/**
 * @author chi
 */
public class Message {
    public String key;
    public String value;

    public String get(Object... args) {
        return MessageFormatter.arrayFormat(value, args).getMessage();
    }

    public boolean isModule(String module) {
        return key.startsWith(module + '.');
    }
}
