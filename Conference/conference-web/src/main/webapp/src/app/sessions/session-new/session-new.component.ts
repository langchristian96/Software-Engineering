import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';
import 'rxjs/add/operator/switchMap';
import {Session} from "../shared/session.model";
import {SessionService} from "../shared/session.service";

@Component({
  selector: 'session-edit',
  templateUrl: './session-new.component.html',
  styleUrls: ['./session-new.component.css'],
})

export class SessionNewComponent implements OnInit{
  @Input()
  session: Session;

  constructor(private sessionService: SessionService,
              private route: ActivatedRoute,
              private location: Location){}

  ngOnInit(): void{
  }

  cancel(): void {
    this.location.back();
  };

  addSession(date, conferenceId, sessionId): void{
    this.session = new Session(date, conferenceId, sessionId);
    this.sessionService.createSession(this.session).subscribe(_ => this.cancel());
  }
}
