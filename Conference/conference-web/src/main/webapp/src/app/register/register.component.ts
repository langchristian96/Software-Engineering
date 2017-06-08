import { Component } from '@angular/core';
import { Router } from '@angular/router';

import {AlertService} from "../alert/alert.service";
import {PersonService} from "../persons/shared/person.service";

@Component({
  moduleId: module.id,
  templateUrl: './register.component.html'
})

export class RegisterComponent {
  person: any = {};
  registerType:string="person";
  loading = false;

  constructor(
    private router: Router,
    private personService: PersonService,
    private alertService: AlertService) { }

  changeRegisterType(type){
    console.log(type);
    if(type=="Chair/Cochair")
      this.registerType="person";
    else if(type=="Listener")
      this.registerType="listener"
    else if(type=="Reviewer/Author")
      this.registerType="reviewer"

    console.log(this.registerType);
  }

  register(newUrl: string,username, password, name, affiliate, email) {
    this.loading = true;
    console.log("Register type",this.registerType);

    let affiliation=affiliate;
    let usern=username;
    let person = {usern, password, name, affiliation, email};
    this.personService.createPerson("/"+this.registerType+"s", person)
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
