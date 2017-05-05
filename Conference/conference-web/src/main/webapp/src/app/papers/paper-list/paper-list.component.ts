/**
 * Created by Adriana on 5/4/2017.
 */
import { Component, OnInit } from '@angular/core';
import {Paper} from "../shared/paper.model";
import {Router} from "@angular/router";
import {PaperService} from "../shared/paper.service";


@Component({
  moduleId: module.id,
  selector: 'app-paper-list',
  templateUrl: './paper-list.component.html',
  styleUrls: ['./paper-list.component.css']
})
export class PaperListComponent implements OnInit {
  errorMessage: string;
  papers: Paper[];
  selectedPaper: Paper;

  constructor(private paperService: PaperService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getPapers();
  }

  getPapers() {
    this.paperService.getPapers()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(paper: Paper): void {
    this.selectedPaper= paper;
  }

  gotoDetail(): void {
    this.router.navigate(['/paper/detail/', this.selectedPaper.id]);
  }

  delete(paper: Paper): void {
    this.paperService.delete(paper.id)
      .subscribe(() => {
        this.papers = this.papers.filter(c => c !== paper);
        if (this.selectedPaper === paper) {
          this.selectedPaper = null;
        }
      });
  }

}
