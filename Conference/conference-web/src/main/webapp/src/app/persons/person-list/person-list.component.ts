
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
  newUrl: string;

  constructor(private personService: PersonService,
              private router: Router) {
    this.newUrl = router.url
  }

  ngOnInit(): void {
    this.getPersons();
  }

  getPersons() {
    console.log("test");
    console.log(this.newUrl);
    this.personService.getPersons(this.newUrl)
      .subscribe(
        persons => this.persons = persons//,
        //error => this.errorMessage = <any>error
      );
    console.log(this.persons);
  }

  onSelect(person: Person): void {
    this.selectedPerson= person;
  }

  editPerson(personId: number): void {
    this.router.navigate([this.newUrl.substring(0, this.newUrl.length - 1) + '/detail/', personId]);
  }

  deletePerson(personId): void {
      this.personService.deletePerson(this.newUrl, personId).subscribe(_ => this.getPersons());
  }
  addNewPerson(): void{
    this.router.navigate([this.newUrl.substring(0, this.newUrl.length - 1)+'/new']);
  }

}
