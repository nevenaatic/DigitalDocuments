import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './home-page/homepage/homepage.component';
import { ClassicSearchComponent } from './classic-search/classic-search.component';
import { CombinedSearchComponent } from './combined-search/combined-search.component';
import { GeopointSearchComponent } from './geopoint-search/geopoint-search.component';
import { AddApplicantComponent } from './add-applicant/add-applicant.component';
import { WorkersComponent } from './workers/workers.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { StatisticComponent } from './statistic/statistic.component';

const routes: Routes = [
  { path: '', component: SignInComponent },
  { path: 'homepage', component: HomepageComponent },
  { path: 'classic-search', component: ClassicSearchComponent },
  { path: 'combined-search', component: CombinedSearchComponent},
  { path: 'geopoint-search', component: GeopointSearchComponent}, 
  { path: 'applicant', component: AddApplicantComponent}, 
  { path: 'workers', component: WorkersComponent}, 
  { path: 'statistic', component:StatisticComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
