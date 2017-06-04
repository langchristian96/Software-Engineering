import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import {PersonService} from "../persons/shared/person.service";

@Injectable()
export class AuthenticationService {
  private loginUrl="http://localhost:8080/login";
  private heads=new Headers({'Content-Type': 'application/json'});
  private heads2=new Headers({'Content-Type': 'application/x-www-form-urlencoded', 'Access-Control-Allow-Origin':'http://localhost:4200'});

  constructor(private http: Http,
              private personService: PersonService) { }

  login(username: string, password: string) {
    console.log("Username: ",username);
    console.log("Password: ",password);
    let sentUser={username:username,password:password};
    let body = JSON.stringify({ username, password });
    let resu=this.http.post(this.loginUrl, "username="+username+"&password="+password,{withCredentials: true,headers:this.heads2});
    //console.log(resu);
    //alert(resu);
    return resu //"username=" + username +
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
      this.personService.getPersonIdByUsern(username).subscribe(data=>{
        localStorage.setItem('userId',String(data));
        console.log("userID: ",data);
      });


      localStorage.setItem('currentUser',username);
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
