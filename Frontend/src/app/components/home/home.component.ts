import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LeagueService } from 'src/app/services/league.service';
import { MatchService } from 'src/app/services/match.service';
import { IMatch} from '../../interfaces/match'
import { ILeague } from '../../interfaces/league';
import { interval } from 'rxjs';
import * as moment from 'moment'
import { DatePipe } from '@angular/common';
import { LoginService } from 'src/app/services/login.service';
import { LikedService } from 'src/app/services/liked.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  matches: IMatch[] = [];
  live: ILeague[] = [];
  today: ILeague[] = [];
  tomorrow: ILeague[] = [];
  liked: ILeague[] = [];
  day: ILeague[] = [];
  selection: number = 2;
  likedmatches: number[]=[];
  xyz: any;

  
  date: string = moment().format('l');
  
  constructor(private _matchSerive: MatchService, private router:Router,
     private _leagueService: LeagueService, private _loginService: LoginService, private _likedService: LikedService) { }

  ngOnInit(): void{
    this._leagueService.getLiveMatches().subscribe(data => {this.live = data; console.log(data);});
    this._leagueService.getMatchesOn(moment().format('l')).subscribe(data => this.today = data);
    let tomorrow  = moment().add(1,'days');
    this._leagueService.getMatchesOn(tomorrow.format('l')).subscribe(data => this.tomorrow = data);
    if(localStorage.getItem("status")==="1"){
      this._leagueService.getLiked(String(localStorage.getItem("user"))).subscribe(data => this.liked = data);
      this._likedService.matches(String(localStorage.getItem("user"))).subscribe(data=> {
        this.likedmatches=data;
        localStorage.setItem("ulubione",data.toString());
      })
    }
    
    const obs2$ = interval(3000);
    const obs$ = interval(60000);
    obs$.subscribe(d=>this.refresh());
    obs2$.subscribe(d=>this.refresh2());
    
  }

  refresh(){
    if (this.selection==1){
      this._leagueService.getLiveMatches().subscribe(data => this.live = data);
    }
  }
  refresh2(){
    if(localStorage.getItem("status")==="0"){
      this.liked = [];
      this.likedmatches = [];
      localStorage.setItem("ulubione","[]");
    }
    else if(localStorage.getItem("status")==="1"){
      this._leagueService.getLiked(String(localStorage.getItem("user"))).subscribe(data => this.liked = data);
      this._likedService.matches(String(localStorage.getItem("user"))).subscribe(data => this.likedmatches=data);
      this.likedmatches = JSON.parse("[" + localStorage.getItem("ulubione") + "]");
    }
  }

  selectLeague(league: string){
    this.router.navigate(['/league',league]).then();
  }
  select(n: number){
    this.selection = n;
    switch(n){
      case 1: this.date = moment().format('l'); break;
      case 2: this.date = moment().format('l'); break;
      case 3: {
        let tomorrow  = moment().add(1,'days');
        this.date = tomorrow.format('l'); break;}
      case 4: this.date = moment().format('l'); break; 
    }
  }

  loadDay(date: string){
    this.selection = 5;
    this.date = date;
    this._leagueService.getMatchesOn(date).subscribe(data => this.day = data);
  }

  selectMatch(id: Number){
    this.router.navigate(['/match',id]).then();
  }
  calculateMin(h: number): number{
    let date = new Date();
    let date2 = new Date();
    let d;
    date.setHours(h);
    date.setMinutes(0);
    d = date2.getTime() - date.getTime();
    d = d / (60*1000);
    return d;
  }

  scoreView(s: number, d: string): string{
    let d1 = new Date();
    let d2 = new Date(d);
    if (d1 > d2){
      return s.toString();
    }
    else{
      return "-";
    }
  }

  getStatus(): number{
    console.log(this._loginService.getIS(),"status--");
    return Number(localStorage.getItem("status"));
  }
  isLiked(id:number): number{
    if(this.likedmatches.indexOf(id)>-1){
      return 1;
    }
    else {
      return 0;
    }
  }
  changeStar(id: number){
    console.log("Zmiana gwiazdy");

    if(this.likedmatches.indexOf(id)>-1){
      console.log("Usuwanie meczu", id,String(localStorage.getItem("user")));
      this._likedService.removeMatch(String(localStorage.getItem("user")),id).subscribe(data=>this.xyz=data);
      this._leagueService.getLiked(String(localStorage.getItem("user"))).subscribe(data => {
        this.liked=data;
        localStorage.setItem("ulubione",data.toString());
      });
      this._likedService.matches(String(localStorage.getItem("user"))).subscribe(data=> this.likedmatches=data);
      this.likedmatches = JSON.parse("[" + localStorage.getItem("ulubione") + "]");
    }
    else{
      console.log("Dodawanie meczu", id,String(localStorage.getItem("user")));
      this._likedService.addMatch(String(localStorage.getItem("user")),id).subscribe(data=>this.xyz=data);
      this._leagueService.getLiked(String(localStorage.getItem("user"))).subscribe(data => {
        this.liked=data;
        localStorage.setItem("ulubione",data.toString());
        console.log(data)});
      this._likedService.matches(String(localStorage.getItem("user"))).subscribe(data=> {
        this.likedmatches=data;
      });
      this.likedmatches = JSON.parse("[" + localStorage.getItem("ulubione") + "]");
    }
  }
  star(id: number):string{
    if (this.isLiked(id)==1){
      return this.getStar2();
    }else{
      return this.getStar1();
    }
    
  }
  getStar1(): string{
    //console.log("assets/pictures/star.png",this._loginService.getUser());
    return "assets/pictures/star.png";
  }
  getStar2():string{
    //console.log("assets/pictures/goldstar.png",this._loginService.getUser());
    return "assets/pictures/goldstar.png";
  }
}
