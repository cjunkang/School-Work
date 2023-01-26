/*
 * The following is an implementation of the finite field GF(2^8) as bit vectors of length 8.
 * Note that the elements of GF(2^8) thus represent polynomials of degree < 8 in the generator x. Addition in this field is simply 
 * bitwise XOR, but multiplication requires the elimination of powers of x <= 8.
 */

#include <stdio.h>
#include <stdint.h>

typedef uint8_t gal8; /* Galois field of order 2^8 */

const gal8 irr_poly  = 0b11101,     /* Irreducible polynomial x^8 + x^4 + x^3 + x^2 + 1 */
           generator = 0b10;        /* Generator of Galois field (Binary) */

gal8 gal_add(gal8 a, gal8 b);       /* Add two elements of GF(2^8) */
gal8 gal_mul(gal8 a, gal8 b);       /* Multiply two elements of GF(2^8) */
void gal_print(gal8 a);             /* Print an element of GF(2^8) in binary form */

int main()
{
    int i = 0, c = 0;
    gal8 a = 0b10110101; //testing value 
    gal8 b = 0b10110101; //testing value
    a = gal_mul(a, b); //multiplication test
    gal_print(a);
    printf("\n");
}

gal8 gal_add(gal8 a, gal8 b)
{
    return a ^ b;
}

gal8 gal_mul(gal8 a, gal8 b)
{
    gal8 res = 0;
    for (; b; b >>= 1) {
        if (b & 1)
            res ^= a;
        if (a & 0x80)
            a = (a << 1) ^ irr_poly;
        else
            a <<= 1;
    }
    return res;
}

void gal_print(gal8 a)
{
    int i = 8;
    while (i--)
        putchar((a >> i & 1) + '0');
}


