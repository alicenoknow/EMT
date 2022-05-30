import React from "react";
import { Container } from "react-bootstrap";
import ReactMarkdown from "react-markdown";
import "./DocumentPage.scss";

const markdownStep1 = `
                    \n* W celu założenia konta należy przejść do zakładki __Rekrutacja__, 
                    wprowadzić mail w domenie agh.edu.pl oraz hasło, a następnie zatwierdzić przyciskiem __Zarejestruj__. 
                    Na górze strony pojawi się komunikat o poprawnym zarejestrowaniu użytkownika
                     oraz informacja o przesłaniu potwierdzenia na mail.
                     \n* W następnym kroku należy zalogować się na podany wcześniej adres email, 
                     potwierdzić własność emailu i aktywować konto. 
                     Po kliknięciu na przesłany w mailu link powinien pojawić się komunikat o poprawnym potwierdzeniu emaila.`;

const markdownStep2 = `
                        \n* Aby zalogować się na konto, należy podać email i hasło podane w procesie rejestracji. 
                        Następnie zatwierdzić dane przyciskiem __Zaloguj__. 
                        \n* Jeśli podano poprawne dane, użytkownik zostanie przekierowany do swojego profilu.
                        \n* W przypadku niepoprawnego logowania na górze straony, wyświetli się adekwatny komunikat.`;

const markdownStep3 = `\n* W celu złożenia ankiety rekrutacyjnej, należy w panelu pocznym wybrać 
                        zakładkę __Studia zagraniczne__ a następnie pole __Ankieta rekrutacyjna__ z odpowiednim priorytetem.  
                        \n* Następnie należy pobrać wzór dostępny na stronie, korzystając z przycisku __Pobierz wzór__.
                        \n* Pobrany dokument PDF należy wypełnić cyfrowo.
                        \n* Wypełniony dokument w wersji elektronicznej, należy załadować w polu __Wgraj wypełniony PDF__.
                        \n* Dokument należy także wydrukować, podpisać, a skan podpisanego dokumentu załadować w polu __Wgraj podpisany skan__.
                        \n* Przesłanie dokumentów należy zatwierdzić przyciskiem __Prześlij dokumenty__.`;

const markdownStep4 = `\n* W kazdym momencie __trwania procesu rekrutacji__ istnieje możliwość dokonania zmiany w ankietach.
                       \n* W tym celu należy ponownie złożyć ankietę, tak jak zostało to opisane w punkcie *Wypełnienie ankiety rekrutacyjne*, 
                       stara ankeita zostanie nadpsana przez nową.`;

const markdownStep5 = `\n* Aby przeglądać obecnie złożone ankiety, należy w panelu pocznym wybrać 
                        zakładkę __Studia zagraniczne__ a następnie pole __Ankieta rekrutacyjna__ z odpowiednim priorytetem
                        \n* Jeśli ankieta została złożona, będzie ona możliwa do pobrania poprzez kliknięcie przycisków __Pobierz wysłany skan__ oraz __Pobierz wysłany pdf__.`;

export default function UserHelp() {
	return (
		<div className="document">
			<Container fluid className="p-0" style={{ maxWidth: "50%" }}>
				<h2
					style={{
						margin: "5vh",
						alignSelf: "center",
						flex: 1,
						display: "flex",
						justifyContent: "center",
					}}>
					Jak korzystać z portalu rekrutacyjnego
				</h2>
				<h3>1. Rejestracja</h3>
				<ReactMarkdown>{markdownStep1}</ReactMarkdown>
				<h3>2. Logowanie</h3>
				<ReactMarkdown>{markdownStep2}</ReactMarkdown>
				<h3>3. Wypełnienie ankiety rekrutacyjnej</h3>
				<ReactMarkdown>{markdownStep3}</ReactMarkdown>
				<h3>4. Zmiany w ankiecie rekrutacyjnej</h3>
				<ReactMarkdown>{markdownStep4}</ReactMarkdown>
				<h3>5. Podgląd ankiet rekrutacyjnych</h3>
				<ReactMarkdown>{markdownStep5}</ReactMarkdown>
			</Container>
		</div>
	);
}
