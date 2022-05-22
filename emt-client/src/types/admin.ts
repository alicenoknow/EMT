export interface StudentFormRecord {
	firstName: string;
	lastName: string;
	major: string;
	faculty: string;
	contractCoordinator: string;
	priority: number;
	link: string;
	formId: string;
}

export interface FormApiListRecord {
	id: string;
	timeAdded: Date;
	timeLastModified: Date;
	rankingPoints: number;
	firstName: string;
	lastName: string;
	major: string;
	faculty: string;
	contractCoordinator: string;
	priority: number;
	oneDriveFormLink: string;
	oneDriveLinkScan: string;
}
