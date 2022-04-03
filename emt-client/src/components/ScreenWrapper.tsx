import React from "react";
import NavBar from "./NavBar";
import "./ScreenWrapper.scss";

interface EmptyProps {
    nothing?: never;
}

export default function ScreenWrapper (props: React.PropsWithChildren<EmptyProps>) {
    return (
        <div className="container">
            <NavBar />
            {props.children}
        </div>
    );
}