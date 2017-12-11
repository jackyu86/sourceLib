package io.sited.i18n.impl.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.i18n.Message;
import io.sited.i18n.MessageRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
public class CompositeMessageRepository implements MessageRepository {
    private final Locale locale;
    private final List<MessageRepository> repositories = Lists.newArrayList();

    public CompositeMessageRepository(Locale locale) {
        this.locale = locale;
    }

    public void add(MessageRepository repository) {
        repositories.add(0, repository);
    }

    @Override
    public Iterator<Message> iterator() {
        Map<String, Message> messages = Maps.newHashMap();
        for (MessageRepository repository : repositories) {
            for (Message message : repository) {
                if (!messages.containsKey(message.key)) {
                    messages.put(message.key, message);
                }
            }
        }
        return Lists.newArrayList(messages.values()).iterator();
    }

    @Override
    public Locale locale() {
        return locale;
    }

    @Override
    public Optional<Message> message(String key) {
        for (MessageRepository repository : repositories) {
            Optional<Message> message = repository.message(key);
            if (message.isPresent()) {
                return message;
            }
        }
        return Optional.empty();
    }
}
