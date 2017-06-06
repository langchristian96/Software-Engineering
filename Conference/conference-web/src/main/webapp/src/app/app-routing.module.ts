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
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {PersonNewComponent} from "./persons/person-new/person-new.component";
import {AuthGuard} from "./guards/auth.guard";


const routes: Routes = [

  { path: '', redirectTo: '/', pathMatch: 'full', canActivate: [AuthGuard] },
  {path: 'sessions', component: SessionsComponent, canActivate: [AuthGuard]},
  {path: 'session/edit/:id', component: SessionEditComponent, canActivate: [AuthGuard]},
  {path: 'session/new', component: SessionNewComponent, canActivate: [AuthGuard]},
  { path: 'conferences', component: ConferencesComponent, canActivate: [AuthGuard] },
  { path: 'conference/detail/:id', component: ConferenceDetailComponent, canActivate: [AuthGuard]},
  { path: 'conference/new', component: ConferenceNewComponent, canActivate: [AuthGuard]},
  { path: 'persons', component: PersonsComponent, canActivate: [AuthGuard] },
  { path: 'person/detail/:id', component: PersonDetailComponent, canActivate: [AuthGuard]},
  { path: 'authors', component: PersonsComponent, canActivate: [AuthGuard] },
  { path: 'author/detail/:id', component: PersonDetailComponent, canActivate: [AuthGuard]},
  { path: 'author/new', component: PersonNewComponent, canActivate: [AuthGuard]},
  { path: 'papers', component: PapersComponent, canActivate: [AuthGuard] },
  { path: 'paper/detail/:id', component: PaperDetailComponent, canActivate: [AuthGuard]},
  { path: 'paper/new', component: PaperNewComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  // { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
