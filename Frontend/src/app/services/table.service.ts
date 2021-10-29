import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { ITableRow } from '../interfaces/tableRow';

@Injectable({
  providedIn: 'root'
})
export class TableService {

  private url: string = "http://localhost:4200"
  private url2: string = "/assets/data/table.json"
  constructor(private http: HttpClient) { }

  getTable(league: string): Observable<ITableRow[]>{
    //return this.http.get<ITableRow[]>(this.url + "/" + league)
    return this.http.get<ITableRow[]>(this.url + "/api/table/" + league);
  }
}
