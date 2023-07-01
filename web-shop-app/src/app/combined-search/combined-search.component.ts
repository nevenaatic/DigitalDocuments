import { trigger } from '@angular/animations';
import { BootstrapOptions, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-combined-search',
  templateUrl: './combined-search.component.html',
  styleUrls: ['./combined-search.component.css']
})
export class CombinedSearchComponent implements OnInit {
  content : string = ""
  phrase = false
  criteria = 0
  result = [
    {
      "name": "",
      "surname": "",
      "education": "",
      "highlight": "",
      "id": ""
    }
  ]
  nameSelect:boolean=true;
  educationSelect: boolean= true;
  cvSelect: boolean = true;
  clSelect:boolean=true;


  namePS: boolean = false;
  educationPS: boolean= false;
  cvPS: boolean= false;
  clPS:boolean=false;


  constructor( private router: Router) { }

  ngOnInit(): void {
  }
  selectPhrase = () => this.phrase = true;

  cancel=() => this.router.navigate(['/']);

  educationLevel($event:any) {this.content= $event.target.value;}
}
