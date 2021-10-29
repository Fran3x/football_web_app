import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private url: string = "http://localhost:4200";
  user: any;
  isLogged: number=0;

  constructor(private http:HttpClient) {

  }

  loginUser(login: string, password: string): Observable<any>{
    return this.http.get<any>(this.url+"/api/login/"+login+"/"+password);
  }
  registerUser(login: string, password: string): Observable<any>{
    return this.http.get<any>(this.url+"/api/register/"+login+"/"+password);
  }
  getIS(): number {
    return this.isLogged;
  }
  getUser(): string {
    return this.user;
  }
  setIS(n: number){
    this.isLogged=n;
  }
  setUser(u: string){
    this.user=u;
  }

}
