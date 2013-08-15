package rsa;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.Assert.*;

import rsa.RSAKeyGenerator;

@SuppressWarnings("unused")
public class RSAEncryptorUnitTest {

	@Test
	public void integerToMessageConsistency() {
		RSAEncryptor encryptor = new RSAEncryptor();

		String msg = "77687920aaaaaerthjkygu207468657265212e3fwhy hello there!.?";
		assertEquals(msg, encryptor.integerToMessage(encryptor
				.messageToInteger(msg)));
	}

	@Test
	public void testEncryptDecrypt() {
		RSAEncryptor encryptor = new RSAEncryptor();
		BigInteger message, n, publicKey, privateKey, cipherText;

		message = BigInteger.valueOf(65);
		n = BigInteger.valueOf(3233);
		publicKey = BigInteger.valueOf(17);
		privateKey = BigInteger.valueOf(2753);
		cipherText = BigInteger.valueOf(2790);

		assertEquals(cipherText, encryptor.encrypt(message, publicKey, n));
		assertEquals(message, encryptor.decrypt(cipherText, privateKey, n));
	}

	@Test
	public void endToEndTest() {
		BigInteger n, publicKey, privateKey, msg, result, cipherNumber;

		RSAEncryptor encryptor = new RSAEncryptor();
		String plainText = "To all interested parties: shipyard @ 7 for"
				+ " pickup. The eagle has landed.";

		KeySet set = new RSAKeyGenerator().generateKeys();
		
		msg = encryptor.messageToInteger(plainText);
		
		cipherNumber = encryptor.encrypt(msg, set.publicKey, set.modulus);
		result = encryptor.decrypt(cipherNumber, set.privateKey, set.modulus);
		
		assertEquals(plainText, encryptor.integerToMessage(result));
	}
}
