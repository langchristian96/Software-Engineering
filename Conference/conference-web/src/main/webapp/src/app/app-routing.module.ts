/**
 * Created by user on 5/4/2017.
 */

import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ConferencesComponent} from "./conferences/conferences.component";
import {ConferenceDetailComponent} from "./conferences/conference-detail/conference-detail.component";
import {ConferenceNewComponent} from "./conferences/conference-new/conference-new.component";
import {SessionsComponent} from "./sessions/sessions.component";
import {SessionEditComponent} from "./sessions/session-edit/session-edit.component";
import {SessionNewComponent} from "./sessions/session-new/session-new.component";
import {PersonsComponent} from "./persons/persons.component";
import {PersonDetailComponent} from "./persons/person-detail/person-detail.component";
import {PapersComponent} from "./papers/papers.component";
import {PaperDetailComponent} from "./papers/paper-detail/paper-detail.component";
import {PaperNewComponent} from "./papers/paper-new/paper-new.component";


const routes: Routes = [

  { path: '', redirectTo: '/', pathMatch: 'full' },
  {path: 'sessions', component: SessionsComponent},
  {path: 'session/edit/:id', component: SessionEditComponent},
  {path: 'session/new', component: SessionNewComponent},
  { path: 'conferences', component: ConferencesComponent },
  { path: 'conference/detail/:id', component: ConferenceDetailComponent},
  { path: 'conference/new', component: ConferenceNewComponent},
  { path: 'persons', component: PersonsComponent },
  { path: 'person/detail/:id', component: PersonDetailComponent},
  { path: 'papers', component: PapersComponent },
  { path: 'paper/detail/:id', component: PaperDetailComponent},
  { path: 'paper/new', component: PaperNewComponent},

];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
