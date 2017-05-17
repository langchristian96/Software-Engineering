/**
 * Created by Adriana on 5/4/2017.
 */
import {Component, OnInit, Input} from '@angular/core';
import {Paper} from "../shared/paper.model";
import {PaperService} from "../shared/paper.service";
import {Location} from '@angular/common';

@Component({
  moduleId: module.id,
  selector: 'app-paper-new',
  templateUrl: './paper-new.component.html',
  styleUrls: ['./paper-new.component.css']
})
export class PaperNewComponent  {
  @Input() paper: Paper;

  constructor(private paperService: PaperService,
              private location: Location) {
  }

  cancel(): void {
    this.location.back();
  };

  addPaper(title, author, content): void{
    this.paper = new Paper(title, author, content);
    this.paperService.createPaper(this.paper).subscribe(_ => this.cancel());
  }

  private isValid(title, author, content) {
    if ( !title || !author || !content) {
      console.log("all fields are required");
      return false;
    }
    return true;
  }
}
