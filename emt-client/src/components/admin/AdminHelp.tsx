import React from "react";
import ReactMarkdown from "react-markdown";

const markdown = `A paragraph with *emphasis* and **strong importance**.



`;

export default function HelpMarkdown() {
	return <ReactMarkdown>{markdown}</ReactMarkdown>;
}
