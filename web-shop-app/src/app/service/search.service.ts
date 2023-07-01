import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private search_url = 'http://localhost:8082/search';

  constructor(private http: HttpClient) { }

  geoSearch(content: { city: string; radius: string; }){
    return this.http.post(`${this.search_url}/location`, content);

  }

  simpleSearch(content:any){
    return this.http.post(`${this.search_url}/applicant`, content);
  }


  educationSearch(content:any){
    return this.http.post(`${this.search_url}/education`, content);

  }

  CVSearch(content:any){
    return this.http.post(`${this.search_url}/cv`, content);

  }

  CLSearch(content:any){
    return this.http.post(`${this.search_url}/cl`, content);

  }
}
