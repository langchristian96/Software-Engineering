
import { Component, OnInit } from '@angular/core';
import {Person} from "../shared/person.model";
import {Router} from "@angular/router";
import {PersonService} from "../shared/person.service";

@Component({
  moduleId: module.id,
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {
  errorMessage: string;
  persons: Person[];
  selectedPerson: Person;

  constructor(private personService: PersonService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getPersons();
  }

  getPersons() {
    this.personService.getPersons()
      .subscribe(
        persons => this.persons = persons,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(person: Person): void {
    this.selectedPerson= person;
  }

  gotoDetail(): void {
    this.router.navigate(['/person/detail/', this.selectedPerson.id]);
  }

  deletePerson(person: Person): void {
    this.personService.delete(person.id)
      .subscribe(() => {
        this.persons = this.persons.filter(c => c !== person);
        if (this.selectedPerson === person) {
          this.selectedPerson = null;
        }
      });
  }
  addNewPerson(): void{
    this.router.navigate(['/person/new']);
  }

}
