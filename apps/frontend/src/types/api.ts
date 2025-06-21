export interface ArgumentErrorMessage {
	field: string;
	message: string;
}

export interface ErrorResponse {
	code: number;
	message: string;
	fields: ArgumentErrorMessage[];
}
