import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SearchService } from '../service/search.service';
import { RegisterService } from '../service/register.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-classic-search',
  templateUrl: './classic-search.component.html',
  styleUrls: ['./classic-search.component.css']
})
export class ClassicSearchComponent implements OnInit {
  content : string = ""
  phrase = false
  criteria = 0
  result = [
    {
      "firstName": "",
      "lastName": "",
      "education": "",
      "highlight": "",
      "id": ""
    }
  ]
  showLevel = false
  constructor( private router: Router, private searchService: SearchService, private registerService: RegisterService, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  selectField($event:any){
    this.criteria = $event.target.value
    if(this.criteria == 4){
      this.showLevel = true
    }
    else{
      this.showLevel = false
    }
  }

  selectPhrase = () => this.phrase = true;

  cancel=() => this.router.navigate(['/']);

  educationLevel($event:any) {this.content= $event.target.value;}

  submit(){

    var content = {
      "content": this.content,
      "phrase": this.phrase
    }
    console.log(content)

    if(this.criteria == 1){
      this.searchService.simpleSearch(content).subscribe(
        (data: any) => {
          this.result = data
          console.log(this.result)
        }
      )
    }
    else if(this.criteria == 4){
      this.searchService.educationSearch(content).subscribe(
        (data: any) => {
          this.result = data
          console.log(this.result)
        }
      )
    }
    else if(this.criteria == 2){
      this.searchService.CVSearch(content).subscribe(
        (data: any) => {
          this.result = data
          console.log(this.result)
        }
      )
    }
    else if(this.criteria == 3){
      this.searchService.CLSearch(content).subscribe(
        (data: any) => {
          this.result = data
          console.log(this.result)
        }
      )
    }
  }
  
  downloadCV(i:any){
    let dto = {
      "id": i,
      "isCV": true
    }

    this.registerService.download(dto).subscribe(
      (data: any) => {
        console.log(this.result)
      }
    )
  }

  downloadCL(i:any){
    let dto = {
      "id": i,
      "isCV": false
    }

    this.registerService.download(dto).subscribe(
      (data: any) => {
        console.log(this.result)
        alert("Uspesno preuzet fajl!!")
      }
    )
  }
}

