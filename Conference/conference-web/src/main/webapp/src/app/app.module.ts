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
import {PaperDetailComponent} from "./papers/paper-detail/paper-detail.component";
import {PaperListComponent} from "./papers/paper-list/paper-list.component";
import {PaperService} from "./papers/shared/paper.service";
import {PapersComponent} from "./papers/papers.component";
import {PaperNewComponent} from "./papers/paper-new/paper-new.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {AlertService} from "./alert/alert.service";
import {AlertComponent} from "./alert/alert.component";
import { AuthGuard } from './guards/index';
import {AuthenticationService} from "./login/authentication.service";
import {GlobalApp} from "./helpers/global";

// class MyBaseRequestOptions extends BaseRequestOptions {
//   headers: Headers = new Headers({
//     'X-Requested-With': 'XMLHttpRequest'
//   });
//   withCredentials: boolean = true;
// }
import {PersonNewComponent} from "./persons/person-new/person-new.component";
import {CallbackPipe} from "./sessions/callback.pipe";
import {PaperGradeComponent} from "./papers/paper-grade/paper-grade.component";

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
    PersonNewComponent,
    PaperDetailComponent,
    PaperGradeComponent,
    PaperListComponent,
    PapersComponent,
    PaperNewComponent,
    ConferenceDetailComponent,
    ConferenceNewComponent,
    ConferencesComponent,
    ConferenceListComponent,
    LoginComponent,
    RegisterComponent,
    AlertComponent,
    CallbackPipe
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
  ],
  providers: [SessionService, ConferenceService, PersonService, PaperService, AlertService, AuthGuard, AuthenticationService, GlobalApp],
  bootstrap: [AppComponent]
})
export class AppModule { }
