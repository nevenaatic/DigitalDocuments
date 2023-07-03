import { Component, OnInit } from '@angular/core';
import { RegisterService } from '../service/register.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  name:string = "";
  password: string ="";
  employeeId: string =""
  constructor(private regiserService: RegisterService, private router: Router) { }

  ngOnInit(): void {
  }

  signIn() {
    var loginData = { name: this.name, password:this.password};
    this.regiserService.signIn(loginData).subscribe( (data:any) => {
      this.employeeId = data;
      console.log(data)
      localStorage.clear
      localStorage.setItem("id", this.employeeId)
      this.router.navigate(['/homepage'])
    }, 
    (err) => {
      alert("Wrong credentials")
    })
  }
}
