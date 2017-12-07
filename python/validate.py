import re


class ValidatorException(Exception):
	def __init__(self, msg):
		Exception.__init__(self, msg)


class Validate:
	def __init__(self, **kwargs):
		self.target = kwargs
		self.argsValidator = kwargs['_args'] if '_args' in kwargs else lambda x: True
		self.kwargsValidator = kwargs['_kwargs'] if '_kwargs' in kwargs else lambda x: True

	def __call__(self, func):
		def wrapper(*args, **kwargs):
			if not self.argsValidator(args):
				raise ValidatorException('Validation Error!! [args]')
			if not self.kwargsValidator(kwargs):
				raise ValidatorException('Validation Error!! [kwargs]')

			for key in self.target:
				if key[1:].isnumeric():
					self.validate_args(key, args)
				else:
					self.validate_kwargs(key, args)
			return func(*args, **kwargs)
		return wrapper

	def validate_args(self, key, _args):
		i = int(key[1:]) - 1
		if 0 <= i < len(_args) and not self.target[key](_args[i]):
			raise ValidatorException('Validation Error!! [args[{}]={}]'.format(i, _args[i]))

	def validate_kwargs(self, key, _kwargs):
		chunk = key[1:]
		if chunk in _kwargs and not self.target[key](_kwargs[chunk]):
			raise ValidatorException('Validation Error!! [{}={}]'.format(chunk, _kwargs[chunk]))


def is_numeric(var):
	return type(var) in [int, float, complex]


def is_collection(var):
	return hasattr(var, '__iter__')


def not_null(var):
	return var is not None


# ==============================================================================================================
# test
# ==============
if __name__ == '__main__':

	@Validate(
		_1=is_numeric,
		_2=is_collection,
		_args=not_null)
	def sample(x, y, *args):
		print('x={}'.format(x))
		print('y={}'.format(y))
		print('args={}'.format(args))

	print('* ' * 10)
	print('check 1')
	sample(10, (1, 2, 3), 'A', 'B', 'C')
	print('* ' * 10)

	try:
		print('* ' * 10)
		print('check 2')
		sample(10, (1, 2, 3))
		assert False
	except ValidatorException as e:
		print('catch : {}'.format(e))

	try:
		print('* ' * 10)
		print('check 3')
		sample(10, 'str', 'A', 'B', 'C')
		assert False
	except ValidatorException as e:
		print('catch : {}'.format(e))






