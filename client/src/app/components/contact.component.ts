import { Component, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { Registration } from '../models';
import { registrationService } from '../service/registrationService';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  form! : FormGroup

  @Output()
  showAllRegistrations = new Subject<Registration[]>()

  @Output()
  onNewRegistration = new Subject<Registration>()
  
  constructor(private fb:FormBuilder,private registrationSvc:registrationService) { }

  ngOnInit(): void {
    this.form=this.createForm()
  }

  private createForm(): FormGroup{
    return this.fb.group({
      name:this.fb.control("",[Validators.required, Validators.minLength(3)]),
      email:this.fb.control("",[Validators.required]),
      mobile:this.fb.control("",[Validators.required, Validators.minLength(3)])
    })
  }

  processForm(){
    const registration = this.form.value as Registration
    console.info('>>>> registration: ', registration)
    this.onNewRegistration.next(registration)
    const data=this.form.value
    console.info('>>>>>>form:',data)
    this.registrationSvc.allRegistrations().then((result: Registration[]) => {
      this.showAllRegistrations.next(result)
    })
  }

  hasError(ctrlName:string){
    return this.form.get(ctrlName)?.hasError('required')
  }

}
