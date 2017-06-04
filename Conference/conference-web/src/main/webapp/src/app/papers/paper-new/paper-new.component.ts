/**
 * Created by Adriana on 5/4/2017.
 */
import {Component, OnInit, Input} from '@angular/core';
import {Paper} from "../shared/paper.model";
import {PaperService} from "../shared/paper.service";
import {Location} from '@angular/common';
import {Http, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Component({
  moduleId: module.id,
  selector: 'app-paper-new',
  templateUrl: './paper-new.component.html',
  styleUrls: ['./paper-new.component.css']
})
export class PaperNewComponent  {
  paper: Paper;

  constructor(private paperService: PaperService,
              private location: Location) {
  }

  cancel(): void {
    this.location.back();
  };

  addPaper(title, abstractText, keywords, topics, authors, sessionId): void{
    var authorsUsername = authors.split(', ');
    var contentPath = "mumu";
    let paper = {title, abstractText, contentPath, keywords, topics, authorsUsername, sessionId};
    this.paperService.createPaper(paper).subscribe(_ => this.cancel());
  }

  private isValid(title, author, content) {
    if ( !title || !author || !content) {
      console.log("all fields are required");
      return false;
    }
    return true;
  }
}
