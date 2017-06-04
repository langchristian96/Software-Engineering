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
  fileList: FileList;

  constructor(private paperService: PaperService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.paperService.getPaper(+params['id']))
      .subscribe(paper => this.paper = paper);
  }

  updatePaperDetail(authors: string): void {
    this.paper.authorsUsername = authors.split(', ');
    this.paperService.updatePaper(this.paper).subscribe(_ => this.cancel());
  }

  fileChange(e){
    this.fileList = e.target.files;
  }

  addPaperWithFile(): void {
    if (this.fileList.length > 0) {
      let file: File = this.fileList[0];
      let formData: FormData = new FormData();
      formData.append('uploadFile', file, file.name);
      formData.append('paper', new Blob([JSON.stringify(this.paper)],
        {
          type: "application/json"
        }));
      this.paperService.addPaperWithFile(formData);
    }
  }

  cancel(): void {
    this.location.back();
  };

}


