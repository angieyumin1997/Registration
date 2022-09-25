export interface Registration{
    id: string
    name: string
    email: string
    mobile: string
}

export interface Response{
    code: number
    message?: string
    data?: any
}

export interface Id{
    id: string
}