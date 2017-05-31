package io.sited.template.impl.node;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.sited.StandardException;
import io.sited.template.Node;
import io.sited.template.impl.TemplateParser;
import io.sited.template.impl.node.processor.ForNodeProcessorImpl;
import io.sited.template.impl.node.processor.HtmlNodeProcessorImpl;
import io.sited.template.impl.node.processor.IfNodeProcessorImpl;
import io.sited.template.impl.node.processor.TextNodeProcessorImpl;
import io.sited.util.PriorityList;
import io.sited.util.source.Source;
import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.RowColumnVector;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.UUID;

/**
 * @author chi
 */
public class TemplateParserImpl implements TemplateParser {
    private final PriorityList<NodeProcessor> processors = new PriorityList<>((o1, o2) -> o1.priority() - o2.priority());

    public TemplateParserImpl() {
        addNodeProcessor(new IfNodeProcessorImpl());
        addNodeProcessor(new ForNodeProcessorImpl());
        addNodeProcessor(new TextNodeProcessorImpl());
        addNodeProcessor(new HtmlNodeProcessorImpl());
    }

    @Override
    public void addNodeProcessor(NodeProcessor nodeProcessor) {
        processors.add(nodeProcessor);
    }

    public Node parse(Source source) {
        try {
            net.htmlparser.jericho.Source doc = new net.htmlparser.jericho.Source(new StringReader(source.text()));
            Node node = new Node();
            node.id = UUID.randomUUID().toString();
            node.name = "ROOT";
            node.children = Lists.newArrayList();
            for (Element element : doc.getChildElements()) {
                node.children.add(doParse(element));
            }
            return node;
        } catch (IOException e) {
            throw new StandardException(e);
        }
    }

    public Node doParse(Element element) {
        String name = element.getName();
        RowColumnVector position = element.getRowColumnVector();
        Node node = new NodeImpl(position.getRow(), position.getColumn());
        node.id = UUID.randomUUID().toString();
        node.name = name.toLowerCase();
        node.children = Lists.newArrayList();
        node.attributes = Maps.newHashMap();

        if (element.getAttributes() != null) {
            for (Attribute attribute : element.getAttributes()) {
                node.attributes.put(attribute.getName(), attribute.getValue());
            }
        }

        int last = element.getContent().getBegin();
        for (net.htmlparser.jericho.Element child : element.getChildElements()) {
            int current = child.getBegin();

            if (current > last) {
                Node text = new NodeImpl(position.getRow(), position.getColumn());
                text.id = UUID.randomUUID().toString();
                text.name = "TEXT";
                text.attributes = Collections.singletonMap("text", element.getSource().subSequence(last, current).toString().trim());
                node.children.add(text);
            }

            if (!child.getName().startsWith("!--")) {
                node.children.add(doParse(child));
            }

            last = child.getEnd();
        }

        if (last < element.getContent().getEnd()) {
            Node text = new NodeImpl(position.getRow(), position.getColumn());
            text.id = UUID.randomUUID().toString();
            text.name = "TEXT";
            text.attributes = Collections.singletonMap("text", element.getSource().subSequence(last, element.getContent().getEnd()).toString().trim());
            node.children.add(text);
        }

        Node head = null;
        for (NodeProcessor processor : processors) {
            Node processed = processor.process(node);
            if (!processed.equals(node) && head == null) {
                head = processed;
            }
        }
        return head == null ? node : head;
    }

}
