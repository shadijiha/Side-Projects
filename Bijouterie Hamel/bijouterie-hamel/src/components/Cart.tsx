import * as React from "react";
import { Component } from "react";

interface IProps {}

interface IState {}

class Cart extends React.Component<IProps, IState> {
	state = {};
	public render(): JSX.Element {
		return <span>This is the Fratured class</span>;
	}
}

export default Cart;
