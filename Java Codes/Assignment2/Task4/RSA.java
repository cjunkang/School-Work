//Name:Chong Jun Kang
//StudentID:6461542

import javax.crypto.*;
import java.io.InputStream;
import java.security.*;
import java.util.Base64;
import java.util.*;  
import java.math.*; 
import java.util.Scanner;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.security.spec.*;
import java.io.*;
import java.nio.*;

public class RSA{

    public static boolean verify(BigInteger n,BigInteger e,BigInteger sig,BigInteger msg) throws Exception {
		
			System.out.println("n is : " + n);
			System.out.println("e is : " + e);
			System.out.println("sig is : " + sig);
			System.out.println("msg is : " + msg);
		if(msg.equals(sig.modPow(e,n)))
		{
			return true;
		}
       else{
		   return false;
		}
	}
    public static void main(String... argv) throws Exception {
        //First generate a public/private key pair
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		BigInteger ONE = new BigInteger("1");
		int bitLength = 256;
		SecureRandom rnd = new SecureRandom();
		boolean end = false;
		while(end == false)
		{
		System.out.println("Enter a int: " + "\n 1:Generate Publickey & Private Key" + "\n 2:Sign Message file mssg to make Sig.txt" + 
		"\n 3:Verify Sig.txt with public key" + "\n 4:Exit");
		
		int k = myObj.nextInt();
		switch(k)
		{
			case 1:
			System.out.println("Enter a int, p, up to 32-bit : ");
			Scanner sc = new Scanner(System.in);  // Create a Scanner object
			String pStr = sc.nextLine();
			BigInteger p = new BigInteger(pStr);
			
			System.out.println("Enter a int, q, up to 32-bit : ");
			Scanner sce = new Scanner(System.in);  // Create a Scanner object
			String qStr = sce.nextLine();
			BigInteger q = new BigInteger(qStr);
			
			BigInteger one = new BigInteger("1");
			BigInteger n = p.multiply(q);
			
			BigInteger e = new BigInteger("15");
			BigInteger p1 = p.subtract(one);
			BigInteger q1 = q.subtract(one);
			BigInteger phi = p1.multiply(q1);
			
			BigInteger d = e.modInverse(phi);
			String nStr = n.toString();
			String dStr = d.toString();
			String eStr = e.toString();
			System.out.println("p-1 String: " + p1.toString());
			System.out.println("q-1 String: " + q1.toString());
			System.out.println("phi String: " + phi.toString());
			System.out.println("n String: " + nStr);
			System.out.println("d String: " + dStr);
			
			BufferedOutputStream outPK = new BufferedOutputStream(new FileOutputStream("pk.txt"));
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append(nStr).append(",").append(eStr);
			String cPK = strBuilder.toString();
			System.out.println("contents: " + cPK);
			byte[] bytePK = cPK.getBytes();
			outPK.write(bytePK);
			outPK.close();
			
			BufferedOutputStream outSK = new BufferedOutputStream(new FileOutputStream("sk.txt"));
			StringBuilder strBuilder1 = new StringBuilder();
			strBuilder1.append(nStr).append(",").append(pStr).append(",").append(qStr).append(",").append(dStr);
			String cSK = strBuilder1.toString();
				//System.out.println("contents: " + cSK);
			byte[] byteSK = cSK.getBytes();
			outSK.write(byteSK);
			outSK.close();
			KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
			//PublicKey puKey = kf.generatePublic(new X509EncodedKeySpec(cPK.getBytes()));
			//PrivateKey priKey = kf.generatePrivate(new PKCS8EncodedKeySpec(cSK.getBytes()));
			//System.out.println("PublicKey Key: " + puKey +  "PrivateKey Key: " + priKey);
			
			/*
			BigInteger randomP = BigInteger.probablePrime(bitLength, rnd);
			System.out.println("RandomPrime P: " + randomP);
			BigInteger p = randomP;  
			BigInteger randomQ = BigInteger.probablePrime(bitLength, rnd);
			System.out.println("RandomPrime Q: " + randomQ);
			BigInteger q = randomQ; 
			BigInteger pOne = p.subtract(ONE);
			BigInteger qOne = q.subtract(ONE);
			BigInteger phi = phiLcm(pOne,qOne);
			BigInteger N = p.multiply(q);
			System.out.println("Prime N: " + N);
			//int e = 2;
			System.out.println("Prime phi:" + phi);
			BigInteger primeE = new BigInteger("65537");
			
			System.out.println("Prime E: " + primeE);
			BigInteger primeD = primeE.modInverse(phi);
			RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(N,primeE);
			
			KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
			RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(N,primeD);	
			//System.out.println("PubKey: " + pubSpec.getModulus() + " " + pubSpec.getPublicExponent());
			//System.out.println("PriKey: " + privSpec.toString());

			PublicKey pubKey = kf.generatePublic(pubSpec);
			PrivateKey priKey = kf.generatePrivate(privSpec);
			
			
			System.out.println("PubKey: " + pubKey);
			System.out.println("PriKey: " + priKey);
			
			byte[] pubKeyBytes = pubKey.getEncoded();
			byte[] priKeyBytes = priKey.getEncoded();
			
			BufferedOutputStream outPK = new BufferedOutputStream(new FileOutputStream("pk.txt"));
			outPK.write(pubKeyBytes);
			outPK.close();

			BufferedOutputStream outSK = new BufferedOutputStream(new FileOutputStream("sk.txt"));
			outSK.write(priKeyBytes);
			outSK.close();
			*/
			break;
			case 2:
			File f = new File("msg.txt");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			byte[] msgBytes = new byte[(int) f.length()];
			dis.readFully(msgBytes);
			dis.close();
			String stringM = new String(msgBytes);
			System.out.println("String MESSAGE: " + stringM);
			BigInteger mSG = new BigInteger(stringM);
			
			File fe = new File("sk.txt");
			FileInputStream fise = new FileInputStream(fe);
			DataInputStream dise = new DataInputStream(fise);
			byte[] secretKey = new byte[(int) fe.length()];
			dise.readFully(secretKey);
			dise.close();
			String stringSK = new String(secretKey);
			String[] myStringSK = stringSK.split(",");
			String nSK = myStringSK[0];
			String pSK = myStringSK[1];
			String qSK = myStringSK[2];
			String dSK = myStringSK[3];
			//System.out.println("String nSK: " + nSK);
			//System.out.println("String pSK: " + pSK);
			//System.out.println("String qSK: " + qSK);
			//System.out.println("String dSK: " + dSK);
			BigInteger bINSK = new BigInteger(nSK);	
			BigInteger bIDSK = new BigInteger(dSK);	
			
			System.out.println("Message = " + mSG);
			System.out.println("N Value = " + bINSK);
			System.out.println("D Value = " + bIDSK);
			
			BigInteger S = mSG.modPow(bIDSK,bINSK);
			System.out.println("Signature = " + S);
			String sigString = S.toString();
			byte[] sigBytes = sigString.getBytes();
			//Signature sign = Signature.getInstance("SHA256withRSA");
			//sign.initSign();
			BufferedOutputStream outS = new BufferedOutputStream(new FileOutputStream("sig.txt"));
			outS.write(sigBytes);
			outS.close();
			
			/*
			File f = new File("msg.txt");
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			byte[] keyBytes = new byte[(int) f.length()];
			dis.readFully(keyBytes);
			dis.close();
			String stringM = new String(keyBytes);
			System.out.println("String M: " + stringM);
			BigInteger mSK = new BigInteger(stringM.getBytes());	
			
			File fe = new File("sk.txt");
			FileInputStream fise = new FileInputStream(fe);
			DataInputStream dise = new DataInputStream(fise);
			byte[] encKey = new byte[(int) fe.length()];
			dise.readFully(encKey);
			dise.close();
				   //Let's sign our message
			String signature = sign(stringM, encKey);
			byte[] bSIG = signature.getBytes();
			System.out.println("Signature: " + signature);
			BufferedOutputStream outSIG = new BufferedOutputStream(new FileOutputStream("sig.txt"));
			outSIG.write(bSIG);
			outSIG.close();
			*/
			break;
			case 3:
			File vf = new File("msg.txt");
			FileInputStream vfis = new FileInputStream(vf);
			DataInputStream vdis = new DataInputStream(vfis);
			byte[] msgVBytes = new byte[(int) vf.length()];
			vdis.readFully(msgVBytes);
			vdis.close();
			String stringVM = new String(msgVBytes);
			System.out.println("String MESSAGE: " + stringVM);
			BigInteger mMSG = new BigInteger(stringVM); // Msg Value
			
			File vs = new File("sig.txt");
			FileInputStream vfisig = new FileInputStream(vs);
			DataInputStream disig = new DataInputStream(vfisig);
			byte[] sigVBytes = new byte[(int) vs.length()];
			disig.readFully(sigVBytes);
			disig.close();
			String stringVS = new String(sigVBytes);
			System.out.println("String Signature: " + stringVS);
			BigInteger mSIG = new BigInteger(stringVS); // Sig Value
			
			File fPK = new File("pk.txt");
			FileInputStream fisPK = new FileInputStream(fPK);
			DataInputStream disPK = new DataInputStream(fisPK);
			byte[] publicKey = new byte[(int) fPK.length()];
			disPK.readFully(publicKey);
			disPK.close();
			String stringKM = new String(publicKey);
			String[] myStringPK = stringKM.split(",");
			String nPK = myStringPK[0];
			String ePK = myStringPK[1];
			BigInteger nPKE = new BigInteger(nPK);	//n value
			BigInteger ePKE = new BigInteger(ePK);	//e value
			
			boolean verification = verify(nPKE,ePKE,mSIG,mMSG);
			
				if(verification == true)
				{
				System.out.println("Signature is : " + verification);
				}
				else
				{
				System.out.println("Signature is : " + verification);	
				}
			
			// 10 = modInverse.()mod
			/*
			File fMsg = new File("msg.txt");
			FileInputStream fisMSG = new FileInputStream(fMsg);
			DataInputStream disMSG = new DataInputStream(fisMSG);
			byte[] keyBytesMSG = new byte[(int) fMsg.length()];
			disMSG.readFully(keyBytesMSG);
			disMSG.close();
			String stringMSG = new String(keyBytesMSG);
			
			File fPK = new File("pk.txt");
			FileInputStream fisPK = new FileInputStream(fPK);
			DataInputStream disPK = new DataInputStream(fisPK);
			byte[] bytePK = new byte[(int) fPK.length()];
			disPK.readFully(bytePK);
			disPK.close();
			String stringKM = new String(bytePK);
			//System.out.println("String M: " + stringKM);
			BigInteger mPK = new BigInteger(stringKM.getBytes());	
			
			File fer = new File("sig.txt");
			FileInputStream fiser = new FileInputStream(fer);
			DataInputStream diser = new DataInputStream(fiser);
			byte[] sigKey = new byte[(int) fer.length()];
			diser.readFully(sigKey);
			diser.close();
			String sigVerify = new String(sigKey);
			//Let's check the signature
			boolean isCorrect = verify(stringMSG, sigVerify, bytePK);
			System.out.println("Signature correct: " + isCorrect);
			*/
			break;
			default:
			end = true;
			break;
			}
		}
    }
}