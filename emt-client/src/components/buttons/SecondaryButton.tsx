import React from "react";
import { Button } from "react-bootstrap";
import './SecondaryButton.scss';

interface SecondaryButtonProps {
    text: string;
    onClick: () => void;
}

export default function SecondaryButton (props: SecondaryButtonProps) {
return (
        <div className="secondary">
            <Button className="secondary__button" variant="link" onClick={props.onClick}>
                {props.text}
            </Button>
        </div>
    );
}