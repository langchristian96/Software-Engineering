import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';

import {PersonService} from "../shared/person.service";
import {Person} from "../shared/person.model";
import {Author} from "../shared/author.model";


@Component({
  selector: 'app-person-detail',
  templateUrl: './person-detail.component.html',
  styleUrls: ['./person-detail.component.css']
})

export class PersonDetailComponent implements OnInit {
  @Input()
  person: Person;
  author: Author;
  inputs: any;
  personType: string;
  newUrl: string;

  constructor(private personService: PersonService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) {
    this.newUrl = router.url;
    //console.log(this.newUrl.substring(this.newUrl.lastIndexOf("/") + 1));
    let crt=this.newUrl.split("/")[1];
    console.log(crt);
    if(crt == "person"){
      this.personType = "Person";
      this.inputs = [
        { type: 'number', value: '-1', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'password', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
      ];
      let a = new Person(-1, "", "", "", "", "");
      let array = Object.getOwnPropertyNames(a);
      for(var i = 0; i < array.length; i++){
        this.inputs[i].name = array[i];
      }
    }
    if(crt == "author"){
      this.personType = "Author";
      this.inputs = [
        { type: 'number', value: '-1', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'password', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''}
      ];
      let a = new Author(-1, "", "", "", "", "", []);
      let array = Object.getOwnPropertyNames(a);
      for(var i = 0; i < array.length; i++){
        this.inputs[i].name = array[i];
      }
    }
  }

  ngOnInit(): void {
    let crt=this.newUrl.split("/")[1];
    this.route.params
      .switchMap((params: Params) => this.personService.getPerson(+params['id'], "/"+crt + 's'))
      .subscribe(person => {
        this.person = person;
        var newPerson;
        if(crt == "person") {
          newPerson = new Person(person.id, person.usern, person.password, person.name, person.affiliation, person.email);
        }
        else if(crt == "author"){
          var newPers =<Author> person;
          newPerson = new Author(person.id, person.usern, person.password, person.name, person.affiliation, person.email, newPers.papers)
        }
        for (var i = 0; i < this.inputs.length; i++) {
          var str = this.inputs[i].name;
          this.inputs[i].value = newPerson[str];
        }
      });
  }
  //this.newUrl.substring(this.newUrl.lastIndexOf("/") + 1)

  cancel(): void {
    this.location.back();
  }

  updatePersonDetail(myForm): void {
    let crt=this.newUrl.split("/")[1];
    if (this.newUrl.includes("person")) {
      let usern = myForm.form.value.inputss.usern;
      let id = myForm.form.value.inputss.id;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let person = {id, usern, password, name, affiliation, email};
      this.personService.updatePerson("/"+crt + 's', person)
        .subscribe(_ => this.cancel());
    } else if (this.newUrl.includes("author")) {
      let papers = myForm.form.value.inputss.papers;
      let id = myForm.form.value.inputss.id;
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let author = {id, usern, password, name, affiliation, email, papers};
      this.personService.updatePerson("/"+crt + 's', author)
        .subscribe(_ => this.cancel());
    }
  }
}

