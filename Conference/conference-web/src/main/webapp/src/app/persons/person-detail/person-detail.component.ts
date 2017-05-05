import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';

import {PersonService} from "../shared/person.service";
import {Person} from "../shared/person.model";


@Component({
  selector: 'app-person-detail',
  templateUrl: './person-detail.component.html',
  styleUrls: ['./person-detail.component.css']
})

export class PersonDetailComponent implements OnInit {
  @Input()
  person: Person;

  constructor(private personService: PersonService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.personService.getPerson(+params['id']))
      .subscribe(person => this.person = person);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.personService.update(this.person)
      .subscribe(_ => this.goBack());
  }

}

