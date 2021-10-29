import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import * as moment from 'moment';
import { IGameweek } from 'src/app/interfaces/gameweek';
import { IPlayer } from 'src/app/interfaces/player';
import { ITableRow } from 'src/app/interfaces/tableRow';
import { LeagueService } from 'src/app/services/league.service';
import { PlayerService } from 'src/app/services/player.service';
import { TableService } from 'src/app/services/table.service';

@Component({
  selector: 'app-league',
  templateUrl: './league.component.html',
  styleUrls: ['./league.component.css']
})
export class LeagueComponent implements OnInit {

  public table: ITableRow[] = [];
  public goals: IPlayer[] = [];
  public assists: IPlayer[] = [];
  public finishedMatches: IGameweek[] = [];
  public nextMatches: IGameweek[]= [];
  league: any;
  selection: number = 1;
  public view: number = 20;
  public view2: number = 2;

  constructor(private _tableService: TableService, private route: ActivatedRoute,
    private _playerService: PlayerService, private _leagueService: LeagueService) { }

  ngOnInit(): void {
    this.initFunctions();
  }

  initFunctions(){
    this.league = this.route.snapshot.paramMap.get('league')
    this._tableService.getTable(this.league).subscribe(data => this.table = data);
    this._playerService.getTopGoalScorers(this.league).subscribe(data => this.goals = data);
    this._playerService.getTopAssists(this.league).subscribe(data => this.assists=data);
    this._leagueService.getNextMatches(this.league,moment().format('l')).subscribe(data => this.nextMatches = data);
    this._leagueService.getFinishedMatches(this.league).subscribe(data => this.finishedMatches = data);
  }
  
  againInit(l: string) {
    console.log(l);
    this.league= l;
    this.initFunctions;
  }
  select(n: number){
    this.view2 = 2;
    this.view=20;
    this.selection = n;
  }
}
