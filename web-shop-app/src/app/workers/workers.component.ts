import { Component, OnInit } from '@angular/core';
import { RegisterService } from '../service/register.service';

@Component({
  selector: 'app-workers',
  templateUrl: './workers.component.html',
  styleUrls: ['./workers.component.css']
})
export class WorkersComponent implements OnInit {

  constructor(private registerService:  RegisterService) { }

  result = [
    {
      "name": "",
      "address": "",
      "education": "",
      "id": "",
      "isHired": true
    }
  ]

  company = 1

  ngOnInit(): void {
    this.registerService.getAll().subscribe(
      (data: any) => {
        this.result = data
        console.log(this.result)
      }
    )
  }

  selectCompany($event:any){
    this.company = $event.target.value
  }


  hire(id: any){
    let employee = localStorage.getItem('id')

    var dto = {
      "applicantId": id,
      "companyId": this.company,
      "employeeId": employee
    }

    this.registerService.hire(dto).subscribe(
      (data: any) => {
        alert("Well done! New emloyee hired!")
        this.registerService.getAll().subscribe( (data: any) => {
          this.result = data
          console.log(this.result)
        })
      }
    )

  }

  

}
