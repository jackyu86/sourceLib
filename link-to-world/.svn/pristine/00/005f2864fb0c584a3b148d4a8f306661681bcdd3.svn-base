package app.dealer.dealer.service;

import com.google.common.hash.Hashing;
import org.junit.Test;

import java.security.SecureRandom;

/**
 * @author Jonathan.Guo
 */
public class DealerUserServiceTest {
    @Test
    public void salt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        System.out.println(Hashing.md5().hashBytes(bytes).toString());
    }


}