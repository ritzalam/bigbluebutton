import subprocess
from subprocess import Popen,PIPE,STDOUT,TimeoutExpired
import os
import json
import re

def pdf2swf(pdf_in, page, swf_out):
	out = subprocess.Popen(["timeout", "5m", "/usr/bin/pdf2swf", '-vv', '-T9', '-F', '/usr/share/fonts',
				'-p', str(page), pdf_in, '-o',
				swf_out], stderr=STDOUT, stdout=PIPE)
	result = {}
	outStr = out.communicate()[0].decode("utf-8")
	result["stdout"] = outStr
	#print(outStr)
	result["returncode"] = out.returncode
	return json.dumps(result, ensure_ascii=False)
	#result = out.communicate()[0],out.returncode
	#return result

def convertTimeout(pdf_in, page, swf_out):
	proc = subprocess.Popen(["/usr/bin/pdf2swf", '-vv', '-T9', '-F', '/usr/share/fonts',
				'-p', str(page), pdf_in, '-o',
				swf_out], stderr=STDOUT, stdout=PIPE)
	try:
		outs, errs = proc.communicate(timeout=5)
	except TimeoutExpired as err:
		proc.kill()
		print(err.cmd)
		print("returncode=", proc.returncode)
		outs, errs = proc.communicate()
		    
		#print("PRINTINT OUTPUT+++++++++++++==================\n")
		print(outs.decode("utf-8"))
		print("PRINT ERROR +++++++++++++==================\n")
		print(errs)

def pdf2png(pdf_in, page, png_out):
	proc = subprocess.Popen(["/usr/bin/pdf2swf", '-vv', '-T9', '-F', '/usr/share/fonts',
				'-p', str(page), pdf_in, '-o',
				swf_out], stderr=STDOUT, stdout=PIPE)
	try:
		outs, errs = proc.communicate(timeout=5)
	except TimeoutExpired as err:
		proc.kill()
		print(err.cmd)
		    
		outs, errs = proc.communicate()
		print("returncode=",  str(proc.returncode))
		#print("PRINTINT OUTPUT+++++++++++++==================\n")
		#print(outs.decode("utf-8"))
		#print("PRINT ERROR +++++++++++++==================\n")
		#print(errs.decode("utf-8"))

def count_number_of_objects_in_swf(text):
	shapeCount = 0
	fontCount = 0
	drawCount = 0

	lines = text.splitlines()
	for line in lines:
		if re.search('shape id', line):
			shapeCount += 1
		elif re.search('Updating font', line):
			fontCount += 1
		elif re.search('Drawing', line):
			drawCount += 1

	return shapeCount, fontCount, drawCount
