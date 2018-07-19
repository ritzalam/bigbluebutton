import docconv
import unittest

class DocConvTest(unittest.TestCase):

	def test_pdf2swf(self):
		'''pdf2swf should convert a page of pdf to swf'''
		docconv.pdf2swf("testdata/flight-school.pdf", 1, "testout/page-1.swf")

	def test_convert_timeout(self):
		'''pdf2swf times out converting page'''
		docconv.convertTimeout("testdata/sample-long-convert.pdf", 1, "testout/page-2.swf")

if __name__ == '__main__':
	unittest.main()
