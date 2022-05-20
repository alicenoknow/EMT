import PyPDF2 as pypdf
import json
import os
import csv
import sys

def convert_pdf_from_file_to_dict(fname):
	"""reads pdf file and returns dictionary
		where key is field label and value is field value
	Args:
		fname (string): pdf file name 

	Returns:
		dict: dictionary as specified in description
	"""
	pdfobject = open(fname,'rb')
	pdf = pypdf.PdfFileReader(pdfobject)
	res = pdf.getFields()
	pdf_format_value_marker = '/V'
	result = {}
	for key in res.keys():
		val = res[key][pdf_format_value_marker]
		result[key] = val
	print(result)
	return result


def save_jsoned_pdf_to_file(pdf_dict, fname):
	"""save dictionary from pdf to json file

	Args:
		pdf_dict (dict): dictionary with parsed pdf
		fname (_type_): json name to save to
	"""
	with open(fname, 'w') as f:
		pdf_dict['being_late'] = '0'
		pdf_dict['activity'] = 10
		json.dump(pdf_dict, f)

def convert_pdf_directory(pdf_path, result_path):
	"""generate jsons from pdfs

	Args:
		pdf_path (string): path to directory containing pdfs
		result_path (string): path where to save jsons to 
	"""
	for filename in os.listdir(pdf_path):
		f = os.path.join(pdf_path, filename)
		print(f)
			# checking if it is a file
		json_name = result_path+filename+'_form.json'
		save_jsoned_pdf_to_file(convert_pdf_from_file_to_dict(f), json_name)


# save pdf to json
print(sys.argv)
result_path = sys.argv[2]
# path where to load each pdf from+
pdf_path = sys.argv[1]
print(result_path, pdf_path)
# python3 pdf_to_json.py ../pdfs/ ./

convert_pdf_directory(pdf_path, result_path)