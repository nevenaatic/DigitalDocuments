import { trigger } from '@angular/animations';
import { BootstrapOptions, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SearchService } from '../service/search.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RegisterService } from '../service/register.service';

@Component({
  selector: 'app-combined-search',
  templateUrl: './combined-search.component.html',
  styleUrls: ['./combined-search.component.css']
})
export class CombinedSearchComponent implements OnInit {
  nameContent: string = ""
  cvContent: string = ""
  clContent: string = ""
  educationLvl: string = ""
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
  fields = [
    {
      criteria: '',
      content: '',
      op: 'AND',
      phrase: false
    }];
  nameOperatorSelected: string = "";
  educationOperatorSelected: string = "";
  cvOperatorSelected: string = "";
  clOperatorSelected: string = "";


  namePS: boolean = false;
  educationPS: boolean = false;
  cvPS: boolean = false;
  clPS: boolean = false;


  constructor(private router: Router, private searchService: SearchService, private snack: MatSnackBar, private registerService: RegisterService) { }

  ngOnInit(): void {
  }

  cancel = () => this.router.navigate(['/homepage']);

  educationLevel($event: any) {
    this.educationLvl = $event.target.value;
  }

  selectPhraseName() {
    this.namePS = true;
  }
  selectPhraseEducation() {
    this.educationPS = true;
  }
  selectPhraseCv() {
    this.cvPS = true;
  }
  selectPhraseCL() {
    this.clPS = true;
  }

  nameOperator($event: any) {
    this.nameOperatorSelected = $event.target.value
  }
  educationOperator($event: any) {
    this.educationOperatorSelected = $event.target.value
  }
  cvOperator($event: any) {
    this.cvOperatorSelected = $event.target.value
  }
  clOperator($event: any) {
    this.clOperator = $event.target.value
  }


  submit() {
    this.fields.shift(); // uklanjam onaj prvi obj
    if (this.nameContent != "") {
      const newField = { criteria: "name", content: this.nameContent, op: this.nameOperatorSelected, phrase: this.namePS };
      this.fields.push(newField);
    }
    if (this.educationLvl != "") {
      const newField = { criteria: "education", content: this.educationLvl, op: this.educationOperatorSelected, phrase: this.educationPS };
      this.fields.push(newField);
    }
    if (this.cvContent != "") {
      const newField = { criteria: "cvContent", content: this.cvContent, op: this.cvOperatorSelected, phrase: this.cvPS };
      this.fields.push(newField);
    }
    if (this.clContent != "") {
      const newField = { criteria: "clContent", content: this.clContent, op: this.clOperatorSelected, phrase: this.clPS };
      this.fields.push(newField);
    }
    console.log(JSON.stringify(this.fields));

    this.searchService.advancedSearch(JSON.stringify(this.fields)).subscribe(
      (data: any) => {
        this.result = data
        this.fields = [];
      }
    )
  }


  downloadCV(i: any) {
    let dto = {
      "id": i,
      "isCV": true
    }

    this.registerService.download(dto).subscribe(
      (data: any) => {
        console.log(this.result)
        var file = new Blob([data], { type: 'application/pdf' })
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(file);
        link.download = 'downloadedCV.pdf';
        link.target = '_blank';
        link.click();
      }
    )
  }

  downloadCL(i: any) {
    let dto = {
      "id": i,
      "isCV": false
    }

    this.registerService.download(dto).subscribe(
      (data: any) => {
        console.log(this.result)
        console.log(this.result)
        var file = new Blob([data], { type: 'application/pdf' })
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(file);
        link.download = 'downloadedCL.pdf';
        link.target = '_blank';
        link.click();
      }
    )
  }
}
