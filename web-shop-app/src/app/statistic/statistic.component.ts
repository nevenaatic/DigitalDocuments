import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-statistic',
  templateUrl: './statistic.component.html',
  styleUrls: ['./statistic.component.css']
})
export class StatisticComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

    back() {
      this.router.navigate(['/homepage'])
    }
}
