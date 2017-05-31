package io.sited.i18n.impl.service;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author chi
 */
public class PropertiesMessageRepositoryTest {
    @Test
    public void get() {
        PropertiesMessageRepository propertiesMessageRepository = new PropertiesMessageRepository("message/message2_zh-CN.properties");
        Assert.assertEquals("Hello World", propertiesMessageRepository.message("message.hello_world").get().get("World"));
    }

    @Test
    public void iterate() {
        PropertiesMessageRepository propertiesMessageRepository = new PropertiesMessageRepository("message/message_zh-CN.properties");
        Assert.assertEquals(2, Lists.newArrayList(propertiesMessageRepository).size());
    }
}