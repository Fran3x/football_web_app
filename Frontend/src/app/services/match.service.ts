import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ILeague } from '../interfaces/league';
import { IMatch } from '../interfaces/match';

@Injectable({
  providedIn: 'root'
})
export class MatchService {

  private _url: string = "/assets/data/match.json";
  private _url2: string = "http://localhost:4200";

  constructor(private http:HttpClient) { }

  getFullMatchInfo(id: string, minute: string): Observable<IMatch[]>{
    //return this.http.get<IMatch[]>(this._url);
    return this.http.get<IMatch[]>(this._url2+"/api/match/" + id + "/" + minute);
  }
  getPreviousMatches(teamA: string, teamB: string): Observable<IMatch[]>{
    return this.http.get<IMatch[]>(this._url2+"/api/previous/"+ teamA + "/" + teamB);
  }
  
}
