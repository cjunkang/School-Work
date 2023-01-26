import java.net.*;
import java.util.*;
import java.io.*; 
public class affine  
{ 
	static int mod;
    static int a; 
    static int b; 
	static int alphabet = 26; 
    static String encryptMessage(char[] msg)  
    { 
        /// Cipher Text initially empty 
        String cipher = "";
		String temp = "";
		int n = 0;
        for (int i = 0; i < msg.length; i++) 
        { 

            // Avoid space to be encrypted  
            /* applying encryption formula ( a x + b ) mod m 
            {here x is msg[i] and m is 26} and added 'A' to  
            bring it in range of ascii alphabet[ 65-90 | A-Z ] */ 
            if (msg[i] != ' ')  
            { 						
				int m = msg[i];
				 if(m>96&&m<123){
							n=m-97;
						}
						if(m>64&&m<91){
							
							n=m-65;
						}
						if(m>96&&m<123)
						{
							int lower = mod((a*n)+b,alphabet);
							//System.out.println("lower " + i + " " + lower);
							int combined = lower+97;
							msg[i]=(char)(combined);
							cipher = cipher + msg[i]; 
						}
						if(m>64&&m<91)
						{
							int upper = mod((a*n)+b,alphabet);
							//System.out.println("upper " + i + " " + upper);
							int combine = upper+65;
							msg[i]=(char)(combine);
							cipher = cipher + msg[i]; 
						}
                
            }
			else // else simply append space character 
            { 
                cipher += msg[i]; 
            } 
        } 
        return cipher; 
    } 
  
    static String decryptCipher(String cipher)  
    { 
        String msg = ""; 
        int a_inv = 0; 
        int flag = 0; 
		String plaintext = "";
        //Find a^-1 (the multiplicative inverse of a  
        //in the group of integers modulo m.)  
		for(int i=0;i<cipher.length();i++){
			int n = 0;
			char decrpt = 0;
			char q = cipher.charAt(i);
			int m = (int)q;
			//System.out.println("int m: " + m);
			if (cipher.charAt(i) != ' ')
			{
				 
				if(m>96&&m<123)
				{
					
					n=m-97;
				}
				if(m>64&&m<91)
				{			
					n=m-65;
				}		
				int x=0;
				int inv = 0;
					for(int j=0;j<26;j++)
					{
						x=(a*j)%alphabet;
						if(x==1)
						{
							inv=j;
						}
					}
				int cipherNum = mod((inv)*(n-b),alphabet);
			//System.out.println("int mod: " + mod);
				if(m>96 && m<123)
				{
					int combined = cipherNum+97;
					//System.out.println(combined);
					decrpt= (char)(combined);
					plaintext = plaintext + decrpt;
				}
				if(m>64 && m<91)
				{
					int combine = cipherNum+65;
					//System.out.println(combine);
					decrpt=(char)(combine);
					//System.out.println(decrpt);
					plaintext = plaintext + decrpt;
				}
				
			}
			else // else simply append space character 
            { 
                plaintext += cipher.charAt(i); 
            }
			        
    }
	return plaintext; 
	}
		static int mod(int a, int n){
			int mod=0;
			if(a>=0){			//To find mod for positive integer
				mod=a%n;
					}
			else{				//To find mod for negative integer
				mod=(n+(a%n))%n;
					}
			return mod;	
		}
			public static void SaveText(String text, String filename) throws IOException {

			FileOutputStream fos = new FileOutputStream(filename);
			byte[] saveText = text.getBytes();
			fos.write(saveText);
			fos.close();
		    }	
      public static void main(String[] args) throws Exception  
    { 
		Scanner sc = new Scanner(System.in);
		System.out.print("Welcome to affine Cipher");
		System.out.print("Input the first number,a : ");
        a = sc.nextInt();  
		System.out.print("Input the second number,b: ");
		b = sc.nextInt(); 
		
		readFile rf = new readFile();
		rf.openFile("file1.txt");
		String msg = rf.reading("file1.txt");
		
        // Calling encryption function 
        String cipherText = encryptMessage(msg.toCharArray()); 
        System.out.println("Encrypted Message is : " + cipherText);
		System.out.println("Saving cipherText as file2.txt");
		SaveText(cipherText,"file2.txt");
  
        // Calling Decryption function 
		rf.openFile("file2.txt");
		String plaintext = 	rf.reading("file2.txt");
        System.out.println("Decrypted Message is: " + decryptCipher(plaintext)); 
		System.out.println("Saving plaintext as file3.txt");
		SaveText(plaintext,"file3.txt");
    }

} 

class readFile{

			private Scanner sc;
			public void openFile(String text) throws Exception{
				sc = new Scanner(new File(text));
			}
			public String reading(String fileName) throws Exception{

				FileInputStream fis = new FileInputStream(fileName);
				byte[] buffer = new byte[10];
				StringBuilder sb = new StringBuilder();
				while (fis.read(buffer) != -1) {
					sb.append(new String(buffer));
					buffer = new byte[10];
				}
				fis.close();;

				String content = sb.toString();
				return content;
				
		}
	
}