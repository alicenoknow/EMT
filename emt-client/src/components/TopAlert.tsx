import React from "react";
import { Alert } from "react-bootstrap";
import "./UtilsStyles.scss";

interface Props {
    type: 'success' | 'danger' | 'warning' | 'info';
    header?: string;
    body?: string;
}

export default function TopAlert(props: Props) {
    const { type, header, body } = props;
	return (
		<Alert className="alert" variant={type}>
            <Alert.Heading>{header}</Alert.Heading>
            <p>
                {body}
            </p>
        </Alert>
	);
}
