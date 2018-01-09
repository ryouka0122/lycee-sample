from functools import reduce
from iters import packiter


class Base64EncodeTokenizer:
 def __init__(self, token):
  self.tok=token
 
 def __iter__(self):
  tk=self.tok
  yield ((tk[0]>>2)&0x3f)
  yield ((tk[0]&0x03)<<4|(tk[1]>>4)&0x0f)
  if tk[1]==0:
   yield -1
  else:
   yield ((tk[1]&0x0f)<<2|(tk[2]>>6)&0x03)
  if tk[2]==0:
   yield -1
  else:
   yield (tk[2]&0x3f)
  return
  

class Base64DecodeTokenizer:
 def __init__(self, token):
  self.tok=token

 def __iter__(self):
  yield ((self.tok[0]&0x3f)<<2)|((self.tok[1]>>4)&0x03)
  yield ((self.tok[1]&0x0f)<<4)|((self.tok[2]>>2)&0x0f)
  yield ((self.tok[2]&0x03)<<6)|(self.tok[3]&0x3f)


class Base64:
 base64elms='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'
 
 def __init__(self):
  pass
 
 def __repr__(self):
  return 'base64'

 __str__ = __repr__
 
 def encode(self, text):
  s=''
  for tok in packiter(3,map(ord,text)):
   s+=self.to_base64(tok)
  return sfrom readlines import readline as rl

class Base64Tokenizer:

 @staticmethod
 def encode1(tk, idx):
  return ((tk[idx]>>2)&0x3f)
 
 @staticmethod
 def encode2(tk, idx):
  return ((tk[idx]&0x03)<<4|(tk[idx+1]>>4)&0x0f)
  
 @staticmethod
 def encode3(tk, idx):
  if tk[idx+1]==0:
   return -1
  else:
   return ((tk[idx+1]&0x0f)<<2|(tk[idx+2]>>6)&0x03)
  
 @staticmethod
 def encode4(tk, idx):
  if tk[idx+2]==0:
   return -1
  else:
   return (tk[idx+2]&0x3f)
 
 @staticmethod
 def decode1(tk, idx):
  return ((tk[idx]&0x3f)<<2)|((tk[idx+1]>>4)&0x03)
  
 @staticmethod
 def decode2(tk, idx):
  return ((tk[idx+1]&0x0f)<<4)|((tk[idx+2]>>2)&0x0f)

 @staticmethod
 def decode3(tk, idx):
  return ((tk[idx+2]&0x03)<<6)|(tk[idx+3]&0x3f)
   
 base64elms='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'

 def __init__(self):
  self.encoder=[
   Base64Tokenizer.encode1
   ,Base64Tokenizer.encode2
   ,Base64Tokenizer.encode3
   ,Base64Tokenizer.encode4
  ]
  self.decoder=[
   Base64Tokenizer.decode1
   ,Base64Tokenizer.decode2
   ,Base64Tokenizer.decode3
  ]
 
 def __repr__(self):
  return 'base64 tokenizer'
  
 __str__=__repr__

 def encode(self, tok):
  l=len(tok)
  tok=list(map(ord,tok))+[0,0,0]
  for i in range(0, l, 3):
   for j in range(4):
    cd=self.encodeToken(tok, i, j)
    yield self.base64elms[cd] if cd>=0 else '='
 
 def encodeToken(self, tk, i, j):
  return self.encoder[j](tk, i)
  
 def decode(self, tok):
  l=len(tok)
  tok=list(map(self.conv, tok))
  tok+=[0,0,0]
  for j in range(0, l, 4):
   for i in range(3):
    cd=self.decodeToken(tok, j, i)
    yield chr(cd) if cd!=0 else ''

 def conv(self, ch):
  if '='==ch:
   return 0
  i=self.base64elms.find(ch)
  if i==-1:
   raise Exception()
  return i
  
 def decodeToken(self, tk, j, i):
  return self.decoder[i](tk, j)


if __name__=='__main__':
 tokenizer=Base64Tokenizer()

 def test(text):
  enc=''
  for t in tokenizer.encode(text):
   enc+=t

  dec=''
  for s in tokenizer.decode(enc):
   dec+=s

  print('* '*13)
  print('input:{}'.format(text))
  print('encode:{}'.format(enc))
  print('decode:{}'.format(dec))
  print('recovery:{}'.format(text==dec))
  print()

 while True:
  line=rl(r'^.+$')
  if len(line)==0:
   break
  test(line)

 
 def decode(self, text):
  s=''
  for tok in packiter(4,text):
   s+=self.to_chr(tok)
  return s
 
 def to_base64(self, tok):
  tokenizer=Base64EncodeTokenizer(tok)
  s=''
  for cd in tokenizer:
   s+=self.base64elms[cd] if cd>=0 else '='
  return s
  
 def to_chr(self, tok):
  tk=tuple(map(self.find, tok))
  tokenizer=Base64DecodeTokenizer(tk)
  s=''
  for cd in tokenizer:
   s+=chr(cd) if cd!=0 else ''
  return s

 def find(self, ch):
  i=0
  for c in self.base64elms:
   if c==ch:
    return i
   i+=1
  return 0


sample='{"typ":"JWT",\r\n "alg":"HS256"}'
sample1='{"iss":"joe",\r\n "exp":1300819380,\r\n "http://example.com/is_root":true}'
sample2=''
 
def execute(cipher, text):
 def output(label, arg):
  print('- {} {}\n{}\n'.format(
   label, '-'*15, arg))
 
 print('*'+'=*'*20)
 output('cipher', cipher)
 output('text', text)
 print()
 
 enc=cipher.encode(text)
 dec=cipher.decode(enc)
 
 print('*'+'-*'*20)
 print('{}.encode\n'.format(cipher))
 output('input', text)
 output('output', enc)
 output('output length', len(enc))
 print()
 print('*'+'-*'*20)
 print('{}.decode\n'.format(cipher))
 output('input', enc)
 output('output', dec)
 print()
 print('*'+'-*'*20)
 
 if text==dec:
  print('- check:True')
  return
 
 if len(text)!=len(dec):
  print('- diff length({} vs {})'.format(
   len(text),len(dec)))
  return
  
 for i in range(len(text)):
  c1=text[i]
  c2=dec[i]
  if c1!=c2:
   print('- diff pt:{}[{}({}) vs {}({})]'.format(
    i, c1,ord(c1), c2,ord(c2)))
   break
 print()
 return


execute(Base64(), sample)

print('[]'+'-[]'*10)
execute(Base64(), sample1)

