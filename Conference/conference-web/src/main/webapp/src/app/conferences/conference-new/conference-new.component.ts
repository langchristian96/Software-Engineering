/**
 * Created by user on 5/4/2017.
 */

import {Component, OnInit, Input} from '@angular/core';
import {Conference} from "../shared/conference.model";
import {ConferenceService} from "../shared/conference.service";
import {Location} from '@angular/common';

@Component({
  moduleId: module.id,
  selector: 'app-conference-new',
  templateUrl: './conference-new.component.html',
  styleUrls: ['./conference-new.component.css']
})
export class ConferenceNewComponent  {
  @Input() conference: Conference;

  constructor(private conferenceService: ConferenceService,
              private location: Location) {
  }

  goBack(): void {
    this.location.back();
  }


  save(name, edition, startDate, endDate, callDate, papersDeadline, committee, sections): void {
    if (!this.isValid(name, edition, startDate, endDate, callDate, papersDeadline, committee, sections)) {
      console.log("all fields are required ");
      alert("all fields are required; cnp has to be an int");
      return;
    }
    this.conferenceService.create(name, edition, startDate, endDate, callDate, papersDeadline, committee, sections)
      .subscribe(_ => this.goBack());
  }

  private isValid(name, edition, startDate, endDate, callDate, papersDeadline, committee, sections) {
    if ( !name || !edition || !startDate || !endDate || !callDate || !papersDeadline || !committee || !sections) {
      console.log("all fields are required");
      return false;
    }
    if (!Number.isInteger(Number(edition))) {
      console.log("Conference edition has to be an int");
      return false;
    }
    return true;
  }
}
