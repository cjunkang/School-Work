import java.util.Scanner;
import java.net.*; 
import java.util.*; 
import java.io.*; 
import java.nio.file.Files;
import java.nio.file.Paths;
public class Caesar {
 char [] input;
 char [] output;
 int enkey;
 int dekey;
 int bfkey;
 int noOfLines = 10000;
     void encrypt() throws Exception
 {
     Scanner sc = new Scanner(System.in);
     System.out.print("Enter the filename to encrypt, no need file format: ");
     String filename = sc.nextLine() + ".txt";
	File fileCipherText = new File(filename);
	FileInputStream fis = new FileInputStream(filename);
	byte[] filebyte = new byte[(int) fileCipherText.length()];
	fis.read(filebyte);
	fis.close();
	String filetext = new String(filebyte);
     input = filetext.toCharArray();
     System.out.print("Enter a key : ");
     enkey = sc.nextInt();
	 if(enkey>26)
	 {
	int factor = enkey/26;
	enkey = enkey-(26*factor);
	 }
     for(int i=0;i<input.length;i++)
     {
         if(input[i] == ' ')
		 {
             continue;
		 }
         else
         {
             if(input[i] >='A' && input[i] <='Z')
             {
                 input[i] = (char)(input[i] + enkey);
                 if(input[i] > 'Z')
                 {
                     input[i] = (char)(input[i] - 26);
                 }
             }
             else if (input[i] >='a' && input[i] <='z')
             {
                 input[i] = (char) (input[i] + enkey);
                 if(input[i] > 'z')
                 {
                     input[i] = (char)(input[i] - 26);
                 }
             }
         }
     }     
	 Scanner de = new Scanner(System.in);
	 String encrypted = String.valueOf(input);
	System.out.print("Enter the filename to save encryption as: ");
	 String fileput = de.nextLine() + ".txt";
	 createFile(fileput);
       writeUsingFileWriter(encrypted,fileput);
     System.out.println("Encrypted String : " + String.valueOf(input) + "\n");
 }
     void decrypt() throws Exception
 {
     Scanner sc = new Scanner(System.in);
     System.out.print("Enter the filename to decrypt, no need file format : ");
     String filename = sc.nextLine() + ".txt";
	File filePlainText = new File(filename);
	FileInputStream fis = new FileInputStream(filename);
	byte[] filebyte = new byte[(int) filePlainText.length()];
	fis.read(filebyte);
	fis.close();
	String filetext = new String(filebyte);
     input = filetext.toCharArray();
     System.out.print("Enter the key : ");
     dekey = sc.nextInt();
	 if(dekey>26)
	 {
	int factor = dekey/26;
	dekey = dekey-(26*factor);	 
		}
     for(int i=0;i<input.length;i++)
     {
         if(input[i] == ' ')
             continue;
         else
         {
             if(input[i] >='A' && input[i] <='Z')
             {
                 input[i] = (char) (input[i] - dekey);
                 if(input[i] < 'A')
                 {
                     input[i] = (char) (input[i] + 26);
                 }
             }
             else if (input[i] >='a' && input[i] <='z')
             {
                 input[i] = (char) (input[i] - dekey);
                 if(input[i] < 'a')
                 {
                     input[i] = (char) (input[i] + 26);
                 }
             }
         }
     }
	 Scanner de = new Scanner(System.in);
	 String decrypted = String.valueOf(input);
	System.out.print("Enter the filename to save decryption as: ");
	 String fileput = de.nextLine() + ".txt";
	 createFile(fileput);
       writeUsingFileWriter(decrypted,fileput);
     System.out.println("Decrypted String : " + String.valueOf(input) + "\n");
 }
			 void bruteforce() throws Exception
											{
			 Scanner sc = new Scanner(System.in);
			 System.out.print("Enter the filename, no need file format : ");
			 String bf = sc.nextLine() + ".txt";
			File filePlainText = new File(bf);
			FileInputStream fis = new FileInputStream(bf);
			byte[] filebyte = new byte[(int) filePlainText.length()];
			fis.read(filebyte);
			fis.close();
			String filetext = new String(filebyte);
			 input = filetext.toCharArray();
			 output = filetext.toCharArray();
			 int bfkey;
			String decryptMessage = "";
			 for(int shift=0;shift<26;shift++)
				
		{
				for(int i=0; i < filetext.length();i++)  
				{
					char alphabet = filetext.charAt(i);
					if(alphabet >= 'a' && alphabet <= 'z')
					{
						// shift alphabet
						alphabet = (char) (alphabet - shift);
					
						// shift alphabet lesser than 'a'
						if(alphabet < 'a') {
							//reshift to starting position 
							alphabet = (char) (alphabet-'a'+'z'+1);
						}
						decryptMessage = decryptMessage + alphabet;
					}    
						// if alphabet lies between A and Z
					else if(alphabet >= 'A' && alphabet <= 'Z')
					{
					 // shift alphabet
						alphabet = (char) (alphabet - shift);
						
						//shift alphabet lesser than 'A'
						if (alphabet < 'A') {
							// reshift to starting position 
							alphabet = (char) (alphabet-'A'+'Z'+1);
						}
						decryptMessage = decryptMessage + alphabet;            
					}
					else 
					{
					 decryptMessage = decryptMessage + alphabet;            
					}
				}
			IC ic = new IC();
			double mic = ic.calculate(decryptMessage);
			//System.out.println("Key: " + shift + "Decrypted message: " + decryptMessage + " MIC value: " + mic);
			if (mic >0.06 && mic < 0.07)
			{
			System.out.println("Key: " + shift + " | Decrypted message: " + decryptMessage + " | MIC value: " + mic);
			}
				decryptMessage = "";
			}
		 }

