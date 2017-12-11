package io.sited.i18n;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.Module;
import io.sited.ModuleInfo;
import io.sited.i18n.impl.service.CompositeMessageRepository;
import io.sited.i18n.impl.service.PropertiesMessageRepository;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author chi
 */
@ModuleInfo(name = "i18n")
public class I18nModule extends Module implements I18nConfig {
    private final Map<Locale, CompositeMessageRepository> repositories = Maps.newHashMap();

    @Override
    protected void configure() throws Exception {
        bind(I18nConfig.class, this);
        export(I18nConfig.class);
    }

    @Override
    public I18nConfig add(MessageRepository messageRepository) {
        if (repositories.containsKey(messageRepository.locale())) {
            repositories.get(messageRepository.locale()).add(messageRepository);
        } else {
            CompositeMessageRepository repository = new CompositeMessageRepository(messageRepository.locale());
            repository.add(messageRepository);
            repositories.put(messageRepository.locale(), repository);
        }
        return this;
    }

    @Override
    public I18nConfig add(String propertyPath) {
        PropertiesMessageRepository propertiesMessageRepository = new PropertiesMessageRepository(propertyPath);
        return add(propertiesMessageRepository);
    }

    @Override
    public Optional<Message> message(String key, Locale locale) {
        CompositeMessageRepository repository = repositories.get(locale);
        if (repository == null) {
            return Optional.empty();
        }
        return repository.message(key);
    }

    @Override
    public List<Message> messages(String page, Locale locale) {
        List<Message> messages = Lists.newArrayList();
        CompositeMessageRepository repository = repositories.get(locale);
        if (repository != null) {
            String prefix = page + '.';
            for (Message message : repository) {
                if (message.key.startsWith(prefix)) {
                    messages.add(message);
                }
            }
        }
        return messages;
    }
}
