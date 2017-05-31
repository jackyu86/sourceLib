package io.sited.i18n.impl.service;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import io.sited.StandardException;
import io.sited.i18n.Message;
import io.sited.i18n.MessageRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertiesMessageRepository implements MessageRepository {
    private static final Pattern MESSAGE_PROPERTY_PATH_PATTERN = Pattern.compile(".*_([a-z]{2}\\-[A-Z]{2})\\.properties");
    private final Locale locale;
    private final Properties properties = new Properties();

    public PropertiesMessageRepository(String propertyPath) {
        Matcher matcher = MESSAGE_PROPERTY_PATH_PATTERN.matcher(propertyPath);
        if (!matcher.matches()) {
            throw new StandardException("invalid message file name, name={}", propertyPath);
        }
        locale = Locale.forLanguageTag(matcher.group(1));
        URL resource = com.google.common.io.Resources.getResource(propertyPath);
        try (InputStreamReader reader = new InputStreamReader(resource.openStream(), Charsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    @Override
    public Locale locale() {
        return locale;
    }

    @Override
    public Optional<Message> message(String key) {
        if (key != null && properties.containsKey(key)) {
            Message message = new Message();
            message.key = key;
            message.value = properties.getProperty(key);
            return Optional.of(message);
        }
        return Optional.empty();
    }


    @Override
    public Iterator<Message> iterator() {
        List<Message> messages = Lists.newArrayList();
        properties.stringPropertyNames().forEach(key -> {
            Message message = new Message();
            message.key = key;
            message.value = properties.getProperty(key);
            messages.add(message);
        });
        return messages.iterator();
    }
}