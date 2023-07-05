import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './home-page/homepage/homepage.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ClassicSearchComponent } from './classic-search/classic-search.component';
import { CombinedSearchComponent } from './combined-search/combined-search.component';
import { GeopointSearchComponent } from './geopoint-search/geopoint-search.component'
import { FormsModule } from '@angular/forms';
import { AddApplicantComponent } from './add-applicant/add-applicant.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { WorkersComponent } from './workers/workers.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { StatisticComponent } from './statistic/statistic.component';
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    ClassicSearchComponent,
    CombinedSearchComponent,
    GeopointSearchComponent,
    AddApplicantComponent,
    WorkersComponent,
    SignInComponent,
    StatisticComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatFormFieldModule, 
    MatButtonModule,
    MatCardModule,
    MatSnackBarModule

  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
