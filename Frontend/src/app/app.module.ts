import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';
import { MatchComponent } from './components/match/match.component'
import { MatchService } from './services/match.service';
import { LoginComponent } from './components/login/login.component';
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { CalendarComponent } from './components/calendar/calendar.component';
import { LeagueComponent } from './components/league/league.component';
import { LeagueService } from './services/league.service';
import { PlayerService } from './services/player.service';
import { TableService } from './services/table.service';
import { LeaguelistComponent } from './components/leaguelist/leaguelist.component';
import { LoginService } from './services/login.service';
import { LikedService } from './services/liked.service';

@NgModule({
  declarations: [
    AppComponent,
    MatchComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    CalendarComponent,
    LeagueComponent,
    LeaguelistComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    MatchService,
    LeagueService,
    PlayerService,
    TableService,
    LeaguelistComponent,
    LoginService,
    LikedService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
