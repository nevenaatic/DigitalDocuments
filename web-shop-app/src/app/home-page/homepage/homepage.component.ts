import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {

  constructor( private router: Router) { }

  ngOnInit(): void {
  }

  public classicSearch = () => this.router.navigate(['/classic-search'])

  public combinedSearch = () => this.router.navigate(['/combined-search'])

  public geopointSearch = () => this.router.navigate(['/geopoint-search'])

  public workersShow = () => this.router.navigate(['/workers'])
  
  public addApplicant = ()=> this.router.navigate(['/applicant'])

  public showStatistic =() => this.router.navigate(['/statistic']);
}
