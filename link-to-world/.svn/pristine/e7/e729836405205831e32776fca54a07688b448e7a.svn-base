package io.sited.user.service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import io.sited.StandardException;

/**
 * @author chi
 */
public class PasswordHasher {
    private final String salt;
    private final String password;

    public PasswordHasher(String salt, String password) {
        this.salt = salt;
        this.password = password;
    }

    public String hash(int iteration) {
        if (iteration <= 0) {
            throw new StandardException("iteration must be larger than 0");
        }
        String hashedPassword = password;
        for (int i = 0; i < iteration; i++) {
            hashedPassword = Hashing.md5().hashString(salt + ":" + hashedPassword, Charsets.UTF_8).toString();
        }
        return hashedPassword;
    }
}

