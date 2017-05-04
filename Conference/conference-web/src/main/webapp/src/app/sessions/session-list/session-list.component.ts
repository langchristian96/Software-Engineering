import {Component, OnInit} from "@angular/core";
import {Session} from "../shared/session.model";
import {SessionService} from "../shared/session.service";
import {Router} from "@angular/router";


@Component({
  moduleId: module.id,
  selector:'session-list',
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.css'],
})

export class SessionListComponent implements OnInit{
  errorMessage: string;
  session: Session[];
  // selectedClient: Client;

  constructor(private sessionService: SessionService,
              private router: Router){}

  ngOnInit(): void{
    this.getSessions();
  }

  getSessions(){
    this.sessionService.getSessions()
      .subscribe(
        session => this.session = session,
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
