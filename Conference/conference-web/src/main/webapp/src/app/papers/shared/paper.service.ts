/**
 * Created by Adriana on 5/4/2017.
 */
import {Injectable} from '@angular/core';
import {Http, Response, Headers} from "@angular/http";

import {Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import {Paper} from "./paper.model";


@Injectable()
export class PaperService {
  private papersUrl = 'http://localhost:8080/api/papers';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {
  }

  getPapers(): Observable<Paper[]> {
    return this.http.get(this.papersUrl)
      .map(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    let body = res.json();
    return body.papers || {};
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

  getPaper(id: number): Observable<Paper> {
    return this.getPapers()
      .map(papers => papers.find(paper => paper.id === id));
  }

  create(title: string, author: string, content: string): Observable<Paper> {
    let paper = {title, author, content};
    return this.http
      .post(this.papersUrl, JSON.stringify({"paper": paper}), {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }


  update(paper): Observable<Paper> {
    const url = `${this.papersUrl}/${paper.id}`;
    return this.http
      .put(url, JSON.stringify({"paper": paper}), {headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  delete(id: number): Observable<void> {
    const url = `${this.papersUrl}/${id}`;
    return this.http
      .delete(url, {headers: this.headers})
      .map(() => null)
      .catch(this.handleError);
  }

}

