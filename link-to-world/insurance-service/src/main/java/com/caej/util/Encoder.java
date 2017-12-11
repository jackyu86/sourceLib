package com.caej.util;
import java.io.UnsupportedEncodingException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Encoder {

   public static String getEncodeKey(){
	   return "Cd3J0qqNn1E5yr6boVg0pOEpCTA69gbi";
	}

	private static Provider provider = new BouncyCastleProvider();
	static {
		Security.addProvider(provider);
	}
	
	
	public static String aesEncodeAsHex(String data) {
		String key = getEncodeKey();
		if (data == null) {return null;}
		if (key == null) {
			throw new IllegalArgumentException("'key' must not be empty");
		}
		return aesEncode(data,key);
		}
	
	
	
	

	public static String aesEncode(String data, String key) {
		try {
			return new String(Base64.encodeBase64(aes(data.getBytes("UTF-8"), key.getBytes("UTF-8"), 128, Cipher.ENCRYPT_MODE)), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
		
	public static String aesDecode(String dataBase64, String key) {
		if (dataBase64 == null) {return null;}
		try {
			return new String(aes(Base64.decodeBase64(dataBase64.getBytes("UTF-8")), key.getBytes("UTF-8"), 128, Cipher.DECRYPT_MODE), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
		
	private static byte[] aes(byte[] data, byte[] key, int keyLen, int opMode) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES", provider);
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG"); // provider
			secureRandom.setSeed(key);
			kgen.init(keyLen, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
			
			 /* mode:	ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/> 
			 * padding: Nopadding/PKCS5Padding/ISO10126Padding
			 */
			Cipher cipher = Cipher.getInstance("AES", provider); // ECB/PKCS5Padding
			cipher.init(opMode, keySpec);
			
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
}