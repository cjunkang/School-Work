public class tea
{

		private final static int DELTA = 0x9e3779b9;
		private final static int DECRYPT_SUM_INIT = 0xC6EF3720;
		private final static long MASK32 = (1L << 32) - 1;
		
		public static long encrypt(long in, int[] k, int bit) 
		{
		int v1 = (int) in;
		int v0 = (int) (in >>> 32);
		int sum = 0;
		for (int i = 0; i < 32; i++) {
		sum += DELTA;
		v0 += ((v1 << 4) + k[0]) ^ (v1 + sum) ^ ((v1 >>> 5) + k[1]);
		v1 += ((v0 << 4) + k[2]) ^ (v0 + sum) ^ ((v0 >>> 5) + k[3]);
		}
		return (v0 & MASK32) << 32 | (v1 & MASK32);
		}
		public static long decrypt(long in, int [] k,int bit) 
		{
		int v1 = (int) in;
		int v0 = (int) (in >>> 32);
		int sum = DECRYPT_SUM_INIT;
		for (int i=0; i<32; i++) {
		v1 -= ((v0<<4) + k[2]) ^ (v0 + sum) ^ ((v0>>>5) + k[3]);
		v0 -= ((v1<<4) + k[0]) ^ (v1 + sum) ^ ((v1>>>5) + k[1]);
		sum -= DELTA;
		}
		return (v0 & MASK32) << 32 | (v1 & MASK32);
		}
		
	public static void main(String[] args)
	{
		long studentid = 6461542; // 4-bit after modulus
		int bit = ((6+4+6+1+5+4+2)%8);
		int[] key = new int[20];
		long encrypted = encrypt(studentid,key,bit);
		System.out.println(encrypted);
		long decrypted = decrypt(encrypted,key,bit);
		System.out.println(decrypted);
		
		
	}
}