
export interface Result<T = any> {
    status: number;
    message: string;
    data?: T;
}

export interface ArgumentErrorMessage{
    field: string;
    message: string;
}

export interface ErrorResponse {
    code: number;
    message: string;
    fields: ArgumentErrorMessage[]
}
