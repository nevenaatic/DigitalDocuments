import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from '../service/register.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-applicant',
  templateUrl: './add-applicant.component.html',
  styleUrls: ['./add-applicant.component.css']
})
export class AddApplicantComponent implements OnInit {


  constructor(private snackBar: MatSnackBar, private registerService: RegisterService, private router:Router) { }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  user = {
    "name": "",
    "surname": "",
    "education": "",
    "street": "",
    "city": "",
  }

  cv: any
  coverLetter: any
  result = [
    {
      "firstName": "",
      "lastName": "",
      "education": "",
      "highlight": ""
    }
  ]

  cancel  =() => this.router.navigate([''])

  submit(){

    const formData: FormData = new FormData();
    formData.append('name', this.user.name);
    formData.append('surname', this.user.surname);
    formData.append('education', this.user.education);
    formData.append('street', this.user.street);
    formData.append('city', this.user.city);
    formData.append('cv', this.cv);
    formData.append('coverLetter', this.coverLetter);

    this.registerService.register(formData).subscribe(
      (data: any) => {
        this.result = data
          this.snackBar.open('Successfull!', 'Close', {
            duration: 2000,
          });
        
       // this.router.navigate(['/workers'])

      }
    )
  }

  addCV(cv:any){
    const file: File = cv.target.files[0];
    this.cv = file
    console.log(this.user.surname)
    // const formData: FormData = new FormData();
    // formData.append('file', file, file.name);
    //this.user.cv = formData
    // this.registerService.sendFile(formData).subscribe(
    //   (data: any) => {
    //     console.log(data)
    //   }
    // )

  }

  addCL(cl:any){
    const file: File = cl.target.files[0];
    // const formData: FormData = new FormData();
    // formData.append('file', file, file.name);
    // this.user.coverLetter = formData
    this.coverLetter = file;
  }


  educationLevel($event:any){
    var level = $event.target.value
    alert(level)
    if(level == 1){
      this.user.education = "I - four grades"
    }
    else if(level == 2){
      this.user.education = "II - primary school"
    }
    else if(level == 3){
      this.user.education = "III - Three-year professional school"
    }
    else if(level == 4){
      this.user.education = "IV -Four-year professional school "
    }
    else if(level == 5){
      this.user.education = "V - College or Higher School"
    }
    else if(level == 6){
      this.user.education = "VI - University"
    }
    else if(level == 7){
      this.user.education = "VII - Magistracy"
    }
    else if(level == 8){
      this.user.education = "VIII - Doctorate/PhD "
    }
  }

}
