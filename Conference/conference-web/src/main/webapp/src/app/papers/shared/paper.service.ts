/**
 * Created by Adriana on 5/4/2017.
 */
import {Injectable} from '@angular/core';
import {Http, Response, Headers, RequestOptions, ResponseContentType} from "@angular/http";

import {Observable} from "rxjs";
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';

import {Paper} from "./paper.model";


@Injectable()
export class PaperService {
  private papersUrl = 'http://localhost:8080/api/papers';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) {
  }

  getPapersByReviewerId(): Observable<Paper[]>{
    var user = JSON.parse(localStorage.getItem('userId'));
    var data = JSON.stringify({"reviewerId": user});
    let myParams = new URLSearchParams();
    myParams.append('reviewerId', user);
    return this.http.get(`${this.papersUrl}/reviewer/${user}`, {withCredentials: true, headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  getPapersByAuthorId(): Observable<Paper[]>{
    let user = localStorage.getItem('userId');

    let url='http://localhost:8080/api/papersOfAuthor/'
    return this.http.get(url+user, {withCredentials: true, headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  gradePaper(paperId, reviewerId, grade): Observable<void> {
    const url = `http://localhost:8080/api/reviewerpapers/${paperId}/${reviewerId}`;
    var data = JSON.stringify({"data": grade});
    return this.http
      .put(url, data, {withCredentials: true,headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  downloadPaper(paperId): Observable<any>{
    let headers = new Headers({'Content-Type': 'application/json'});
    return this.http.get(`${this.papersUrl}/download//${paperId}`, {withCredentials: true, headers: headers, responseType: ResponseContentType.Blob})
      .map((res:Response) => res.blob())
      .catch(this.handleError);
  }

  private extractDocument(res: any) {
    var blob = new Blob([res._body], { type: 'application/pdf' });
    var url=window.URL.createObjectURL(blob);
    window.open(url);
  }


  getPapers(): Observable<Paper[]> {
    return this.http.get(this.papersUrl,{withCredentials: true})
      .map(this.extractData)
      .catch(this.handleError);
  }

  getPaperById(id:number): Observable<Paper[]> {
    return this.getPapers()
      .map(papers => papers.filter(paper => paper.sessionId === id))

  }

  addPaperWithFile(formData): void{
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let options = new RequestOptions({withCredentials: true, headers: headers });
    this.http.post('http://localhost:8080/api/papers/file',formData, options)
    /*.map((res: Response) => res.json())*/
      .catch(error => Observable.throw(error))
      .subscribe(
        data =>{
          console.log(data);
        }
        ,
        error => console.log(error)
      )
  }

  private extractData(res: Response) {
    let body = res.json();
    console.log(body);
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

  createPaper(paper): Observable<Paper> {
    return this.http
      .post(this.papersUrl, JSON.stringify({"paper": paper}), {withCredentials: true,headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }


  updatePaper(paper): Observable<Paper> {
    const url = `${this.papersUrl}/${paper.id}`;
    return this.http
      .put(url, JSON.stringify({"paper": paper}), {withCredentials: true,headers: this.headers})
      .map(this.extractData)
      .catch(this.handleError);
  }

  deletePaper(id: number): Observable<void> {
    const url = `${this.papersUrl}/${id}`;
    return this.http
      .delete(url, {withCredentials: true,headers: this.headers})
      .map(() => null)
      .catch(this.handleError);
  }

}

