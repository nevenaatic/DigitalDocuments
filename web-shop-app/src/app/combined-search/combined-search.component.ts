import { trigger } from '@angular/animations';
import { BootstrapOptions, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SearchService } from '../service/search.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-combined-search',
  templateUrl: './combined-search.component.html',
  styleUrls: ['./combined-search.component.css']
})
export class CombinedSearchComponent implements OnInit {
  nameContent : string = ""
  cvContent: string=""
  clContent: string =""
  educationLvl:string =""
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
      op : 'AND',
      phrase: false
    }];
  nameOperatorSelected:string="";
  educationOperatorSelected:string="";
  cvOperatorSelected: string="";
  clOperatorSelected:string="";


  namePS: boolean = false;
  educationPS: boolean= false;
  cvPS: boolean= false;
  clPS:boolean=false;


  constructor( private router: Router, private searchService: SearchService, private snack: MatSnackBar) { }

  ngOnInit(): void {
  }


  cancel=() => this.router.navigate(['/']);

  educationLevel($event:any) {
 
    this.educationLvl= $event.target.value;
      // alert("EDUCATION LVL" + this.educationLvl)
      }

  selectPhraseName(){
   this.namePS = true;
  }
  selectPhraseEducation(){
    this.educationPS = true;
   }
   selectPhraseCv(){
    this.cvPS = true;
   }
   selectPhraseCL(){
    this.clPS = true;
   }

   nameOperator($event:any){
   
    this.nameOperatorSelected= $event.target.value 
   // alert("NAME SELECT "+ this.nameOperatorSelected)
   }
   educationOperator($event:any){
    this.educationOperatorSelected= $event.target.value
   // alert("EDUCATION OPERATOR"+this.educationOperatorSelected)
   }
   cvOperator($event:any){
    this.cvOperatorSelected= $event.target.value
   // alert("CV OPERATOR"+this.cvOperatorSelected)
   }
   clOperator($event:any) {
    this.clOperator = $event.target.value
   }

  
  submit() {
    console.log(this.nameContent)
    console.log(this.nameOperatorSelected)
    console.log(this.namePS)

    
    console.log(this.educationLvl)
    console.log("ED SEL ********")
    console.log(this.educationOperatorSelected)
    console.log(" ********")
    console.log(this.educationPS)
this.fields.shift(); // uklanjam onaj prvi obj
    if(this.nameContent != "") {
      const newField = { criteria: "name", content: this.nameContent, op: this.nameOperatorSelected, phrase: this.namePS };
   this.fields.push(newField);
    }
     if(this.educationLvl != ""){
      const newField = { criteria: "education", content: this.educationLvl, op: this.educationOperatorSelected, phrase: this.educationPS };
     console.log(newField);
      this.fields.push(newField);
    }
     if(this.cvContent != ""){
      const newField = { criteria: "cvContent", content: this.cvContent, op: this.cvOperatorSelected, phrase: this.cvPS };
      this.fields.push(newField);
    }
     if(this.clContent!= "") {
      const newField = { criteria: "clContent", content: this.clContent, op: this.clOperatorSelected, phrase: this.clPS };
      this.fields.push(newField);
    }
    console.log(this.fields)
    console.log(JSON.stringify(this.fields));

    this.searchService.advancedSearch(JSON.stringify(this.fields)).subscribe(
      (data: any) => {
        this.result = data
        console.log(data)
        if(this.result.length == 0) {
          this.snack.open('Poruka koju želite prikazati', 'Zatvori', {
            duration: 2000, 
          });
        }
        this.fields = [];
      }
    )
  }
}