			private static void writeUsingFileWriter(String data,String filename) throws Exception {
				File file = new File(filename);
				FileWriter fr = null;
				try {
					fr = new FileWriter(file);
					fr.write(data);
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					//close resources
					try {
						fr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			private static void createFile(String filename) throws Exception
			{
				try {
				  File myObj = new File(filename);
				  if (myObj.createNewFile()) 
				  {
				  } 
				  else {
				  }
				} catch (IOException e) {
				  System.out.println("An error occurred.");
				  e.printStackTrace();
				}
			  }
    
 public static void main(String[] args) throws Exception {
     Scanner sc = new Scanner(System.in);
     int c;
     do
     {
         System.out.println("1:Encryption\n2:Decryption\n3:Bruteforce\n4:Exit");
         c = sc.nextInt();
         switch(c)
         {
             case 1 : new Caesar().encrypt(); break;
             case 2 : new Caesar().decrypt(); break;
             case 3 : new Caesar().bruteforce();  break;
             case 4 : break;
         }
     }while(c!=4);
 }
}

class IC {
    
    public IC(){
    	
    }
    
    public double calculate(String s){
    	
    	int i;
    	int N = 0;
    	double sum = 0.0;
    	double total = 0.0;
    	s = s.toUpperCase();
    	
    	//initialize array of values to count probability of each letter
    	double[] values = new double[26];
    	values[0] = 0.082;
		values[1] = 0.015;
		values[2] = 0.028;
		values[3] = 0.043;
		values[4] = 0.127;
		values[5] = 0.022;
		values[6] = 0.020;
		values[7] = 0.061;
		values[8] = 0.070;
		values[9] = 0.002;
		values[10] = 0.008;
		values[11] = 0.040;
		values[12] = 0.024;
		values[13] = 0.067;
		values[14] = 0.075;
		values[15] = 0.019;
		values[16] = 0.001;
		values[17] = 0.060;
		values[18] = 0.063;
		values[19] = 0.091;
		values[20] = 0.028;
		values[21] = 0.010;
		values[22] = 0.023;
		values[23] = 0.001;
		values[24] = 0.020;
		values[25] = 0.001;
		
		
    	
    	//calculate frequency of each letter in s
    	int ch;
    	for(i=0; i<s.length(); i++)
		{
    		ch = s.charAt(i)-65;
    		if(ch>=0 && ch<26){
				sum += values[ch];
    			N++;
    			}	
    	}
      	
    	//divide by N(N-1)	
    	total = sum/N;
    	
    	//return the result
    	return total;
    	
    }
}