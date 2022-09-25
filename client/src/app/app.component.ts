import { Component, OnInit } from '@angular/core';
import { Registration } from './models';
import { registrationService } from './service/registrationService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'miniproject1';

  add:boolean = false
  list:boolean = false

  constructor(private registrationSvc: registrationService){}

  processNewRegistration(newRegistration:Registration){
    this.list = true
    this.add = false
    console.info('>>>>>>>>new registration:', newRegistration)
    this.registrationSvc.newRegistration(newRegistration)
      .then(result => {
        console.info('>>>>>result:',result)
        alert(`Your registration id is ${result.message}`)
      })
      .catch(error =>{
        console.error('>>>>>>error:',error)
      })
  }

  register(){
    this.add = true
    this.list = false
  }

  listall(){
    this.list = true
    this.add = false
  }

}
