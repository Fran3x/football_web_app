import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ILeague } from 'src/app/interfaces/league';
import { LeagueService } from 'src/app/services/league.service';

@Component({
  selector: 'leaguelist',
  templateUrl: './leaguelist.component.html',
  styleUrls: ['./leaguelist.component.css']
})
export class LeaguelistComponent implements OnInit {

  leagueList: ILeague[] = []
  @Output() leagueReload = new EventEmitter<string>();

  constructor(private _leagueservice: LeagueService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this._leagueservice.getLeagues().subscribe(data => this.leagueList = data);
  }

  selectLeague(league: string){
    this.router.navigate(['/league',league]).then();
    this.leagueReload.emit(league);
  }

}
