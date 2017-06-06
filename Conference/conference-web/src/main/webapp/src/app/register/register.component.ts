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
  loading = false;

  constructor(
    private router: Router,
    private personService: PersonService,
    private alertService: AlertService) { }

  register(newUrl: string,username, password, name, affiliate, email) {
    this.loading = true;
    let affiliation=affiliate;
    let usern=username;
    let person = {usern, password, name, affiliation, email};
    this.personService.createPerson(newUrl, person)
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
