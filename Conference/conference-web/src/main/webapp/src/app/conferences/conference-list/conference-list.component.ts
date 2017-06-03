/**
 * Created by user on 5/4/2017.
 */

import { Component, OnInit } from '@angular/core';
import {Conference} from "../shared/conference.model";
import {Router} from "@angular/router";
import {ConferenceService} from "../shared/conference.service";

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

  constructor(private conferenceService: ConferenceService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getConferences();
  }

  getConferences() {
    this.conferenceService.getConferences()
      .subscribe(
        conferences => this.conferences = conferences,
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

  addNewConference(): void{
    this.router.navigate(['/conference/new']);
  }

}
