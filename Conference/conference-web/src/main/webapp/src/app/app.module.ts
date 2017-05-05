import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import {SessionEditComponent} from "./sessions/session-edit/session-edit.component";
import {SessionNewComponent} from "./sessions/session-new/session-new.component";
import {SessionsComponent} from "./sessions/sessions.component";
import {SessionListComponent} from "./sessions/session-list/session-list.component";
import {ConferenceDetailComponent} from "./conferences/conference-detail/conference-detail.component";
import {ConferenceNewComponent} from "./conferences/conference-new/conference-new.component";
import {ConferencesComponent} from "./conferences/conferences.component";
import {ConferenceListComponent} from "./conferences/conference-list/conference-list.component";
import {SessionService} from "./sessions/shared/session.service";
import {ConferenceService} from "./conferences/shared/conference.service";
import {AppRoutingModule} from "./app-routing.module";
import {PersonDetailComponent} from "./persons/person-detail/person-detail.component";
import {PersonListComponent} from "./persons/person-list/person-list.component";
import {PersonService} from "./persons/shared/person.service";
import {PersonsComponent} from "./persons/persons.component";

@NgModule({
  declarations: [
    AppComponent,
    SessionEditComponent,
    SessionNewComponent,
    SessionsComponent,
    SessionListComponent,
    PersonDetailComponent,
    PersonListComponent,
    PersonsComponent,
    ConferenceDetailComponent,
    ConferenceNewComponent,
    ConferencesComponent,
    ConferenceListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
  ],
  providers: [SessionService, ConferenceService, PersonService],
  bootstrap: [AppComponent]
})
export class AppModule { }
