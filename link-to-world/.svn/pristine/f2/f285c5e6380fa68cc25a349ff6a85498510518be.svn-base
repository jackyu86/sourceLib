package io.sited.util;

import io.sited.db.FindView;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chi
 */
public class CodeBuilderTest {
    private final Logger logger = LoggerFactory.getLogger(CodeBuilderTest.class);
    FindView<String> field = new FindView<>();

    @Test
    public void typeLiteral() throws NoSuchFieldException {
        logger.info("", field);
        String code = CodeBuilder.typeVariableLiteral(this.getClass().getDeclaredField("field").getGenericType());
        Assert.assertEquals("io.sited.util.Types.generic(io.sited.db.FindView.class, new java.lang.reflect.Type[]{java.lang.String.class})", code);
    }
}