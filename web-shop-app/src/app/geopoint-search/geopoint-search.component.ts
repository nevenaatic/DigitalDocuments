import { Component, OnInit } from '@angular/core';
import { SearchService } from '../service/search.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-geopoint-search',
  templateUrl: './geopoint-search.component.html',
  styleUrls: ['./geopoint-search.component.css']
})
export class GeopointSearchComponent implements OnInit {
  content = {
    "city": "",
    "radius": ""
  }

  result = [
    {
      "firstName": "",
      "lastName": "",
      "education": "",
      "highlight": "",
      "address": ""
    }
  ]


  constructor(private searchService: SearchService, private router: Router) { }

  ngOnInit(): void {
  }
  submit(){
    this.searchService.geoSearch(this.content).subscribe(
      (data: any) => {
        this.result = data
        console.log(this.result)
      }
    )
  }

  cancel = () => this.router.navigate(['/'])
}
