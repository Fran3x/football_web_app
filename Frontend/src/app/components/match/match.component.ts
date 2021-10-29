import { Component, OnInit } from '@angular/core';
import { MatchService } from '../../services/match.service';
import { ILeague } from '../../interfaces/league';
import { IMatch } from '../../interfaces/match';
import { ActivatedRoute } from '@angular/router';
import { IYellowCard } from 'src/app/interfaces/yellowCard';
import { IRedCard } from 'src/app/interfaces/redCard';
import { IRefereree } from 'src/app/interfaces/referee';
import { IPlayer } from 'src/app/interfaces/player';
import { interval } from 'rxjs';
import { IMatchEvents} from 'src/app/interfaces/matchEvents'
import { PlayerService } from 'src/app/services/player.service';
import { letProto } from 'rxjs-compat/operator/let';
import * as moment from 'moment'

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css']
})
export class MatchComponent implements OnInit {

  //example match 
  public match: IMatch[]=[
    {
    "matchDate": "",
    "matchHour": 10,
    "match_id":0,
    "homeTeam":"",
    "homeScore":0,
    "awayTeam":"",
    "awayScore":1,
    "events":[
        ],
    "referee":{"referee_id":1,"name":"","surname":""}
    }
];
  public homePlayers: IPlayer[]=[];
  public awayPlayers: IPlayer[]=[];
  public previousMatches: IMatch[]=[];

  id: any;
  selection: number = 1;
  start: number = 0;
  min: number = 0;


  constructor(private _matchService: MatchService, private route:ActivatedRoute, private _playerService: PlayerService) {
   }

  ngOnInit() {
    console.log("Hello");
    this.id = this.route.snapshot.paramMap.get('id');
    this._matchService.getFullMatchInfo(this.id,"0").subscribe(
      data => {
        this.match = data;
        this.start = data[0].matchHour;
        console.log(this.start);

        this._playerService.getPlayers(this.match[0].homeTeam).subscribe(data => this.homePlayers = data);
        this._playerService.getPlayers(this.match[0].awayTeam).subscribe(data => this.awayPlayers = data);
        this._matchService.getPreviousMatches(this.match[0].homeTeam,this.match[0].awayTeam).subscribe(data => this.previousMatches = data);
        let x = this.calculateMin(this.start);
        console.log(moment().locale('zh-tw').format('l'))
        if (x <= 90 && x>0 && this.match[0].matchDate===moment().locale('zh-tw').format('l')){
          this.min = x;
          this._matchService.getFullMatchInfo(this.id,x.toString()).subscribe(data => this.match = data);
          const obs$ = interval(60000);
          obs$.subscribe(d=>this.refresh(this.start));
        }
        else{ 
          let d2 =  new Date(this.match[0].matchDate);
          let td =  new Date();
          this.min = 0;
          if (d2>td){
            this._matchService.getFullMatchInfo(this.id,"0").subscribe(data => this.match = data);
          }
          else{
            this.min = 90;
            let x = this.calculateMin(this.start);
            this._matchService.getFullMatchInfo(this.id,"90").subscribe(data => this.match = data);
          }
        }
    }
    );
    //console.log(this.match);
    //this.start = this.match[0].matchHour;
    //console.log(this.start);
    //this._playerService.getPlayers(this.match[0].homeTeam).subscribe(data => this.homePlayers = data);
    //this._playerService.getPlayers(this.match[0].awayTeam).subscribe(data => this.awayPlayers = data);
    //console.log(this.homePlayers);
    //this._matchService.getPreviousMatches(this.match[0].homeTeam,this.match[0].awayTeam).subscribe(data => this.previousMatches = data);
    console.log("hello2");
    console.log(this.calculateMin(this.start));
    // if (this.calculateMin(this.start) <= 90){
    //   const obs$ = interval(60000);
    //   obs$.subscribe(d=>this.refresh(this.start));
    // }
    // else{
    //   let x = this.calculateMin(this.start);
    //   this._matchService.getFullMatchInfo(this.id,x.toString()).subscribe(data => this.match = data);
    // }
  }

  calculateMin(h: number): number{
    let date = new Date();
    let date2 = new Date();
    let d;
    date.setHours(h);
    date.setMinutes(0);
    d = date2.getTime() - date.getTime();
    d = d / (60*1000);
    if(d<0){
      return 0;
    }
    return d;
  }

  refresh(h: number){
    let min = this.calculateMin(h);
    this.min= min;
    if(min<= 90){
      this._matchService.getFullMatchInfo(this.id,min.toString()).subscribe(data => this.match = data);
    }
  }

  select(n: number){
    this.selection = n;
  }
  side(h: string, a: string, t: string): boolean{
    if (t===h){
      return true; 
    }
    else return false;
  }

  change_picture(t: string): string {
    if (t==="goal"){
      return "assets/pictures/ball.png";
    }
    if(t==="yellow_card"){
      return "assets/pictures/yellowcard.png";
    }
    if(t==="red_card"){
      return "assets/pictures/redcard.png";
    } 
    return "" 
   }

  displayEvent(e: IMatchEvents): string{
    if (e.eventType==="goal"){
      if (e.team===this.match[0].homeTeam){
        return e.minute + "' "+ e.sname + " " + e.ssurname + "(" +e.aname +" "+ e.asurname + ")" ;
      }
      else
        return "(" +e.aname +" "+ e.asurname + ")" + e.sname + " " + e.ssurname +" "+ e.minute + "' ";
    }
    else{
      if (e.team===this.match[0].homeTeam) return e.minute + "' " + e.name + " " + e.surname;
      else return e.name + " " + e.surname + " "+ e.minute + "' ";
    }
  }
}
