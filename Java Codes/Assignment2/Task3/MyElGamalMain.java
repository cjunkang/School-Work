//Name:Chong Jun Kang
//StudentID:6461542

import java.math.BigInteger;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.Scanner;

public class MyElGamalMain {
	
	public static void main(String[] args) throws IOException {
		
		Scanner s = new Scanner(System.in);
		System.out.println("1:Encryption, 2:Decryption");
		int k = s.nextInt();
		
		switch(k)
		{
		case 1:
		Scanner se = new Scanner(System.in);
		MyElGamalE pkcE = new MyElGamalE();
		MyTransformerE trfmrE;
		trfmrE = pkcE.getEncrypter();
		System.out.println("Please Enter Text File to Encrypt: ");
		System.out.println("\nmb1.txt:1mb, mb10.txt:10mb, mb100.txt:100mb ");
		String filesize = se.nextLine();
		File inFileE = new File(filesize);
		int inputLengthE = (int)(inFileE.length()/100 + 1)*100;
		BufferedInputStream inStrmE = new BufferedInputStream(
				new FileInputStream(inFileE));
		BufferedOutputStream outStrm = new BufferedOutputStream(
				new FileOutputStream("ciphertext.txt"));
		System.err.println("Buffer size = " + inputLengthE);
		
		byte[] bufE = new byte[inputLengthE];
		
		int nBytesE = inStrmE.read(bufE);
		System.out.println("\n" + nBytesE + " bytes read");
		
		byte[] msgE = new byte[nBytesE];
		System.arraycopy(bufE, 0, msgE, 0, nBytesE);
		
		byte[] tmsgE = trfmrE.transform(msgE);
		System.out.println("" + tmsgE.length + " bytes produced");
		outStrm.write(tmsgE);
		inStrmE.close();
		outStrm.close();
		break;
		case 2:
		MyElGamalD pkcD = new MyElGamalD();
		MyTransformerD trfmrD;
			trfmrD = pkcD.getDecrypter();
		File inFileD = new File("ciphertext.txt");
		int inputLengthD = (int)(inFileD.length()/100 + 1)*100;
		BufferedInputStream inStrmD = new BufferedInputStream(
				new FileInputStream(inFileD));
		BufferedOutputStream outStrmD = new BufferedOutputStream(
				new FileOutputStream("deciphertext.txt"));
		System.err.println("Buffer size = " + inputLengthD);
		
		byte[] bufD = new byte[inputLengthD];
		
		int nBytesD = inStrmD.read(bufD);
		System.out.println("\n" + nBytesD + " bytes read");
		
		byte[] msgD = new byte[nBytesD];
		System.arraycopy(bufD, 0, msgD, 0, nBytesD);
		
		byte[] tmsgD = trfmrD.transform(msgD);
		System.out.println("" + tmsgD.length + " bytes produced");
		outStrmD.write(tmsgD);
		inStrmD.close();
		outStrmD.close();
		break;
		default:
		break;
		}
	}
	}



abstract class MyTransformerE {

	//Pad a message to a multiple of the block size, according to PKCS#5 scheme
	//Note that 1 <= nToPd <= blkSz extra bytes are added, = (byte)nToPd
	protected static byte[] pad(byte[] msg, int blkSz) {
		if (blkSz < 1 || blkSz > 255)
			throw new IllegalArgumentException("Block size out of range");
		int nToPd = blkSz - msg.length%blkSz;
		byte[] pdMsg = new byte[msg.length + nToPd];
		System.arraycopy(msg, 0, pdMsg, 0, msg.length);
		for (int i=msg.length; i< pdMsg.length; i++)
			pdMsg[i] = (byte)nToPd;
		return pdMsg;
	}

	//Remove the PKCS#5 padding
	protected static byte[] unpad(byte[] msg, int blkSz) {
		int nPd = (msg[msg.length - 1] + 256) % 256; //unsigned val in last byte
		//Chop off this many bytes
		byte[] rslt = new byte[msg.length - nPd];
		System.arraycopy(msg, 0, rslt, 0, rslt.length);
		return rslt;
	}

