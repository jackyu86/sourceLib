package io.sited.http;

import com.google.common.collect.Lists;
import io.sited.util.XML;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author chi
 */
public class XMLTest {

    @Test
    public void toXML() {
        XMLResponse response = new XMLResponse();
        response.names = Lists.newArrayList("1", "2", null);
        String xml = XML.toXML(response);

        XMLResponse parsed = XML.fromXML(xml, XMLResponse.class);
        Assert.assertNull(parsed.age);
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "response")
    public static class XMLResponse {
        @XmlElementWrapper(name = "names")
        @XmlElement(name = "name")
        public List<String> names;

        @XmlElement(name = "age")
        public String age;
    }
}