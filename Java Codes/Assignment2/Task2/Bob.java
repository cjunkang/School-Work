//Name:Chong Jun Kang
//StudentID:6461542

import java.net.*; 
import java.io.*; 
import java.security.SecureRandom;
import java.util.Random;
import java.math.BigInteger;
import java.math.*;
 
  
public class Bob { 
    public static void main(String[] args) 
    { 
			BigInteger TWO = new BigInteger("2");
			BigInteger ONE = new BigInteger("1");
			BigInteger x;
        try { 
            String pstr, gstr, Astr; 
            String serverName = "localhost"; 
            int port = 8088; 
			
			
            // form g,h,private key 
            //int b = 3; 
			// public key g^bInt mod pInt
			
            // Declare p, g, and Key of client 
            BigInteger CommonKey,serverKey;
  
            // Established the connection 
            System.out.println("Connecting to " + serverName 
                               + " on port " + port); 
            Socket client = new Socket(serverName, port); 
            System.out.println("Just connected to "
                               + client.getRemoteSocketAddress()); 
					
            // Accepts the data p,q,g and server public key		   
			DataInputStream in = new DataInputStream(client.getInputStream()); 
  
			BigInteger pInt = new BigInteger(in.readUTF()); //recieve pInt
			BigInteger qInt = new BigInteger(in.readUTF()); // recieve qInt
			BigInteger gInt = new BigInteger(in.readUTF()); // recieve gInt
            serverKey = new BigInteger(in.readUTF()); // recieve Server Public Key
			
			
			//random bInt 1 to (q-1) to form private key
			BigInteger maxLimit;
			maxLimit = qInt.subtract(ONE);
			BigInteger minLimit = new BigInteger("1");
			BigInteger bigInteger = maxLimit.subtract(minLimit);
			Random randNum = new Random();
			int len = maxLimit.bitLength();
			BigInteger hInt = new BigInteger(len, randNum);
			  if (hInt.compareTo(minLimit) < 0)
				 hInt = hInt.add(minLimit);
			  if (hInt.compareTo(bigInteger) >= 0)
				 hInt = hInt.mod(bigInteger).add(minLimit);
			System.out.println("The random BigInteger h= " + hInt);
			BigInteger bInt = hInt;
			System.out.println("The private key b= " + bInt);
			
			//public key
			BigInteger PubKey = gInt.modPow(bInt,pInt);
			System.out.println("From Server, Public Key = "
                               + serverKey); 
            //System.out.println("From Server : Public Key = " + serverB); 
            // Sends the data to client 
            OutputStream outToServer = client.getOutputStream(); 
            DataOutputStream out = new DataOutputStream(outToServer); 
			
            String pubStr = PubKey.toString(); 
            out.writeUTF(pubStr); // Sending public key
			
            // calculate common key
  
            CommonKey = serverKey.modPow(bInt,pInt);
  
            System.out.println("Common Key to perform Symmetric Encryption = "
                               + CommonKey); 
            client.close(); 
        } 
        catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
}