	//Divide a msg into (blkSz)-sized blocks
	//Assumes msg has been padded to an integral multiple of blkSz
	protected static byte[][] block(byte[] msg, int blkSz) {
		int nBlks = msg.length / blkSz;
		byte[][] ba = new byte[nBlks][blkSz];
		for (int i=0; i < nBlks; i++)
			for (int j=0; j < blkSz; j++)
				ba[i][j] = msg[i*blkSz + j];
		return ba;
	}

	//put a series of encoded blocks back into a single array of byytes.
	//The BigInt might be smaller than the block size, so fill the array
	//from the rear of each block.
	protected static byte[] unblock(byte[][] ba, int blkSz) {
		byte[] ub = new byte [ba.length * blkSz];
		for (int i=0; i<ba.length; i++) {
			int j = blkSz-1, k = ba[i].length-1;
			while (k >= 0) {
				ub[i*blkSz+j] = ba[i][k];
				k--; j--; }
		}
		return ub; }

	//Strip off the extra byte containing the sign bit (always 0 here)
	//returned by toByteArray() (ref D Bishop p123)
	protected static byte[] getBytes(BigInteger bg) {
		byte[] bts = bg.toByteArray();
		if (bg.bitLength()%8 != 0)
			return bts;
		else {
			byte[] sbts = new byte[bg.bitLength()/8];
			System.arraycopy(bts, 1, sbts, 0, sbts.length);
			return sbts;
		} 
	}
	
	public abstract byte[] transform(byte[] msg);
}
abstract class MyTransformerD {

	//Pad a message to a multiple of the block size, according to PKCS#5 scheme
	//Note that 1 <= nToPd <= blkSz extra bytes are added, = (byte)nToPd
	protected static byte[] pad(byte[] msg, int blkSz) {
		if (blkSz < 1 || blkSz > 255)
			throw new IllegalArgumentException("Block size out of range");
		int nToPd = blkSz - msg.length%blkSz;
		byte[] pdMsg = new byte[msg.length + nToPd];
		System.arraycopy(msg, 0, pdMsg, 0, msg.length);
		for (int i=msg.length; i< pdMsg.length; i++)
			pdMsg[i] = (byte)nToPd;
		return pdMsg;
	}

	//Remove the PKCS#5 padding
	protected static byte[] unpad(byte[] msg, int blkSz) {
		int nPd = (msg[msg.length - 1] + 256) % 256; //unsigned val in last byte
		//Chop off this many bytes
		byte[] rslt = new byte[msg.length - nPd];
		System.arraycopy(msg, 0, rslt, 0, rslt.length);
		return rslt;
	}

	//Divide a msg into (blkSz)-sized blocks
	//Assumes msg has been padded to an integral multiple of blkSz
	protected static byte[][] block(byte[] msg, int blkSz) {
		int nBlks = msg.length / blkSz;
		byte[][] ba = new byte[nBlks][blkSz];
		for (int i=0; i < nBlks; i++)
			for (int j=0; j < blkSz; j++)
				ba[i][j] = msg[i*blkSz + j];
		return ba;
	}

	//put a series of encoded blocks back into a single array of byytes.
	//The BigInt might be smaller than the block size, so fill the array
	//from the rear of each block.
	protected static byte[] unblock(byte[][] ba, int blkSz) {
		byte[] ub = new byte [ba.length * blkSz];
		for (int i=0; i<ba.length; i++) {
			int j = blkSz-1, k = ba[i].length-1;
			while (k >= 0) {
				ub[i*blkSz+j] = ba[i][k];
				k--; j--; }
		}
		return ub; }

	//Strip off the extra byte containing the sign bit (always 0 here)
	//returned by toByteArray() (ref D Bishop p123)
	protected static byte[] getBytes(BigInteger bg) {
		byte[] bts = bg.toByteArray();
		if (bg.bitLength()%8 != 0)
			return bts;
		else {
			byte[] sbts = new byte[bg.bitLength()/8];
			System.arraycopy(bts, 1, sbts, 0, sbts.length);
			return sbts;
		} 
	}
	
