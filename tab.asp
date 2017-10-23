
def find_primes():
   i1 = 2
   while i1 <= n:
      i2 = 2 * i1
      while i2 <= n:
         primes[i2] = False
         i2 = i2 + i1
      i1 = i1+1
