package rsa;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class KeySetUnitTest {

	@Test
	public void intParsing() {		
		BigInteger number = BigInteger.valueOf(2393459789L);
		assertEquals(number, KeySet.parseInt(KeySet.intString(number)));
	}
}
