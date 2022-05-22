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

export interface AdminQueryData {
	data: {
		formList?: StudentFormRecord[];
		config?: {
			startDate?: Date;
			endDate?: Date;
			defaultPDFLink?: string;
		};
	};
}
