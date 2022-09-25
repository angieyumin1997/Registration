import { Component, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { Registration } from '../models';
import { registrationService } from '../service/registrationService';

@Component({
  selector: 'app-contact-list',
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css']
})

export class ContactListComponent implements OnInit{

  constructor(private registrationSvc:registrationService) { }

  allRegistrations: Registration[] = []

  ngOnInit(): void {
    this.getAllRegistrations() 
  }

  delete(id:string){
    this.registrationSvc.deleteRegistration(id).then(result=>{
      this.getAllRegistrations()
    })
  }

  getAllRegistrations() {
    this.registrationSvc.allRegistrations().then((result: Registration[]) => {
      this.allRegistrations= result
  })
  }


}