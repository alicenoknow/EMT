import React, { useState } from "react";
import { Button, Container, Form, Row, Col } from "react-bootstrap";

interface RecruitSurveyState {
    isSent: boolean;
}

export default function RecruitSurveyForm() {
    const [state, setState] = useState<RecruitSurveyState>({
        isSent: false,
    });

    const { isSent } = state;

    return (
        <Container>
            <Form>
                <Form.Group className="mb-3" controlId="name">
                    <Row>
                        <Col>
                            <Form.Label>Imie</Form.Label>
                            <Form.Control type="text" placeholder="Imię" />
                            <Form.Label>Nazwisko</Form.Label>
                            <Form.Control type="text" placeholder="Nazwisko" />
                            <Form.Label>Wydział</Form.Label>
                            <Form.Select>
                                <option>Ceramika</option>
                                <option>Informatyka</option>
                                <option>Garncarstwo</option>
                            </Form.Select>
                            <Form.Label>Stopień</Form.Label>
                            <Form.Control type="text" placeholder="Stopień" />

                        </Col>
                        <Col>
                            <Form.Label>Rok</Form.Label>
                            <Form.Control type="text" placeholder="Rok" />
                            <Form.Label>Kierunek</Form.Label>
                            <Form.Control type="text" placeholder="Kierunek studiów" />
                            <Form.Label>Adres</Form.Label>
                            <Form.Control type="text" placeholder="Adres stałego zamieszkania" />
                            <Form.Label>Telefon</Form.Label>
                            <Form.Control type="text" placeholder="telefon" />
                            <Form.Label>E-mail</Form.Label>
                            <Form.Control type="email" placeholder="you@student.agh.edu.pl" />
                        </Col>
                    </Row>

                    <Row>
                        <Col>
                            <Form.Label>Całkowicie zaliczony semestr</Form.Label>
                            <Form.Select aria-label="Wybierz semestr">
                                <option>Wybierz</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </Form.Select>
                            <Form.Label>
                                Język obcy
                            </Form.Label>
                            <Form.Control type="text" placeholder="polski" />
                            <Form.Label>Rodzaj certyfikatu</Form.Label>
                            <Form.Control type="value" placeholder="CAE"></Form.Control>
                            <Form.Check type="switch" id="other-type-switch" label="miejsce z innego wydziału"></Form.Check>
                            <Form.Label>Planowane miejsce praktyki</Form.Label>
                            <Form.Control type="value" placeholder="Kraków"></Form.Control>
                            <Form.Label>Praktyki od</Form.Label>
                            <Form.Control type="value" placeholder="01.01.2023"></Form.Control>
                            <Form.Label>Praktyki do</Form.Label>
                            <Form.Control type="value" placeholder="01.04.2023"></Form.Control>
                        </Col>

                        <Col>
                            <Form.Label>Średnia ważona za cały okres studiów</Form.Label>
                            <Form.Control type="value" placeholder="0.0"></Form.Control>
                            <Form.Label>Poziom certyfikatu/Ocena</Form.Label>
                            <Form.Control type="text" placeholder="C1"></Form.Control>
                            <Form.Label>Koordynator wydziałowy(wydział macierzysty studenta)</Form.Label>
                            <Form.Control type="text" placeholder="Jan Kowalski"></Form.Control>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Form.Check type="switch" id="other-type-switch" label="Wyjazd długoterminowy 2-12 miesięcy"></Form.Check>
                            <Form.Check type="switch" id="other-type-switch" label="Wyjazd krótkoterminowy 5-30 dni połączony z obowiązkową częścią wirtualną"></Form.Check>
                        </Col>
                        <Form.Label>Jeśli uczestniczyłeś już w programie Erasmus+ to poniżej podaj stopień
                            studiów, ilość miesiecy.
                        </Form.Label>
                        <Form.Control as="textarea" rows={2}></Form.Control>
                        <Form.Check type="switch" id="other-type-switch" label="Otrzymuję stypendium socjalne z AGH"></Form.Check>
                        <Form.Check type="switch" id="other-type-switch" label="Jestem osobą niepełnosprawną"></Form.Check>
                    </Row>
                </Form.Group>

                <Button variant="primary" type="submit" onClick={() => setState({isSent: true})}>
                    { isSent ? "Zapisz" : "Zapisano" }
                </Button>
            </Form>
        </Container>
    );
}
