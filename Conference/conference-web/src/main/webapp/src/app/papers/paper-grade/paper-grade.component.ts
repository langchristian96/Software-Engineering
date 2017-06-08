/**
 * Created by Adriana on 5/4/2017.
 */
import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';

import 'rxjs/add/operator/switchMap';

import {PaperService} from "../shared/paper.service";
import {Paper} from "../shared/paper.model";


@Component({
  selector: 'app-paper-detail',
  templateUrl: './paper-grade.component.html',
  styleUrls: ['./paper-grade.component.css']
})

export class PaperGradeComponent implements OnInit {
  @Input()
  paper: Paper;

  constructor(private paperService: PaperService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.paperService.getPaper(+params['id']))
      .subscribe(paper => this.paper = paper);
  }

  gradePaper(grade): void {
    this.paperService.gradePaper(this.paper.id, JSON.parse(localStorage.getItem('userId')), parseInt(grade)).subscribe(_ => this.cancel());
  }

  cancel(): void {
    this.location.back();
  };

}


