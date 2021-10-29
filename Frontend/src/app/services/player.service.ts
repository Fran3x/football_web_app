import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IPlayer } from '../interfaces/player';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private url: string = "http://localhost:4200";
  private url2: string = "assets/data/ranking.json";

  constructor(private http: HttpClient) { }

  getPlayers(team: string): Observable<IPlayer[]>{
    return this.http.get<IPlayer[]>(this.url + "/api/players/" + team);
  }
  getTopGoalScorers(league: string): Observable<IPlayer[]>{
    return this.http.get<IPlayer[]>(this.url + "/api/goals/" + league);
    //return this.http.get<IPlayer[]>(this.url2);
  }
  getTopAssists(league: string): Observable<IPlayer[]>{
    return this.http.get<IPlayer[]>(this.url + "/api/assists/" + league);
  }
}
