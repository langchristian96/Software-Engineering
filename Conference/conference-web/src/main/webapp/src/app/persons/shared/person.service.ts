
import {Inject, Injectable, Optional} from '@angular/core';
import {Http, Response, Headers} from "@angular/http";

import {Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import {Person} from "./person.model";


@Injectable()
export class PersonService {
  private personsUrl = 'http://localhost:8080/api';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {
  }

  getPersons(urlNew: string): Observable<Person[]> {
    console.log(this.http.get(this.personsUrl + urlNew)
      .map(this.extractData)
      .catch(this.handleError));
    return this.http.get(this.personsUrl + urlNew)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body.persons || {};
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    return Observable.throw(errMsg);
  }

  getPerson(id: number, urlNew): Observable<Person> {
    return this.getPersons(urlNew)
      .map(persons => persons.find(person => person.id === id));
  }

  createPerson(urlNew: string, person): Observable<Person> {
    let m = "";
    if(urlNew.includes("person")){
      m = JSON.stringify({"person": person});
    }
    else if(urlNew.includes("author")){
      m = JSON.stringify({"author": person});
    }
    return this.http
      .post(this.personsUrl + urlNew, m, {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }


  updatePerson(urlNew: string, person): Observable<Person> {
    let m = "";
    if(urlNew.includes("person")){
      m = JSON.stringify({"person": person});
    }
    else if(urlNew.includes("author")){
      m = JSON.stringify({"author": person});
    }
    const url = `${this.personsUrl+urlNew}/${person.id}`;
    return this.http
      .put(url, m, {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  deletePerson(urlNew: string, id: number): Observable<void> {
    const url = `${this.personsUrl+ urlNew}/${id}`;
    return this.http
      .delete(url, {headers: this.headers})
      .map(() => null)
      .catch(this.handleError);
  }

}