	public abstract byte[] transform(byte[] msg);
} 

class MyElGamalEncrypter extends MyTransformerE {

	private BigInteger p, g, r, pMinus2;
	private SecureRandom srng;
	private static final BigInteger ONE = BigInteger.ONE, TWO = ONE.add(ONE);

	//Assume p is prime, g is a gen mod p, r = g^a mod p (a = pvt key)
	public MyElGamalEncrypter(BigInteger p, BigInteger g, BigInteger r) {
		srng = new SecureRandom();
		this.p = p; this.g = g;  this.r = r;
		pMinus2 = p.subtract(TWO);
		System.out.println("Encryption key:");
		System.out.println("p = " + p.toString(16));
		System.out.println("g = " + g.toString(16));
		System.out.println("r = " + r.toString(16));
	}

	public byte[] transform(byte[] msg) {
		
		long startTm = System.currentTimeMillis();
		int blkSz = (p.bitLength() - 1)/8;
		byte[][] ba = block(pad(msg, blkSz), blkSz);
		byte[][] ba2 = new byte[2*ba.length][];
		BigInteger m, c0, c1, k;
		System.err.println("" + ba.length + " blocks");
		
		for (int i=0; i<ba.length; i++) {
			m = new BigInteger(1, ba[i]);      //make +ve BigInt out of current blk
			k = new BigInteger(p.bitLength(), srng);
			k = k.mod(pMinus2).add(ONE);       //rndm k, 0 < k < p-1
			
			c0 = g.modPow(k, p);               //compute ElGamal transform
			c1 = r.modPow(k, p).multiply(m).mod(p);
			
			ba2[2*i]   = getBytes(c0);         // convert to bytes
			ba2[2*i+1] = getBytes(c1);
			if (i%10 == 0) System.err.print("\rBlock " + i);
		}
		System.err.println("\rEncryption took " +
				(System.currentTimeMillis()-startTm) + " ms");
		return unblock(ba2, blkSz+1);
	}
}


class MyElGamalDecrypter extends MyTransformerD {
	private BigInteger p, a;

	public MyElGamalDecrypter(BigInteger p, BigInteger a) {
		this.p = p; this.a = a;
		System.out.println("Decryption key:");
		System.out.println("p = " + p.toString(16));
		System.out.println("a = " + a.toString(16));
	}

	public byte[] transform(byte[] msg) {
		
		long startTm = System.currentTimeMillis();
		int blkSz = (p.bitLength()-1)/8 + 1;
		byte[][] ba2 = block(msg, blkSz);
		byte[][] ba  = new byte[ba2.length/2][];
		BigInteger m, c0, c1, c;
		System.err.println("" + ba.length + " blocks");
		
		for (int i=0; i<ba.length; i++) {
			c0 = new BigInteger(1, ba2[2*i]);   //make +ve BigInts out of
			c1 = new BigInteger(1, ba2[2*i+1]); //current  blocks
			c = c0.modPow(a, p).modInverse(p);  //c0^-a mod p
			m = c.multiply(c1).mod(p);          //recover plain "text"
			ba[i] = getBytes(m);                //convert to bytes
			if (i%10 == 0) System.err.print("\rBlock " + i);
		}
		System.err.println("\nDecryption took " +
				(System.currentTimeMillis()-startTm) + " ms");
		return unpad(unblock(ba, blkSz-1), blkSz-1);
	}
}

class MyElGamalE {

	private BigInteger p, g, r, pMinus2;
	private SecureRandom srng;
	private static final int CRTTY = 300;
	private static final String configPath1 = "PublicElGamalConfig.txt";
	private static final BigInteger
	ZERO = BigInteger.ZERO,   ONE = BigInteger.ONE;

