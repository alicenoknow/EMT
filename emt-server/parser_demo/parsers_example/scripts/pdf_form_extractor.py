import PyPDF2 as pypdf
import json
import os
import csv
import sys





def get_faculty_points(faculty):
	"""get ranking points based on student's faculty

	Args:
		faculty (string): AGH faculty

	Returns:
		int: points for faculty
	"""
	if faculty == 'WIET':
		return 10
	return 0

def get_extra_activity_points(activity):
	"""extra activity points, which are based on 
		coordinator will

	Args:
		activity (string): activity points 

	Returns:
		int: ranking points for activity
	"""
	return int(activity)

def get_gpa(gpa):
	"""parses gpa string to int

	Args:
		gpa (string): gpa

	Returns:
		int: gpa string
	"""
	return float(gpa)

def get_late_points(late):
	"""negative points for being late

	Args:
		late (string): points for being lat

	Returns:
		int: points as int
	"""
	if late!= 'late':
		return 10
	return 0

def get_cert_points(cert_type):
	"""points for certification level

	Args:
		cert_type (string): certification level (A1, A2, ...)

	Returns:
		int: ranking points for certification type
	"""
	if cert_type == 'C2':
		return 40
	if cert_type == 'C1':
		return 35
	if cert_type == 'B2':
		return 30
	return 0

def generate_rank(json_directory, rank_file_name):
	"""generate .csv file containing users ranking

	Args:
		json_directory (string): path where .json file of each user 
								are stored


		rank_file_name (string): .csv file name
	"""
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
		user['rank_score'] = 8*get_gpa(user['GPA']) + get_cert_points(user['exam_level'])+ get_faculty_points(user['faculty'])+ get_extra_activity_points(user['activity'])- get_late_points(user['being_late'])
	users.sort(key = lambda user: user['rank_score'], reverse = True)
	cols = users[0].keys()
	with open(rank_file_name, 'w') as csvfile:
		writer = csv.DictWriter(csvfile, fieldnames = cols)
		writer.writeheader()
		writer.writerows(users)



# HOW TO USE?
# python3 pdf_form_extractor.py path_to_save_csv path_to_load_pdfs path_to_save_jsons

# place where to save each of created json
result_path = sys.argv[4]
# path where to load each pdf from
pdf_path = sys.argv[3]
csv_path = sys.argv[2]

# execution flow

generate_rank(result_path, csv_path)