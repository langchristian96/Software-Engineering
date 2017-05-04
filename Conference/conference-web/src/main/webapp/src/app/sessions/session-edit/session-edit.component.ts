import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Location} from '@angular/common';
import 'rxjs/add/operator/switchMap';
import {SessionService} from "../shared/session.service";
import {Session} from "../shared/session.model";

@Component({
  selector: 'session-edit',
  templateUrl: './session-edit.component.html',
  styleUrls: ['./session-edit.component.css'],
})

export class SessionEditComponent implements OnInit{
  @Input()
  session: Session;

  constructor(private sessionService: SessionService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router){}

  ngOnInit(): void{
    this.route.params
      .switchMap((params: Params) => this.sessionService.getSession(+params['id']))
      .subscribe(session => this.session = session);
  }

  updateSessionDetail(): void {
    this.sessionService.updateSession(this.session).subscribe(_ => this.cancel());
  }

  cancel(): void {
    this.location.back();
  };
}
