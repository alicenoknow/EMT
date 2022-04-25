import PyPDF2 as pypdf
import json
import os
import csv


def convert_pdf_from_file_to_dict(fname):
	pdfobject = open(fname,'rb')
	pdf = pypdf.PdfFileReader(pdfobject)
	res = pdf.getFormTextFields()
	return res

def save_jsoned_pdf_to_file(pdf_dict, fname):
	with open(fname, 'w') as f:
		pdf_dict['being_late'] = '0'
		pdf_dict['activity'] = 10
		json.dump(pdf_dict, f)

def get_faculty_points(faculty):
	if faculty == 'WIET':
		return 10
	return 0

def get_extra_activity_points(activity):
	return int(activity)

def get_gpa(gpa):
	return float(gpa)

def get_late_points(late):
	if late!= 'late':
		return 10
	return 0

def get_cert_points(cert_type):
	if cert_type == 'C2':
		return 40
	if cert_type == 'C1':
		return 35
	if cert_type == 'B2':
		return 30
	return 0

def generate_rank(json_directory, rank_file_name):
	users = []
	for filename in os.listdir(json_directory):
		f = os.path.join(json_directory, filename)
		# checking if it is a file
		if os.path.isfile(f) is False:
			return

		with open(f, 'r') as f:
			user = json.load(f)
			# print(user)
			users.append(user)
	for user in users:
		# print(8*get_gpa(user['GPA']))
		# print(get_cert_points(user['certificate_type']))
		user['rank_score'] = 8*get_gpa(user['GPA']) + get_cert_points(user['certificate_type'])+ get_faculty_points(user['faculty'])+ get_extra_activity_points(user['activity'])- get_late_points(user['being_late'])
	users.sort(key = lambda user: user['rank_score'], reverse = True)
	cols = users[0].keys()
	with open(rank_file_name, 'w') as csvfile:
		writer = csv.DictWriter(csvfile, fieldnames = cols)
		writer.writeheader()
		writer.writerows(users)

def convert_pdf_directory(pdf_path, result_path):
	for filename in os.listdir(pdf_path):
		f = os.path.join(pdf_path, filename)
			# checking if it is a file
		save_jsoned_pdf_to_file(convert_pdf_from_file_to_dict(f), result_path+filename+'_form.json')

result_path = './results/'
pdf_path = './pdfs/'
json_directory = result_path

convert_pdf_directory(pdf_path, result_path)
generate_rank(json_directory, 'result.csv')