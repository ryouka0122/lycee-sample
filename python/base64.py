from readlines import readline as rl

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