	public MyElGamalE() {
		srng = new SecureRandom();
		try {
			BufferedReader in = new BufferedReader(new FileReader(configPath1));
			p = new BigInteger(in.readLine(), 16);
			g = new BigInteger(in.readLine(), 16);
			r = new BigInteger(in.readLine(), 16);
			in.close();
		} catch (NumberFormatException ex) {
			System.err.println("Invalid data in config file - " + ex);
			System.exit(1);
		} catch (EOFException ex) {
			System.err.println("Unexpected end of config file");
			System.exit(1);
		} catch (IOException ex) {
			System.err.println("Trouble reading config file");
			System.exit(1);
		} catch (NullPointerException ex) {
			System.err.println("Trouble reading string from config file - " +ex);
			System.exit(1);
		}

		if (!p.isProbablePrime(CRTTY)) {
			System.err.println(p.toString(16) + " is not prime. Terminating.");
			System.exit(1);
		}
		if (g.mod(p).equals(ZERO)) {
			System.err.println(p.toString(16) + " divides " + g.toString(16) +". Terminating.");
			System.exit(1);
		}
	}


	public BigInteger getP() { return p; }
	public BigInteger getG() { return g; }
	public BigInteger getR() { return r; }

	//A message block is considered a BigInteger.
	//Returns pair of BigIntegers comprising the ElGamal cipher-"text"
	public BigInteger[] encrypt(BigInteger m) {
		BigInteger k = new BigInteger(p.bitLength(), srng);
		k = k.mod(pMinus2).add(ONE);
		BigInteger[] cipher = new BigInteger[2];
		cipher[0] = g.modPow(k, p);
		cipher[1] = r.modPow(k, p).multiply(m).mod(p);
		return cipher;
	}


	public MyElGamalEncrypter getEncrypter() {
		return new MyElGamalEncrypter(p, g, r);
	}

	public static void main(String[] args) {
		
			MyElGamalE sys = new MyElGamalE();
			SecureRandom sr = new SecureRandom();
			
			BigInteger p = sys.getP(),msg = (new BigInteger(p.bitLength(), sr)).mod(p);
			System.out.println("Message = " + msg.toString(16));
			
			BigInteger[] c = sys.encrypt(msg);
			System.out.println("Cipher: c0 = " + c[0].toString(16));
			System.out.println("Cipher: c1 = " + c[1].toString(16));
			
		 
	}
}
class MyElGamalD {

	private BigInteger p, g, a, r, pMinus2;
	private SecureRandom srng;
	private static final int CRTTY = 300;
	private static final String configPath = "MyElGamalConfig.txt";
	private static final String configPath1 = "PublicElGamalConfig.txt";
	private static final BigInteger
	ZERO = BigInteger.ZERO,   ONE = BigInteger.ONE,
	TWO  = ONE.add(ONE),    THREE = TWO.add(ONE);

	//Two constructors -
	//Option 1: generate a random system using specified key size; save config:
	public MyElGamalD(int kSz) {  //Random system with at least (kSz) bits in p
		srng = new SecureRandom();
		GeneratorFactory fact = new GeneratorFactory(kSz, CRTTY, srng);
		p = fact.getP(); pMinus2 = p.subtract(TWO);
		g = fact.getG();

		//a should be a random integer in range 1 < a < p-1
		BigInteger pmt = p.subtract(THREE);
		a = (new BigInteger(p.bitLength(), srng)).mod(pmt).add(TWO);
		r = g.modPow(a, p);
		saveConfig();
	}

	//Option 2 (default) -- construct a system from saved values:
	public MyElGamalD() {
		srng = new SecureRandom();
		try {
			BufferedReader in = new BufferedReader(new FileReader(configPath));
			p = new BigInteger(in.readLine(), 16);
			a = new BigInteger(in.readLine(), 16);
			g = new BigInteger(in.readLine(), 16);
			in.close();
		} catch (NumberFormatException ex) {
			System.err.println("Invalid data in config file - " + ex);
			System.exit(1);
		} catch (EOFException ex) {
			System.err.println("Unexpected end of config file");
			System.exit(1);
		} catch (IOException ex) {
			System.err.println("Trouble reading config file");
			System.exit(1);
		} catch (NullPointerException ex) {
			System.err.println("Trouble reading string from config file - " +ex);
			System.exit(1);
		}

		if (!p.isProbablePrime(CRTTY)) {
			System.err.println(p.toString(16) + " is not prime. Terminating.");
			System.exit(1);
		}
		if (g.mod(p).equals(ZERO)) {
			System.err.println(p.toString(16) + " divides " + g.toString(16) +
					". Terminating.");
			System.exit(1);
		}
	}

