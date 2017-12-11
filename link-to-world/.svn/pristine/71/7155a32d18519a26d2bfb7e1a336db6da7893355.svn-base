package io.sited.util;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import io.sited.StandardException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author chi
 */
public class AES {
    private static final BaseEncoding BASE32 = BaseEncoding.base32()
        .omitPadding().lowerCase();

    final SecretKey secretKey;

    final byte[] salt = {
        (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
        (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99
    };

    public AES(String key) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt, 65536, 128);
            secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new StandardException(e);
        }
    }

    public String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypt = cipher.doFinal(content.getBytes(Charsets.UTF_8));
            return BASE32.encode(encrypt);
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException e) {
            throw new StandardException(e);
        }
    }

    public String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypt = cipher.doFinal(BASE32.decode(content));
            return new String(decrypt, Charsets.UTF_8);
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException e) {
            throw new StandardException(e);
        }
    }
}
