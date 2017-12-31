

def iteloop(cnt, *args):
 l=len(args)
 if l==0: return
 
 lst=args 
 if l==1 and hasattr(lst, '__iter__'):
  lst=lst[0]
  l=len(lst)
 
 for i in range(cnt):
  yield lst[i%l]


def hexiter(cnt):
 for i in range(cnt):
  yield '%X' % (i%16)


def packiter(size, col):
 ary=[]
 for elm in col:
  ary+=[elm]
  if size<=len(ary):
   yield ary
   ary=[]
 l=len(ary)
 if l>0:
  if l<size:
   for i in range(size-l):
    ary+=[0]
  yield ary


