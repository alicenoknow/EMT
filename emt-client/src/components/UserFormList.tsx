import React from "react";
import { List, ListItem, ListItemIcon, ListItemText } from "@material-ui/core";
import { useNavigate } from "react-router-dom";
import FeedIcon from "@mui/icons-material/Feed";
import "./UserFormList.scss";

interface UserFormListProps {
	config: {
		formsNum: number;
		startDate?: Date;
		endDate?: Date;
	};
}

export default function UserFormList(props: UserFormListProps) {
	const navigate = useNavigate();

	return (
		<div>
			<h2>Dokumenty rekrutacyjne</h2>
			<List dense>
				{Array.from(Array(props.config?.formsNum ?? 0).keys()).map(
					(elem: number) => {
						return (
							<ListItem
								key={elem}
								className="list__item"
								onClick={() => {
									navigate("/form", { state: { priority: elem + 1 } });
								}}>
								<ListItemIcon>
									<FeedIcon />
								</ListItemIcon>
								<ListItemText
									primary="Ankieta rekrutacyjna"
									secondary={`${elem + 1} wybór`}
								/>
							</ListItem>
						);
					},
				)}
				<ListItem
					key={"lang"}
					className="list__item"
					onClick={() => navigate("/docs")}>
					<ListItemIcon>
						<FeedIcon />
					</ListItemIcon>
					<ListItemText primary="Certyfikaty językowe" secondary={""} />
				</ListItem>
				<ListItem
					key={"other"}
					className="list__item"
					onClick={() => navigate("/docs")}>
					<ListItemIcon>
						<FeedIcon />
					</ListItemIcon>
					<ListItemText primary="Dodatkowe dokumenty" secondary={""} />
				</ListItem>
			</List>
		</div>
	);
}
