package io.sited.file.admin.service;

import io.sited.StandardException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.function.Function;

/**
 * @author chi
 */
public class PinYinPathSuggesterImpl implements Function<String, String> {
    HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();

    public PinYinPathSuggesterImpl() {
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    @Override
    public String apply(String name) {
        try {
            String value = PinyinHelper.toHanYuPinyinString(name, outputFormat, "-", true);
            StringBuilder b = new StringBuilder();
            boolean skip = false;
            String input = value.trim();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (Character.isAlphabetic(c)) {
                    b.append(c);
                    skip = false;
                } else {
                    if (!skip) {
                        b.append('-');
                        skip = true;
                    }
                }
            }
            return b.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new StandardException(e);
        }
    }
}
