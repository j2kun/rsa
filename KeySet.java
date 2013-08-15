package rsa;

import java.math.BigInteger;

public class KeySet {
	BigInteger modulus, publicKey, privateKey;
	public static final int radix = 36;
	
	public KeySet(BigInteger modulus, BigInteger publicKeyExponent,
			BigInteger privateKeyExponent) {
		this.modulus = modulus;
		this.publicKey = publicKeyExponent;
		this.privateKey = privateKeyExponent;
	}

	public String toString() {
		return "modulus = " + modulus.toString() + "\n\n" 
			 + "public exponent = " + publicKey.toString() + "\n\n" 
			 + "private exponent = " + privateKey.toString();
	}
	
	public static BigInteger parseInt(String input) {
		return new BigInteger(input, KeySet.radix);
	}
	
	public static String intString(BigInteger input) {
		return input.toString(KeySet.radix);
	}
}