import {Component, OnInit} from '@angular/core';
import {GlobalApp} from "./helpers/global";

@Component({
  moduleId: module.id,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Conference-App';
  app:GlobalApp;
  ngOnInit(): void {
    this.app=new GlobalApp();

  }
//   constructor (public app: GlobalApp)  {
//   }
}


