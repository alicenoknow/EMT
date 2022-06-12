import pandas as pd
import openpyxl
from openpyxl.utils.dataframe import dataframe_to_rows
import sys


# ATTENTION PLEASE
# delimiter is hardcoded everywhere as ;, please use 
# it to enable showing csv file in office 365

csv_file_path = sys.argv[1] # ../csv_results/result.csv
input_excel_path = sys.argv[2] # ../forms/form.xlsx
output_excel_path = sys.argv[3]  #../forms/out_form.xlsx
df = pd.read_csv(csv_file_path, delimiter = ';',encoding='latin-1')
members_num = df.shape[0]
wb_obj = openpyxl.load_workbook(input_excel_path)
 
sheet_obj = wb_obj.active
for i,row in enumerate(dataframe_to_rows(df, index=False, header=False)):
	sheet_obj.insert_rows(11+i)
	for j,val in enumerate(row):
		sheet_obj.cell(row = 11+i, column = j+2).value = val
	

wb_obj.save(output_excel_path)
