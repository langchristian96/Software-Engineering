
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

  private heads2=new Headers({'Access-Control-Allow-Origin':'http://localhost:8080'});


  getPersons(urlNew: string): Observable<Person[]> {
    // console.log(this.http.get(this.personsUrl + urlNew,{withCredentials: true})
    //   .map(this.extractData)
    //   .catch(this.handleError));
    console.log("Inside get persons: ",this.personsUrl + urlNew);
    return this.http.get(this.personsUrl + urlNew,{withCredentials: true})
      .map(this.extractData)
      .catch(this.handleError);
  }

  getPersonIdByUsern(usern: string): Observable<number> {
    let personByUsernURL=`http://localhost:8080/api/getPersonId/`+usern;
    console.log("inside getPersonIdByUsern function",personByUsernURL);
    let tempRes=this.http.get(personByUsernURL,{withCredentials: true});
    console.log(tempRes);
    return tempRes
      .map(this.extractId)
      .catch(this.handleError);
  }

  private extractId(res: Response) {
    let body = res.json();
    return body.personId || {};
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
    else if(urlNew.includes("listener")){
      m = JSON.stringify({"listener": person});

    }
    console.log("URL: ",this.personsUrl + urlNew);
    return this.http
      .post(this.personsUrl + urlNew, m, {withCredentials: true,headers: this.headers})
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
    else if(urlNew.includes("listener")){
      m = JSON.stringify({"listener": person});

    }
    const url = `${this.personsUrl+urlNew}/${person.id}`;
    return this.http
      .put(url, m, {withCredentials: true,headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  deletePerson(urlNew: string, id: number): Observable<void> {
    const url = `${this.personsUrl+ urlNew}/${id}`;
    return this.http
      .delete(url, {withCredentials: true,headers: this.headers})
      .map(() => null)
      .catch(this.handleError);
  }

}

