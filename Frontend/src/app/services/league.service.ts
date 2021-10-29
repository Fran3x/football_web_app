import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ILeague } from '../interfaces/league';
import { IGameweek } from '../interfaces/gameweek';

@Injectable({
  providedIn: 'root'
})
export class LeagueService {

  private url: string = "/assets/data/match2.json";
  private url2: string = "/assets/data/match2.json";
  private url3: string = "/assets/data/match3.json";
  private url4: string = "/assets/data/match4.json";
  private _url5:string = "http://localhost:4200";
  constructor(private http:HttpClient) { }

  getLeagues():Observable<ILeague[]>{
    return this.http.get<ILeague[]>(this._url5 + "/api/leagues");
  }

  getLiveMatches(): Observable<ILeague[]>{
    return this.http.get<ILeague[]>(this._url5 + "/api/live");
    //return this.http.get<ILeague[]>(this.url5+"/live");
  }
  getMatchesOn(date: String): Observable<ILeague[]>{
   // return this.http.get<ILeague[]>(this.url3);
    return this.http.get<ILeague[]>(this._url5+"/api/matches/" + date);
  }
  getLiked(user: string): Observable<ILeague[]>{
    return this.http.get<ILeague[]>(this._url5+"/api/favmatches/"+ user);
    //return this.http.get<ILeague[]>(this.url2+"/liked" + user);
  }

  getFinishedMatches(league: string): Observable<IGameweek[]>{
    //return this.http.get<IGameweek[]>(this._url5 + "/league/" + league + "/end/"+ date);
    return this.http.get<IGameweek[]>(this._url5 + "/api/previousgameweeks/");
  }

  getNextMatches(league: string, date: string): Observable<IGameweek[]>{
    //return this.http.get<IGameweek[]>(this._url5 + "/league/" + league + "/next/"+ date);
    return this.http.get<IGameweek[]>(this._url5 + "/api/cominggameweeks");
  }
  
}
