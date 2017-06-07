import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';
import 'rxjs/add/operator/switchMap';
import {Person} from "../shared/person.model";
import {PersonService} from "../shared/person.service";
import {Author} from "../shared/author.model";
import {forEach} from "@angular/router/src/utils/collection";
import {Listener} from "../shared/listener.model";

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
    let crt=this.newUrl.split("/")[1];
    if(crt == "person"){
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
    if(crt == "author"){
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

    if(crt=="listener"){
      this.personType = "Listener";
      this.inputs = [
        { type: 'text', value: '', name: ''},
        { type: 'password', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''},
        { type: 'text', value: '', name: ''}
      ];
      let a = new Listener(-1, "", "", "", "", "", []);
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
    let crt=this.newUrl.split("/")[1];
    if(this.newUrl.includes("person")){
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let person = {usern, password, name, affiliation, email};
      this.personService.createPerson("/"+crt + 's', person).subscribe(_ => this.cancel());
    }
    else if(this.newUrl.includes("author")) {
      let papers = myForm.form.value.inputss.papers.split(', ');
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let author = {usern, password, name, affiliation, email, papers};
      this.personService.createPerson("/"+crt + 's', author).subscribe(_ => this.cancel());
    }

    else if(this.newUrl.includes("listener")) {
      let sessionsString = myForm.form.value.inputss.sessions.split(', ');
      let sessions=[];
      for(var i=0;i<sessionsString.length;i++){
        sessions.push(+sessionsString[i]);
      }
      let usern = myForm.form.value.inputss.usern;
      let password = myForm.form.value.inputss.password;
      let name = myForm.form.value.inputss.name;
      let affiliation = myForm.form.value.inputss.affiliation;
      let email = myForm.form.value.inputss.email;
      let listener = {usern, password, name, affiliation, email, sessions};
      this.personService.createPerson("/"+crt + 's', listener).subscribe(_ => this.cancel());
    }
  }
}
