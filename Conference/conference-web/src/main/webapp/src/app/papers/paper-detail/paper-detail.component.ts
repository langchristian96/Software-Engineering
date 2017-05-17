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
  templateUrl: './paper-detail.component.html',
  styleUrls: ['./paper-detail.component.css']
})

export class PaperDetailComponent implements OnInit {
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

  updatePaperDetail(): void {
    this.paperService.updatePaper(this.paper).subscribe(_ => this.cancel());
  }

  cancel(): void {
    this.location.back();
  };

}


