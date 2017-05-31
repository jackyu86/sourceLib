package io.sited.user.service;


import io.sited.user.UserOptions;

import javax.inject.Inject;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

/**
 * @author chi
 */
public class PinCodeProvider implements Supplier<String> {
    @Inject
    UserOptions userOptions;

    public String get() {
        if (userOptions.pinCode.length <= 0) {
            return "1111";
        } else {
            StringBuilder b = new StringBuilder();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < userOptions.pinCode.length; i++) {
                b.append(Math.abs(random.nextInt(10)));
            }
            return b.toString();
        }
    }
}
