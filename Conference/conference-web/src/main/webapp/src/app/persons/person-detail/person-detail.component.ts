import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';

import {PersonService} from "../shared/person.service";
import {Person} from "../shared/person.model";
import {Author} from "../shared/author.model";
import {Reviewer} from "../shared/reviewer.model";
import {Listener} from "../shared/listener.model";


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
    let crt=this.newUrl.split("/")[1];
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


    if(crt == "listener"){
      this.personType = "Listener";
      this.inputs = [
        { type: 'number', value: '-1', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'password', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''}
      ];
      let a = new Listener(-1, "", "", "", "", "", []);
      let array = Object.getOwnPropertyNames(a);
      for(var i = 0; i < array.length; i++){
        this.inputs[i].name = array[i];
      }
    }


    if(this.newUrl.includes("reviewer")){
      this.personType = "Reviewer";
      this.inputs = [
        { type: 'number', value: '-1', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'password', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''}
      ];
      let a = new Reviewer(-1, "", "", "", "", "", []);
      let array = Object.getOwnPropertyNames(a);
      for(var i = 0; i < array.length; i++){
        this.inputs[i].name = array[i];
      }
    }
  }

  ngOnInit(): void {
    console.log(this.newUrl.substring(1, this.newUrl.length - 9));
    var ur = this.newUrl.substring(1, this.newUrl.length);
    let crt=this.newUrl.split("/")[1];
    this.route.params
      .switchMap((params: Params) => this.personService.getPerson(+params['id'], this.newUrl.substring(0, ur.indexOf('/') + 1) + 's'))
      .subscribe(person => {
        this.person = person;
        var newPerson;
        if(this.newUrl.includes("person")) {
          newPerson = new Person(person.id, person.usern, person.password, person.name, person.affiliation, person.email);
        }
        else if(this.newUrl.includes("author")){
          var newPers =<Author> person;
          newPerson = new Author(person.id, person.usern, person.password, person.name, person.affiliation, person.email, newPers.papers)
        }
        else if(this.newUrl.includes("reviewer")){
          var newPersRev = <Reviewer> person;
          newPerson = new Reviewer(person.id, person.usern, person.password, person.name, person.affiliation, person.email, newPersRev.papers)
        }
        else if(crt == "listener"){
          var newPers2 =<Listener> person;
          newPerson = new Listener(person.id, person.usern, person.password, person.name, person.affiliation, person.email, newPers2.sessions)
        }
        for (var i = 0; i < this.inputs.length; i++) {
          var str = this.inputs[i].name;
          this.inputs[i].value = newPerson[str];
        }
      });
  }

  cancel(): void {
    this.location.back();
  }

  updatePersonDetail(myForm): void {
    var ur = this.newUrl.substring(1, this.newUrl.length);
    let crt=this.newUrl.split("/")[1];
    if (this.newUrl.includes("person")) {
      let usern = myForm.form.value.inputss.usern;
      let id = myForm.form.value.inputss.id;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let person = {id, usern, password, name, affiliation, email};

      this.personService.updatePerson(this.newUrl.substring(0, ur.indexOf('/') + 1) + 's', person)
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
      this.personService.updatePerson(this.newUrl.substring(0, ur.indexOf('/') + 1) + 's', author)
        .subscribe(_ => this.cancel());
    }
    else if (this.newUrl.includes("reviewer")) {
      let papers = myForm.form.value.inputss.papers;
      let id = myForm.form.value.inputss.id;
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let reviewer = {id, usern, password, name, affiliation, email, papers};
      this.personService.updatePerson(this.newUrl.substring(0, ur.indexOf('/') + 1) + 's', reviewer)

        .subscribe(_ => this.cancel());
    } else if (this.newUrl.includes("listener")) {
      let sessions = myForm.form.value.inputss.sessions;
      let id = myForm.form.value.inputss.id;
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let listener = {id, usern, password, name, affiliation, email, sessions};
      this.personService.updatePerson("/"+crt + 's', listener)
        .subscribe(_ => this.cancel());
    }
  }
}

