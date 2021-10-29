import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LeagueComponent } from './components/league/league.component';
import { MatchComponent } from './components/match/match.component';

const routes: Routes = [
  {
  path: '', component: HomeComponent
  },
  {
  path: 'match/:id', component: MatchComponent
  },
  {
  path: 'league/:league', component: LeagueComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
