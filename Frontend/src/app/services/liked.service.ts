import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class LikedService {

  private url: string = "http://localhost:4200";

  constructor(private http: HttpClient) { }

  addMatch(user: string, id: number): Observable<any>{
    console.log(this.url+"/api/addfav/"+user+"/"+id.toString());
    return this.http.get<any>(this.url+"/api/addfav/"+user+"/"+id.toString());
  }
  removeMatch(user: string, id: number): Observable<any>{
    return this.http.get<any>(this.url+"/api/removefav/"+user+"/"+id.toString());
  }
  matches(user: string): Observable<number[]>{
    return this.http.get<number[]>(this.url+"/api/favids/"+user);
  }
}
