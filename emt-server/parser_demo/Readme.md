Jak używać?

umieść pliki .pdf w katalogu pdfs

uruchum python3 pdf_to_json.py ../pdfs/ ../json_results/

-> pliki .pdf zostały sparsowane i zapisane w katalogu json_results

->uruchom: python3 json_to_csv.py ../csv_results/result.csv json_fields_specs.json ../json_results/ get_rank

- pierwszy argument -> plik csv do którego zapisać ranking
- json_fields_specs -> pola które zawrzeć w pliku koncowym
- json results -> katalog w którym zapisano pliki w formacie .pdf
- get_rank - dodaj pole zawierajace wynik każdego uczestnika

aby wygenerowac koncową ankiete do dwzu -> uruchom skrypy dwz_export.py

python3 dwz_export.py ../csv_results/result.csv ../forms/form.xlsx ../forms/out_form.xlsx
