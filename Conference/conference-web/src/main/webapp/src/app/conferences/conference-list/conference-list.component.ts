/**
 * Created by user on 5/4/2017.
 */

import { Component, OnInit } from '@angular/core';
import {Conference} from "../shared/conference.model";
import {Router} from "@angular/router";
import {ConferenceService} from "../shared/conference.service";
import {Session} from "../../sessions/shared/session.model";
import {SessionService} from "../../sessions/shared/session.service";
import {Observable} from "rxjs/Observable";
import {PaperService} from "../../papers/shared/paper.service";
import {Paper} from "../../papers/shared/paper.model";

@Component({
  moduleId: module.id,
  selector: 'app-conference-list',
  templateUrl: './conference-list.component.html',
  styleUrls: ['./conference-list.component.css']
})
export class ConferenceListComponent implements OnInit {
  errorMessage: string;
  conferences: Conference[];
  selectedConference: Conference;
  sessions: Session[];
  papers: Paper[];

  constructor(private conferenceService: ConferenceService,
              private sessionService: SessionService,
              private paperService: PaperService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getConferences();
    // this.getSessionsById(1);
  }

  getConferences() {
    this.conferenceService.getConferences()
      .subscribe(
        conferences => this.conferences = conferences,
        error => this.errorMessage = <any>error
      );
  }

  getSessions() {
    this.sessionService.getSessions()
      .subscribe(
        sessions => this.sessions = sessions,
        error => this.errorMessage = <any>error
      );
  }

  getPapers() {
    this.paperService.getPapers()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  getSessionsById(id:number) {
    this.sessionService.getSessionById(id)
      .subscribe(
        sessions => this.sessions = sessions,
        error => this.errorMessage = <any>error
      );
    console.log(this.sessionService.getSessionById(id));
  }

  getPapersById(id:number) {
    this.paperService.getPaperById(id)
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );

  }


  onSelect(conference: Conference): void {
    this.selectedConference= conference;
  }

  gotoDetail(): void {
    this.router.navigate(['/conference/detail/', this.selectedConference.id]);
  }

  deleteConference(conference: Conference): void {
    this.conferenceService.delete(conference.id)
      .subscribe(() => {
        this.conferences = this.conferences.filter(c => c !== conference);
        if (this.selectedConference === conference) {
          this.selectedConference = null;
        }
      });
  }

  delete(conferenceId): void{
    this.conferenceService.delete(conferenceId).subscribe(_ => this.getConferences());
  }

  viewSessions(conferenceId): void {
  }

  addNewConference(): void{
    this.router.navigate(['/conference/new']);
  }

}
