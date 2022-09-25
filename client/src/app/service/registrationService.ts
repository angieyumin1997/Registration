import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable, OnInit } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { firstValueFrom, lastValueFrom, map, Subject, tap } from "rxjs";
import { Id, Registration, Response } from "../models";

@Injectable()
export class registrationService{

    constructor(private http:HttpClient){
    }

    newRegistration(registration:Registration):Promise<Response>{
        const headers = new HttpHeaders()
            .set('Content-Type','application/json')
            .set('Accept','application/json')
        return lastValueFrom(
            this.http.post<Response>('https://obscure-hamlet-44481.herokuapp.com/api/registration',registration,{headers})
            // .pipe()
            )
    }

    allRegistrations():Promise<Registration[]>{
      
        return lastValueFrom(
            this.http.get<Registration[]>('https://obscure-hamlet-44481.herokuapp.com/api/registration')
            .pipe(
            map(result=>{
                return result
            })   
            )
        )
    }

    deleteRegistration(contactId:string):Promise<Response>{
        console.info('>>>>>>id:',contactId)
        const headers = new HttpHeaders()
            .set('Content-Type','application/json')
            .set('Accept','application/json')
        let idJson:Id = {
            id: contactId
        }

        return lastValueFrom(
            this.http.post<Response>('https://obscure-hamlet-44481.herokuapp.com/api/registration/delete',idJson,{headers})
            // .pipe()
            )
    }
}
