package rsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSAKeyGenerator {

	private static final List<BigInteger> bigIntegerPrimes = new ArrayList<BigInteger>(6550);

	static {
		for (int i : SmallPrimes.smallPrimes)
			bigIntegerPrimes.add(BigInteger.valueOf(i));
	}

	public KeySet generateKeys() {
		System.out.println("Generating...");

		Random randomNumberGenerator = new MersenneTwister();

		// to protect against Pollard or Williams factorization methods
		// we require p+1 p-1 to be divisible by large primes
		BigInteger p = BigInteger.valueOf(2);
		while (!isDivisibleByLargePrime(p.add(BigInteger.ONE))
				&& !isDivisibleByLargePrime(p.subtract(BigInteger.ONE)))
			p = BigInteger.probablePrime(2048, randomNumberGenerator);

		BigInteger q = BigInteger.valueOf(2);
		while (!isDivisibleByLargePrime(q.add(BigInteger.ONE))
				&& !isDivisibleByLargePrime(q.subtract(BigInteger.ONE)))
			q = BigInteger.probablePrime(2050, randomNumberGenerator);

		BigInteger n = p.multiply(q);
		BigInteger totient = p.subtract(BigInteger.ONE).multiply(
				q.subtract(BigInteger.ONE));

		BigInteger e = BigInteger.valueOf(65537); // low Hamming weight and prime
		BigInteger d = e.modInverse(totient);

		System.out.println("Generated!");
		return new KeySet(n,e,d);
	}

	public boolean isDivisibleByLargePrime(BigInteger n) {
		BigInteger reduced = n;
		boolean foundDivisor = true;

		while (foundDivisor && !reduced.equals(BigInteger.ZERO)) {
			foundDivisor = false;

			for (BigInteger p : bigIntegerPrimes) {
				if (reduced.mod(p).equals(BigInteger.ZERO)) {
					reduced = reduced.divide(p);
					foundDivisor = true;
					break;
				}
			}
		}

		return reduced.compareTo(BigInteger.valueOf(65537)) > 0;
	}
	
	// to generate your own keys without running the GUI
	public static void main(String[] args) {
		KeySet myKey = new RSAKeyGenerator().generateKeys();
		System.out.println(myKey.toString());
	}
}
