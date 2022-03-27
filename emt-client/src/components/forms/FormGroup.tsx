import React from "react";
import { Form } from "react-bootstrap";
import type { InputType } from './formTypes';
import "./FormGroup.scss";

interface FormGroupProps {
    id: string;
    type: InputType;
    label?: string;
    value: string;
    placeholder?: string;
    onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    bottomText?: string; 
}

export default function FormGroup (props: FormGroupProps) {
    const { id, type, value, label, placeholder, onChange, bottomText } = props;
    return (
        <Form.Group className="form__group" controlId={id}>
             <Form.Label>{label}</Form.Label>
                <Form.Control
                    autoFocus
                    type={type}
                    value={value}
                    placeholder={placeholder}
                    onChange={onChange}
                />
            <Form.Text>{bottomText}</Form.Text>
        </Form.Group>
    );
}