package io.sited.i18n;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author chi
 */
public interface I18nConfig {
    I18nConfig add(MessageRepository messageRepository);

    I18nConfig add(String propertyPath);

    Optional<Message> message(String key, Locale locale);

    List<Message> messages(String page, Locale locale);
}
