import {Component, OnInit} from "@angular/core";
import {Session} from "../shared/session.model";
import {SessionService} from "../shared/session.service";
import {Router} from "@angular/router";
import {GlobalApp} from "../../helpers/global";


@Component({
  moduleId: module.id,
  selector:'app-session-list',
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.css'],
})

export class SessionListComponent implements OnInit{
  errorMessage: string;
  sessions: Session[];
  app:GlobalApp;
  // selectedClient: Client;

  constructor(private sessionService: SessionService,
              private router: Router){
    this.app=new GlobalApp();
  }

  ngOnInit(): void{
    this.getSessions();
  }

  getSessions(){

    if(localStorage.getItem('userClass')=='person')
    this.sessionService.getSessionsForChair()
      .subscribe(
        session => this.sessions = session,
        error => this.errorMessage = <any>error
      );
    else this.sessionService.getSessions()
      .subscribe(
        session => this.sessions = session,
        error => this.errorMessage = <any>error
      );

  }

  editSession (sessionId): void {
    this.router.navigate(['/session/edit', sessionId]);
  }
  eraseSession(sessionId): void{
    this.sessionService.deleteSession(sessionId).subscribe(_ => this.getSessions());
  }

  addNewSession(): void{
    this.router.navigate(['/session/new']);
  }
}
