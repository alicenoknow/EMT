import React from "react";
import { List, ListItem, ListItemIcon, ListItemText } from "@material-ui/core";
import { useNavigate } from "react-router-dom";
import FeedIcon from "@mui/icons-material/Feed";
import "./UserFormList.scss";

export default function UserFormList() {
	const navigate = useNavigate();

	return (
		<div>
			<h2>Dokumenty rekrutacyjne</h2>
			<List dense>
				<ListItem
					className="list__item"
					onClick={() => {
						navigate("/form", { state: { firstChoice: true } });
					}}>
					<ListItemIcon>
						<FeedIcon />
					</ListItemIcon>
					<ListItemText
						primary="Ankieta rekrutacyjna"
						secondary={"Pierwszy wybór"}
					/>
				</ListItem>
				<ListItem
					className="list__item"
					onClick={() => {
						navigate("/form", { state: { firstChoice: false } });
					}}>
					<ListItemIcon>
						<FeedIcon />
					</ListItemIcon>
					<ListItemText
						primary="Ankieta rekrutacyjna"
						secondary={"Drugi wybór"}
					/>
				</ListItem>
				<ListItem className="list__item">
					<ListItemIcon>
						<FeedIcon />
					</ListItemIcon>
					<ListItemText primary="Certyfikaty językowe" secondary={""} />
				</ListItem>
				<ListItem className="list__item">
					<ListItemIcon>
						<FeedIcon />
					</ListItemIcon>
					<ListItemText primary="Dodatkowe dokumenty" secondary={""} />
				</ListItem>
			</List>
		</div>
	);
}
