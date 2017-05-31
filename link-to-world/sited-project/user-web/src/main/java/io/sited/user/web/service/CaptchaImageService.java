package io.sited.user.web.service;

import com.github.cage.Cage;
import com.github.cage.image.ConstantColorGenerator;
import io.sited.StandardException;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.cage.Cage.DEFAULT_COMPRESS_RATIO;

/**
 * @author chi
 */
public class CaptchaImageService {
    private final Cage cage = new Cage(null, null, new ConstantColorGenerator(java.awt.Color.BLACK), null, DEFAULT_COMPRESS_RATIO, null, null);

    public CaptchaImage create() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CaptchaImage captchaImage = new CaptchaImage();
        captchaImage.text = generate();
        try {
            cage.draw(captchaImage.text, outputStream);

            outputStream.flush();
            captchaImage.content = outputStream.toByteArray();
            outputStream.close();
            return captchaImage;
        } catch (Exception e) {
            throw new StandardException(e);
        }
    }

    private String generate() {
        StringBuilder b = new StringBuilder();
        int count = 4;
        while (count > 0) {
            b.append(ThreadLocalRandom.current().nextInt(0, 9));
            count--;
        }
        return b.toString();
    }
}
