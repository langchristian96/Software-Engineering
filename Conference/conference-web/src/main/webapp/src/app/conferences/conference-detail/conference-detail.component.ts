import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';

import {ConferenceService} from "../shared/conference.service";
import {Conference} from "../shared/conference.model";


@Component({
  selector: 'app-conference-detail',
  templateUrl: './conference-detail.component.html',
  styleUrls: ['./conference-detail.component.css']
})

export class ConferenceDetailComponent implements OnInit {
  @Input()
  conference: Conference;

  constructor(private conferenceService: ConferenceService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.conferenceService.getConference(+params['id']))
      .subscribe(conference => this.conference = conference);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.conferenceService.update(this.conference)
      .subscribe(_ => this.goBack());
  }

}

