//Name:Chong Jun Kang
//StudentID:6461542

import java.net.*; 
import java.io.*; 
import java.security.SecureRandom;
import java.util.Random;
import java.math.BigInteger;
import java.math.*;

  
public class Alice { 
			
    public static void main(String[] args) throws IOException 
    { 
			BigInteger bits = new BigInteger("100");
			BigInteger qInt = new BigInteger("100");
			BigInteger pInt = new BigInteger("100");
			boolean isPrime = false;
			BigInteger TWO = new BigInteger("2");
			BigInteger ONE = new BigInteger("1");
			BigInteger x;
			
        try {
			
			while(isPrime == false)
			{
				qInt = createPrimeBigger(bits);
				x = qInt.multiply(TWO);
				x = x.add(ONE);
				String val = "" + x;
				//System.out.println(x);
				BigInteger temp = new BigInteger(val);
				boolean check = returnPrime(temp);
				//System.out.println(check); check boolean status
					if (check == true)
					{
						isPrime = true;
						pInt = temp;
					}
			}
			
            int port = 8088;
			
			
			//System.out.println("Prime Number q: " + q + " Prime Number p: " + p);

			System.out.println("Prime Number q: " + qInt);
			System.out.println("Prime Number p: " + pInt);
            // form g,h,private key 
            //int b = 3; 
			BigInteger maxLimit;
			maxLimit = pInt.subtract(TWO);
			BigInteger minLimit = new BigInteger("2");
			BigInteger bigInteger = maxLimit.subtract(minLimit);
			Random randNum = new Random();
			int len = maxLimit.bitLength();
			BigInteger hInt = new BigInteger(len, randNum);
			  if (hInt.compareTo(minLimit) < 0)
				 hInt = hInt.add(minLimit);
			  if (hInt.compareTo(bigInteger) >= 0)
				 hInt = hInt.mod(bigInteger).add(minLimit);
			System.out.println("The random BigInteger h= " + hInt);
			BigInteger gInt = hInt.pow(2);
			gInt = gInt.mod(pInt);
			System.out.println("The BigInteger g= " + gInt);
			
			maxLimit = qInt.subtract(ONE);
			minLimit = new BigInteger("1");
			bigInteger = maxLimit.subtract(minLimit);
			randNum = new Random();
			len = maxLimit.bitLength();
			BigInteger aInt = new BigInteger(len, randNum);
			  if (aInt.compareTo(minLimit) < 0)
				 aInt = aInt.add(minLimit);
			  if (aInt.compareTo(bigInteger) >= 0)
				 aInt = aInt.mod(bigInteger).add(minLimit);
			BigInteger PriKey = aInt;
			System.out.println("The Private Key= " + PriKey);
			// public key g^aInt mod pInt
			
			BigInteger PubKey = gInt.modPow(aInt,pInt);
			System.out.println("The Public Key= " + PubKey);
            // Client p, g, and key 
            BigInteger clientP, clientG, clientA, B, CommonKey; 
            String Bstr,Pstr,Qstr,Gstr; 
  
            // Established the Connection 
            ServerSocket serverSocket = new ServerSocket(port); 
            System.out.println("Waiting for Bob on port " + serverSocket.getLocalPort() + "..."); 
            Socket server = serverSocket.accept(); 
            System.out.println("Just connected to " + server.getRemoteSocketAddress()); 
			
			Bstr = PubKey.toString();
			Pstr = pInt.toString(); 
            Qstr = qInt.toString(); 
            Gstr = gInt.toString(); 
			    			  
            // Sends data to client 
            // Value of Public key
            OutputStream outToclient = server.getOutputStream(); 
            DataOutputStream out = new DataOutputStream(outToclient); 
  
            out.writeUTF(Pstr); // Sending pInt 
            out.writeUTF(Qstr); // Sending qInt
            out.writeUTF(Gstr); // Sending gInt
            out.writeUTF(Bstr); // Sending B 
  
  
            // Accepts the data from client 
            DataInputStream in = new DataInputStream(server.getInputStream()); 
  
            clientP = new BigInteger(in.readUTF()); // to accept public key
            System.out.println("From Client : Public Key = " + clientP); 
			
            CommonKey = clientP.modPow(aInt,pInt); // calculation of Bob's private key 
  
            System.out.println("Common Key to perform Symmetric Encryption = "
                               + CommonKey); 
            server.close(); 
        } 
  
        catch (SocketTimeoutException s) { 
            System.out.println("Socket timed out!"); 
        } 
        catch (IOException e) { 
        } 
    }
		public static BigInteger createPrimeBigger(BigInteger numberBits)
		{
			int numbits = numberBits.intValue();
			Random random = new SecureRandom();
			BigInteger ret = BigInteger.probablePrime(numbits, random);
			return ret;
		}
		
		public static boolean returnPrime(BigInteger number) 
		{
		// Boolean variable to store the result 
        boolean result; 
  
        // Creates one BigInteger object 
        BigInteger a = number;
  
        // When certainty is one, 
        // it will check number for prime or composite 
        result = a.isProbablePrime(1); 
		return result;
		}
		
}
