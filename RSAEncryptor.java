package rsa;

import java.math.BigInteger;

public class RSAEncryptor {
	
	public BigInteger messageToInteger(String message) {
		return new BigInteger(1, message.getBytes());
	}
	
	public String integerToMessage(BigInteger message) {
		return new String(message.toByteArray());
	}
	
	public BigInteger encrypt(BigInteger plainText, BigInteger publicKey, BigInteger modulus) {
		return plainText.modPow(publicKey, modulus);
	}
	
	public BigInteger decrypt(BigInteger cipherText, BigInteger privateKey, BigInteger modulus) {
		return cipherText.modPow(privateKey, modulus);
	}
}
