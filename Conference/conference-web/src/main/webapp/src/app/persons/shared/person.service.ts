
import {Injectable} from '@angular/core';
import {Http, Response, Headers} from "@angular/http";

import {Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import {Person} from "./person.model";


@Injectable()
export class PersonService {
  private personsUrl = 'http://localhost:8080/api/persons';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {
  }

  private heads2=new Headers({'Access-Control-Allow-Origin':'http://localhost:8080'});

  getPersons(): Observable<Person[]> {
    return this.http.get(this.personsUrl,{withCredentials: true})
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

  getPerson(id: number): Observable<Person> {
    return this.getPersons()
      .map(persons => persons.find(person => person.id === id));
  }

  create(usern: string, password: string, name: string, affiliation: string, email: string): Observable<Person> {
    let person = {usern, password, name, affiliation, email};
    return this.http
      .post(this.personsUrl, JSON.stringify({"person": person}), {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }


  update(person): Observable<Person> {
    const url = `${this.personsUrl}/${person.id}`;
    return this.http
      .put(url, JSON.stringify({"person": person}), {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  delete(id: number): Observable<void> {
    const url = `${this.personsUrl}/${id}`;
    return this.http
      .delete(url, {headers: this.headers})
      .map(() => null)
      .catch(this.handleError);
  }

}

