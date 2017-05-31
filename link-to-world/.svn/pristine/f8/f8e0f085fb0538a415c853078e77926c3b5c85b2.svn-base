package io.sited.template.impl.attribute.writer;

/**
 * @author miller
 */
public class HtmlEncoder {
    private static final String[] htmlCode = new String[256];

    static {
        for (int i = 0; i < 10; i++) {
            htmlCode[i] = "&#00" + i + ";";
        }

        for (int i = 10; i < 32; i++) {
            htmlCode[i] = "&#0" + i + ";";
        }

        for (int i = 32; i < 128; i++) {
            htmlCode[i] = String.valueOf((char) i);
        }

        // Special characters
        htmlCode['\t'] = "\t";
        htmlCode['\n'] = "<" + HtmlTags.NEWLINE + " />\n";
        htmlCode['\"'] = "&quot;"; // double quote
        htmlCode['&'] = "&amp;"; // ampersand
        htmlCode['<'] = "&lt;"; // lower than
        htmlCode['>'] = "&gt;"; // greater than

        for (int i = 128; i < 256; i++) {
            htmlCode[i] = "&#" + i + ";";
        }
    }

    public static String encode(String string) {
        int n = string.length();
        char character;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < n; i++) {
            character = string.charAt(i);
            if (character < 256) {
                buffer.append(htmlCode[character]);
            } else {
                buffer.append("&#").append((int) character).append(';');
            }
        }
        return buffer.toString();
    }
}
