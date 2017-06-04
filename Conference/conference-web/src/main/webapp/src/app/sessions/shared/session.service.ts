import {Injectable} from '@angular/core';
import{Http, Response, Headers} from '@angular/http';

import {Session} from './session.model';

import {Observable} from 'rxjs';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class SessionService{
  private sessionUrl = 'http://localhost:8080/api/sessions';

  constructor(private http: Http){}

  getSessions(): Observable<Session[]>{
    return this.http.get(this.sessionUrl,{withCredentials: true})
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(response: Response){
    let body = response.json();
    return body.sessions || {};
  }

  private handleError(error: Response | any){
    let errorMessage: string;
    if(error instanceof Response){
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errorMessage = `${error.status} - ${error.statusText || ''} ${err}`;
    } else{
      errorMessage = error.message ? error.message : error.toString();
    }
    console.error(errorMessage);
    return Observable.throw(errorMessage);
  }

  getSession(id: number): Observable<Session>{
    return this.getSessions()
      .map(sessions => sessions.find(session => session.id === id));
  }

  private extractSessionData(response:Response){
    let body=response.json();
    console.log("BODY OF Session DATA: ",body);
    return body.session || {};
  }

  createSession(session): Observable<Session>{
    let bodyString = JSON.stringify({"session":session}); // Stringify payload
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.post(this.sessionUrl, bodyString, {withCredentials: true,headers: headers}) // ...using post request
      .map(this.extractSessionData) // ...and calling .json() on the response to return data
      .catch(this.handleError); //...errors if
  }

  updateSession(session): Observable<Session>{
    let updateUrl = this.sessionUrl + 'update/' +session.id;
    let bodyString = JSON.stringify({"session":session}); // Stringify payload
    let headers = new Headers();
    console.log(bodyString);
    headers.append('Content-Type', 'application/json');

    return this.http.put(updateUrl, bodyString, {withCredentials: true,headers: headers}) // ...using post request
      .map(this.extractSessionData) // ...and calling .json() on the response to return data
      .catch(this.handleError); //...errors if
  }

  deleteSession(sessionId): Observable<Session>{
    let deleteUrl = this.sessionUrl + 'delete/' +sessionId;
    console.log(deleteUrl);
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');

    return this.http.delete(deleteUrl, {withCredentials: true,headers: headers}) // ...using post request
      .mapTo(()=>null) // ...and calling .json() on the response to return data
      .catch(this.handleError); //...errors if
  }
}
