/**
 * Created by Adriana on 5/4/2017.
 */
import { Component, OnInit } from '@angular/core';
import {Paper} from "../shared/paper.model";
import {Router} from "@angular/router";
import {PaperService} from "../shared/paper.service";
import {GlobalApp} from "../../helpers/global";


@Component({
  moduleId: module.id,
  selector: 'app-paper-list',
  templateUrl: './paper-list.component.html',
  styleUrls: ['./paper-list.component.css']
})
export class PaperListComponent implements OnInit {
  errorMessage: string;
  papers: Paper[];
  newUrl: string;
  app:GlobalApp;

  // selectedPaper: Paper;

  constructor(private paperService: PaperService,
              private router: Router) {
    this.newUrl = router.url;
    this.app=new GlobalApp();
  }

  ngOnInit(): void {
    if(this.newUrl.includes("reviewer")){
      this.getPapersWithReviewerId();
    }else
    if(localStorage.getItem('userClass')!='admin'){
      this.getPapersWithAuthorId();
    }
    else
    {
      this.getPapers();
    }
  }

  getPapers() {
    this.paperService.getPapers()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  getPapersWithReviewerId(){
    this.paperService.getPapersByReviewerId()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  getPapersWithAuthorId(){
    this.paperService.getPapersByAuthorId()
      .subscribe(
        papers => this.papers = papers,
        error => this.errorMessage = <any>error
      );
  }

  editPaper (paperId): void {
    this.router.navigate(['/paper/detail', paperId]);
  }

  erasePaper(paperId): void{
    this.paperService.deletePaper(paperId).subscribe(_ => this.getPapers());
  }

  gradePaper(paperId): void{
    this.router.navigate(['/paper/reviewer/grade', paperId]);
  }

  addNewPaper(): void{
    this.router.navigate(['/paper/new']);
  }

}
