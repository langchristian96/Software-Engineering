import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';
import 'rxjs/add/operator/switchMap';
import {Person} from "../shared/person.model";
import {PersonService} from "../shared/person.service";

@Component({
  selector: 'person-edit',
  templateUrl: './person-new.component.html',
  styleUrls: ['./person-new.component.css'],
})

export class PersonNewComponent implements OnInit{
  @Input()
  person: Person;

  constructor(private personService: PersonService,
              private route: ActivatedRoute,
              private location: Location){}

  ngOnInit(): void{
  }

  cancel(): void {
    this.location.back();
  };

  addPerson(username, password, name, affiliation, email): void{
    this.personService.create(username, password, name, affiliation, email).subscribe(_ => this.cancel());
  }
}
