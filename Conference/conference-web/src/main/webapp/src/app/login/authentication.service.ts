import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {
  private loginUrl="http://localhost:8080/login";
  private heads=new Headers({'Content-Type': 'application/json'});
  private heads2=new Headers({'Content-Type': 'application/x-www-form-urlencoded', 'Access-Control-Allow-Origin':'http://localhost:8080'});

  constructor(private http: Http) { }

  login(username: string, password: string) {
    console.log("Username: ",username);
    console.log("Password: ",password);
    let sentUser={username:username,password:password};
    let body = JSON.stringify({ username, password });
    return this.http.post(this.loginUrl, "username="+username+"&password="+password,{headers:this.heads2}) //"username=" + username +
      // "&password=" + password,this.heads2)//JSON.stringify({ username: username, password: password })
      // .map((response: Response) => {
      //   // login successful if there's a jwt token in the response
      //   console.log(response);
      //   let user = response.json();
      //   if (user && user.token) {
      //     // store user details and jwt token in local storage to keep user logged in between page refreshes
      //     localStorage.setItem('currentUser', JSON.stringify(user));
      //   }
      // });

    .map((response: Response) => {
      // login successful if there's a jwt token in the response
      localStorage.setItem('currentUser','asd');
      // if () {
      //   // store user details and jwt token in local storage to keep user logged in between page refreshes
      //   localStorage.setItem('currentUser', JSON.stringify(user));
      // }
    });


  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
  }
}
