import re

def readline(ptn, label='type word'):
 while True:
  line=input(label+'> ').strip()
  if len(line)==0 or re.search(ptn, line):
   return line
  print('illegal value[{}]'.format(line))
