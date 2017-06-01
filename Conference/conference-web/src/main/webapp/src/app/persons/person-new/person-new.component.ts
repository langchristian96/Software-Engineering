import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';
import 'rxjs/add/operator/switchMap';
import {Person} from "../shared/person.model";
import {PersonService} from "../shared/person.service";
import {Author} from "../shared/author.model";
import {forEach} from "@angular/router/src/utils/collection";

@Component({
  selector: 'person-edit',
  templateUrl: './person-new.component.html',
  styleUrls: ['./person-new.component.css'],
})

export class PersonNewComponent implements OnInit{
  @Input()
  inputs: any;
  personType: string;
  newUrl: string;

  constructor(private personService: PersonService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router){
    this.newUrl = router.url;
    if(this.newUrl.substring(1, this.newUrl.length - 4) == "person"){
      this.personType = "Person";
      this.inputs = [
        { type: 'text', value: '', name: ''},
        { type: 'password', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
      ];
      let a = new Person(-1, "", "", "", "", "");
      let array = Object.getOwnPropertyNames(a);
      for(var i = 1; i < array.length; i++){
        this.inputs[i - 1].name = array[i];
      }
    }
    if(this.newUrl.substring(1, this.newUrl.length - 4) == "author"){
      this.personType = "Author";
      this.inputs = [
        { type: 'text', value: '', name: ''},
        { type: 'password', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''}
      ];
      let a = new Author(-1, "", "", "", "", "", []);
      let array = Object.getOwnPropertyNames(a);
      for(var i = 1; i < array.length; i++){
        this.inputs[i - 1].name = array[i];
      }
    }
  }

  ngOnInit(): void{
  }

  cancel(): void {
    this.location.back();
  };

  addPerson(myForm): void {
    if(this.newUrl.includes("person")){
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let person = {usern, password, name, affiliation, email};
      this.personService.createPerson(this.newUrl.substring(0, this.newUrl.length - 4) + 's', person).subscribe(_ => this.cancel());
    }
    else if(this.newUrl.includes("author")) {
      let papers = myForm.form.value.inputss.papers.split(', ');
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let author = {usern, password, name, affiliation, email, papers};
      this.personService.createPerson(this.newUrl.substring(0, this.newUrl.length - 4) + 's', author).subscribe(_ => this.cancel());
    }
  }
}
