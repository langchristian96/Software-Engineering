/**
 * Created by user on 5/4/2017.
 */

import {Injectable} from '@angular/core';
import {Http, Response, Headers} from "@angular/http";

import {Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import {Conference} from "./conference.model";


@Injectable()
export class ConferenceService {
  private conferencesUrl = 'http://localhost:8080/api/conferences';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {
  }

  getConferences(): Observable<Conference[]> {
    return this.http.get(this.conferencesUrl)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body.conferences || {};
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

  getConference(id: number): Observable<Conference> {
    return this.getConferences()
      .map(conferences => conferences.find(conference => conference.id === id));
  }

  create(name: string, edition: number, startDate: string, endDate: string, callDate: string, papersDeadline: string/*, committee: string, sections: string*/): Observable<Conference> {
    let conference = {name, edition, startDate, endDate, callDate, papersDeadline/*, committee, sections*/};
    return this.http
      .post(this.conferencesUrl, JSON.stringify({"conference": conference}), {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }


  update(conference): Observable<Conference> {
    const url = `${this.conferencesUrl}/${conference.id}`;
    return this.http
      .put(url, JSON.stringify({"conference": conference}), {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  delete(id: number): Observable<void> {
    const url = `${this.conferencesUrl}/${id}`;
    return this.http
      .delete(url, {headers: this.headers})
      .map(() => null)
      .catch(this.handleError);
  }

}
