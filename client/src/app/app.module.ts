import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import{HttpClient, HttpClientModule} from '@angular/common/http';

import { AppComponent } from './app.component';
import { ContactListComponent } from './components/contact-list.component';
import { ContactComponent } from './components/contact.component';
import { registrationService } from './service/registrationService';

@NgModule({
  declarations: [
    AppComponent,
    ContactListComponent,
    ContactComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [registrationService],
  bootstrap: [AppComponent]
})
export class AppModule { 
  
}
