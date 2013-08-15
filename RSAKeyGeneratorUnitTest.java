package rsa;
import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.Assert.*;

import rsa.RSAKeyGenerator;


@SuppressWarnings("unused")
public class RSAKeyGeneratorUnitTest {

	@Test
	public void testDivisibleByLargePrime() {
		RSAKeyGenerator keygen = new RSAKeyGenerator();
		
		assertTrue(keygen.isDivisibleByLargePrime(BigInteger.valueOf(78031)));
		assertTrue(keygen.isDivisibleByLargePrime(BigInteger.valueOf(2*9*5*7*11*78031)));
		assertFalse(keygen.isDivisibleByLargePrime(BigInteger.valueOf(2*9*5*7*11*63499)));
		assertFalse(keygen.isDivisibleByLargePrime(BigInteger.valueOf(2)));
		assertFalse(keygen.isDivisibleByLargePrime(BigInteger.valueOf(9)));
	}
	
}
