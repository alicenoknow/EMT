import React from "react";
import ReactMarkdown from "react-markdown";
import { Container } from "react-bootstrap";
import "./AdminHelp.scss";

const markdownSteps = `
					\n ### 1. Rejestracja
                    \n* W celu założenia konta należy przejść do zakładki __Rekrutacja__, 
                    wprowadzić mail w domenie agh.edu.pl oraz hasło, a następnie zatwierdzić przyciskiem __Zarejestruj__. 
                    Na górze strony pojawi się komunikat o poprawnym zarejestrowaniu użytkownika
                     oraz informacja o przesłaniu potwierdzenia na mail.
                     \n* W następnym kroku należy zalogować się na podany wcześniej adres email, 
                     potwierdzić własność emailu i aktywować konto. 
                     Po kliknięciu na przesłany w mailu link powinien pojawić się komunikat o poprawnym potwierdzeniu emaila.
					 \n* Dodatkowo należy skontaktować się z osobą utrzymującą aplikację, w celu nadania uprawnień administratora.

					\n ### 2. Logowanie
					\n* Aby zalogować się na konto, należy podać email i hasło podane w procesie rejestracji. 
					Następnie zatwierdzić dane przyciskiem __Zaloguj__. 
					\n* Jeśli podano poprawne dane, użytkownik zostanie przekierowany do swojego profilu.
					\n* W przypadku niepoprawnego logowania na górze straony, wyświetli się adekwatny komunikat.
					
					\n ### 3. Podgląd ankiet
					\n* W celu uzyskania podglądu listy złożonych ankiet, w pierwszej kolejności należy zalogować 
					się na konto z uprawnieniami administratora (patrz punkt 2). 
					Po zalogowaniu użytkownik zostanie przeniesiony do głównego widoku administracyjnego, 
					gdzie z bocznego menu po lewej stronie użytkownik może wybrać zakładkę __LISTA ANKIET__, która otworzy
					widok wszystkich ankiet rekrutacyjnych złożonych przez studentów.   
					\n* Lista ta umożliwia podgląd wszystkich wypełnionych ankiet znajdujących się na aplikacyjnym OneDrive’ie. 
					Kliknięcie w link znajdujący się po prawej wiersza interesującej nas ankiety,
						spowoduje otwarcie nowej zakładki w przeglądarce, która wyświetli podgląd wypełnionego dokumentu.
					\n* Podgląd umożliwia sortowanie wyników po dowolnej z widocznych kolumn oraz przeszukiwanie listy korzystając z
						pola tekstowego __Wyszukaj__ znajdującego się bezpośrednio nad listą.
					
					\n ### 4. Generowanie tymczasowego rankingu
					\n* W kazdym momencie __trwania procesu rekrutaW każdym momencie rekrutacji, 
					niezależnie od ustawionych dat rozpoczęcia i zakończenia administrator 
					może wygenerować ranking aplikantów, w celu dalszej weryfikacji, 
					bądź finalnego rozdzielenia studentów na miejsca wynikające ze 
					studento-godzin zawartych w umowach z uczelniami zagranicznymi.
					\n* Ranking ten jednocześnie służy jako plik potwierdzający listę zakwalifikowanych 
					studentów do celów wygenerowania oficjalnej listy przekazywanej do DWZ.
					\n* Aby wygenerować wyżej opisaną listę, w pierwszej kolejności należy przejść do zakładki __WYNIKI__ w panelu administratora.
					\n* Następnie należy kliknąć w link „Pobierz plik (.xlsx)” uruchomi on generację pliku rankingowego, a 
					po jej zakończeniu przeniesie do podglądu pliku na platformie OneDrive.
					\n* Widok ten ze względu na automatyczne formatowanie wersji online programu Microsoft Excel 
					nie zawsze może wyświetlać się poprawnie. W celu uzyskania najlepszej jakości doświadczeń płynącej 
					z użytkowania naszego systemu zalecamy przejście do widoku plików na dysku OneDrive poprzez kliknięcie 
					w nazwę pliku podglądu, a następnie nazwy folderu „results”, w którym owy plik się znajduje. 
					\n* W efekcie powinno otworzyć się okno, pozwalające pobrać świeżo wygenerowany ranking w poprawnym formacie .csv. 

					\n ### 5. Edytowanie listy rankingowej
					\n* Plik wygenerowany przez nasz system jest plikiem przeznaczonym do edycji. 
					Może być on w swobodny sposób przekształcany w arkuszu excel. Niemniej jednak, 
					ostatecznie plik ten ma posłużyć do wygenerowania oficjalnej listy na potrzeby DWZ,
						zatem jego ostateczna forma powinna być niezmieniona względem pierwotnej. 
					\n* Funkcja pozwalająca przekształcić ten tymczasowy ranking do oficjalnej listy
						na potrzeby DWZ bierze wszystkich studentów znajdujących się w tymczasowym
						rankingu, zatem w kompetencjach osoby zajmującej się obróbką tymczasowego 
						rankingu jest usunięcie z niej wpisów o osobach, które nie otrzymają miejsca w aktualnej edycji. 
					\n* W celu ułatwienia pracy, w wygenerowanym przez nas arkuszu znajduje się kolumna „score”, która reprezentuje 
						liczbę punktów zdobytą zgodnie z wzorem przedstawionym na początku opracowania. 

					\n ### 6. Zatwierdzanie rankingu i generowanie listy dla DWZ
					\n* Zgodnie z podaną wcześniej informacją wygenerowany ranking może posłużyć 
					do zatwierdzenia rekrutacji i wygenerowania oficjalnego dokumentu gotowego do przekazania do DWZ.
					W tym celu w zakładce __WYNIKI__ w fragmencie __Prześlij końcowe wyniki rekrutacji__, należy po 
						kliknięciu w przycisk __Wybierz__ wskazać zeedytowany plika .csv, a następnie przesłać przyciskiem __Prześlij__. 
						\n* Aby możliwe było wygenerowanie oficjalnej lsity na potrzeby DWZ niezbędne są do spełnienia 2 warunki: 
						\n    1. Ostateczne i poprawne przygotowanie listy rankingowej, 
						\n    2. Załadowanie listy rankingowej do systemu zgodnie ze wskazówkami podanymi w poprzednim punkcie
					\n * Po spełnieniu tych warunków, możliwe będzie naciśnięcie przycisku odpowiedzialnego za wygenerowanie ostatecznej listy dla DWZ.  
					\n* Sam przebieg generowania jest identyczny z generowaniem tymczasowej listy rankingowej, 
					z tego względu zalecamy zapoznanie się z zamieszczoną tam instrukcją. 

					\n ### 7. Ustawienia edycji
					\n* Do dyspozycji administratora oddana jest również zakładka __USTAWIENIA__ pozwala ona na: 
					\n    1. Zmianę daty otwarcia i zamknięcia rekrutacji w systemie online. 
					\n    2. Zmianę dokumentu wzorcowego dostępnego dla zalogowanych studentów. 
					\n	  3. Zmianę liczby dostępnych ankiet dla studenta. `;

export default function HelpMarkdown() {
	return (
		<Container fluid className="p-0" style={{ maxWidth: "50%" }}>
			<h2 className="help__header">
				{"Jak korzystać z portalu rekrutacyjnego"}
			</h2>
			<ReactMarkdown>{markdownSteps}</ReactMarkdown>
		</Container>
	);
}