	public String toString() {
		String dspStg = "p = " + p.toString(16);
		String nl = dspStg.length() > 80? "\n\n": "\n";
		dspStg += nl + "g = " + g.toString(16);
		dspStg += nl + "a = " + a.toString(16);
		dspStg += nl + "r = " + r.toString(16);
		return dspStg;
	}

	public void saveConfig() {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(configPath));
			out.println(p.toString(16));      
			out.println(a.toString(16));
			out.println(g.toString(16));
			out.close();
			PrintWriter out1 = new PrintWriter(new FileWriter(configPath1));
			out1.println(p.toString(16));
			out1.println(g.toString(16));
			out1.println(r.toString(16));
			out1.close();
		} catch (IOException ex) {
			System.err.println("Could not save the config");
		}
	}

	public BigInteger getP() { return p; }
	public BigInteger getG() { return g; }
	public BigInteger getR() { return r; }


	public BigInteger decrypt(BigInteger c0, BigInteger c1) {
		BigInteger c = c0.modPow(a, p).modInverse(p); //c0^-a mod p
		return c.multiply(c1).mod(p);
	}

	public MyElGamalDecrypter getDecrypter() {
		return new MyElGamalDecrypter(p, a);
	}

	/* Test bed
	 * Run with single integer argument, kSz, to generate a system and save it.
	 * Run with no arguments to initialise a system from a saved config,
	 * generate a random BigInteger < p, encrypt it and decrypt it again.
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			new MyElGamalD(Integer.parseInt(args[0]));
			System.err.println("Config saved in " + configPath +"and"+configPath1);
		} 
	}
}

class GenTest {
  public static void main(String[] args) {
    boolean isGen = GeneratorFactory.isGenerator(
      new BigInteger(args[0]), new BigInteger(args[1]), 1);
    System.err.println(args[1] + (isGen?" is ":" is not ") + "a generator mod "+ args[0]);
  }
}

class GeneratorFactory {

	private int minBits, crtty; private SecureRandom srng; private BigInteger p, g; private static final BigInteger
	ZERO = BigInteger.ZERO,   ONE = BigInteger.ONE,TWO  = ONE.add(ONE);

	//Constructors
	public GeneratorFactory (int bits) { this(bits, 300); }

	public GeneratorFactory (int bits, int crtty) {
		this(bits, crtty, new SecureRandom());
	}

	public GeneratorFactory (int bits, int crtty, SecureRandom sr) {
		if (bits < 512)
			System.err.println("WARNING: Safe primes should be >= 512 bits long");
		this.minBits = bits;
		this.crtty   = crtty;
		this.srng    = sr;

		System.out.printf("Making a safe prime of at least %d bits...\n", bits);
		long startTm = System.currentTimeMillis(), endTm;
		makeSafePrimeAndGenerator();
		endTm = System.currentTimeMillis();
		System.err.printf("Generating p, g took %d ms\n", endTm - startTm);
		System.out.printf("p = %x (%d bits)\n", p, p.bitLength());
		System.out.printf("g = %x (%d bits)\n", g, g.bitLength());
	}

	public void makeSafePrimeAndGenerator() {
		BigInteger r = BigInteger.valueOf(0x7fffffff),t = new BigInteger(minBits, crtty, srng);

		//(1) make prime p
		do {
			r = r.add(ONE);
			p = TWO.multiply(r).multiply(t).add(ONE);
		} while (!p.isProbablePrime(crtty));

		//(2) obtain prime factorization of p-1 = 2rt
		HashSet<BigInteger> factors = new HashSet<BigInteger>();
		factors.add(t); factors.add(TWO);
		if (r.isProbablePrime(crtty))
			factors.add(r);
		else
			factors.addAll(primeFact(r));

		BigInteger pMinusOne = p.subtract(ONE), z, lnr;
		boolean isGen;
		do {
			isGen = true;
			do{
				g = new BigInteger(p.bitLength()-1, srng);//random, < p
			}while(g.equals(ZERO));
			for (BigInteger f: factors) { //check cond on g for each f
				z = pMinusOne.divide(f);
				//System.out.printf(" f= %d g = %d (%d bits) z = %d (%d bits)\n",f, g, g.bitLength(),z, z.bitLength());
				lnr = g.modPow(z, p);
				if (lnr.equals(ONE)) {
					isGen = false;
					break; }
			}
		} while (!isGen);
		// Now g is a generator mod p
	} // end of makeSafePrimeAndGenerator() method

	public static HashSet<BigInteger> primeFact(BigInteger n) {
		BigInteger nn = new BigInteger(n.toByteArray()); //clone n
		HashSet<BigInteger> factors = new HashSet<BigInteger>();
		BigInteger dvsr   = TWO,dvsrSq = dvsr.multiply(dvsr);

		while (dvsrSq.compareTo(nn) <= 0) {
			if (nn.mod(dvsr).equals(ZERO)) {
				//divisor <= sqrt of n
				//found a factor (must be prime):
				//add it to set
				factors.add(dvsr);
				while (nn.mod(dvsr).equals(ZERO)) //divide it out from n completely
					nn = nn.divide(dvsr);           //(ensures later factors are prime)
			}
			dvsr = dvsr.add(ONE);               //next possible divisor
			dvsrSq = dvsr.multiply(dvsr);
		}

		//if nn's largest prime factor had multiplicity >= 2, nn will now be 1;
		//if the multimplicity is only 1, the loop will have been exited leaving nn == this prime factor;
		if (nn.compareTo(ONE) > 0)
			factors.add(nn);
		return factors;
	}

	public BigInteger getP() { return p; }
	public BigInteger getG() { return g; }

	//Test whether p is prime, not p|g, and g is a generator mod p.
	public static boolean isGenerator(BigInteger p, BigInteger g, int crtty) {
		System.err.printf("Testing p = %s,\ng = %s\n",p.toString(16), g.toString(16));
		if (!p.isProbablePrime(crtty)) {
			System.err.println("p is not prime.");
			return false;
		}
		if (g.mod(p).equals(ZERO)) {
			System.err.println("p divides g.");
			return false;
		}
		//See note below on generator test
		BigInteger pMinusOne = p.subtract(ONE), z;
		System.err.println("Finding prime factors of p-1 ...");
		//Warning: a large prime factor will take a long time to find!
		HashSet<BigInteger> factors = primeFact(pMinusOne);
		boolean isGen = true;
		for (BigInteger f: factors) { //check cond on g for each f
			z = pMinusOne.divide(f);
			if (g.modPow(z, p).equals(ONE)) {
				isGen = false;
				System.err.println("g is not a generator mod p.");
				break;
			}
		}
		return isGen;
	}

	//Test driver
	public static void main(String[] args) {
		int bitLen = 512;
		if (args.length > 0) {
			try {
				bitLen = Integer.parseInt(args[0]);
			} catch(NumberFormatException ex) {
				bitLen = 512;
			} }
		GeneratorFactory fact = new GeneratorFactory(bitLen);
		BigInteger p = fact.getP(), g = fact.getG();
		System.out.println("p probable prime: "
				+ (p.isProbablePrime(300)?"yes":"no"));
		if (g.compareTo(p) < 0)
			System.out.println("g < p");
		else
			System.out.println("p divides g: " + (g.mod(p).equals(ZERO)?"yes":"no"));
	}
}