//Name:Chong Jun Kang

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.net.*;
import java.util.*;
import java.io.*; 

public class subCipher {

		char[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k','l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		char[] keyalpha =new char[alpha.length];
					public static void main(String[] args) throws Exception 
					{
						String message;
						subCipher subCipher = new subCipher();
						Scanner sc = new Scanner(System.in);
						System.out.println("Enter keyword");
						String input = sc.nextLine();
						readFile rf = new readFile();
						char str[] = input.toCharArray();
						int n = str.length; 
						String remove = removeDuplicate(str, n);
						System.out.println("Here is the keyword without duplicates: " + remove);
						subCipher.word(remove);
						System.out.println("Enter your choice:");
						System.out.println("1:Input plaintext, get ciphertext");
						System.out.println("2:Input ciphertext, get plaintext");
						System.out.println("Input any other text to quit");
						int expression = Integer.parseInt(sc.nextLine());
						switch(expression) {
						  case 1:
						File filePlainText = new File("plaintext.txt");
						FileInputStream pis = new FileInputStream("plaintext.txt");
						byte[] plainbyte = new byte[(int) filePlainText.length()];
						pis.read(plainbyte);
						pis.close();
						String plaintext = new String(plainbyte);
						String cipherT = subCipher.enc(plaintext);
						System.out.println(cipherT);
						SaveCipher(cipherT);
							break;
						  case 2:
						File fileCiperText = new File("ciphertext.txt");
						FileInputStream cis = new FileInputStream("ciphertext.txt");
						byte[] cipherbyte = new byte[(int) fileCiperText.length()];
						cis.read(cipherbyte);
						cis.close();
						String ciphertext = new String(cipherbyte);
						String plainT =  subCipher.dec(ciphertext);
						System.out.println(plainT);
						SavePlain(plainT);
							break;
						  default:
						break;
						}
					}
					public void word(String keyword)
					{
			   char[] keychar=keyword.toCharArray();
			   int count=0;
				for (int i = 0; i < keychar.length; i++) {
					keyalpha[count++]=keychar[i];
				}
				for (int i = 0; i < alpha.length; i++) {
					boolean x=false;
					for (int j = 0; j < keychar.length; j++) {
						if (alpha[i] == keyalpha[j]) {
							x=true;
						}   }
					if (x == false) {
						keyalpha[count++]=alpha[i];
													}
													}
				
					}

					public String enc(String Plain){
						String saveText = "";
					   char[] charPlain=Plain.toCharArray();
					   char[] temptext = new char[Plain.length()];
					   int a=0;
						for (int i = 0; i < charPlain.length; i++) {
							for (int j = 0; j < alpha.length; j++) {
								if (charPlain[i] == alpha[j]) 
								{
							temptext[a] = keyalpha[j];
									a++;
							}
						}
						}
							saveText = new String(temptext);
									return saveText;
					}

					public String dec(String cipher){
						String saveText = "";
					   char[] charCipher = cipher.toCharArray();
					   char[] tempcipher = new char[cipher.length()];
					   int a=0;
						for (int i = 0; i < charCipher.length; i++) {
							for (int j = 0; j < alpha.length; j++) {
								if (charCipher[i] == keyalpha[j]) 
								{				
							tempcipher[a] = alpha[j];
									a++;
								}
							}
						}
						saveText = new String(tempcipher);
								return saveText;
					}
				public static void SaveCipher(String Cipher) throws Exception
				{
				try
					{
					FileOutputStream fos = new FileOutputStream("ciphertext.txt");
					String ctext = Cipher;
					byte[] cbytes = ctext.getBytes();
					fos.write(cbytes);
					fos.close();
					}
					catch (Exception ex)  
					{
						System.out.println("Error.");
					}
				}
				public static void SavePlain(String Plain) throws Exception{
					try
					{
					FileOutputStream os = new FileOutputStream("plaintext.txt");
					String ptext = Plain;
					byte[] pbytes = ptext.getBytes();
					os.write(pbytes);
					os.close();
					}
					catch (Exception ex)  
					{
						System.out.println("Error.");
					}
				}
				

			static String removeDuplicate(char str[], int n) 
				{ 
				// Used as index in the modified string 
				int index = 0; 

				// Traverse through all characters 
				for (int i = 0; i < n; i++) 
				{ 

					// Check if str[i] is present before it 
					int j; 
					for (j = 0; j < i; j++) 
					{ 
						if (str[i] == str[j]) 
						{ 
							break; 
						} 
					} 

					// If not present, then add it to 
					// result. 
					if (j == i) 
					{ 
						str[index++] = str[i]; 
					} 
				} 
				return String.valueOf(Arrays.copyOf(str, index)); 
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