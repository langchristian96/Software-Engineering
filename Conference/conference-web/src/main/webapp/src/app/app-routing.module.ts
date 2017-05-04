/**
 * Created by user on 5/4/2017.
 */

import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ConferencesComponent} from "./conferences/conferences.component";
import {ConferenceDetailComponent} from "./conferences/conference-detail/conference-detail.component";
import {ConferenceNewComponent} from "./conferences/conference-new/conference-new.component";


const routes: Routes = [

  { path: '', redirectTo: '/', pathMatch: 'full' },

  { path: 'conferences',     component: ConferencesComponent },
  { path: 'conference/detail/:id', component: ConferenceDetailComponent},
  { path: 'conference/new', component: ConferenceNewComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
