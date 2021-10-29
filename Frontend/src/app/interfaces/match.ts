import { IGoal } from "./goal";
import { IMatchEvents } from "./matchEvents";
import { IPlayer } from "./player";
import { IRedCard } from "./redCard";
import { IRefereree } from "./referee";
import { IYellowCard } from "./yellowCard";

export interface IMatch {
    matchDate: string,
    matchHour: number,
    match_id: number,
    homeTeam: string,
    homeScore: number,
    awayTeam: string,
    awayScore: number,
    events: IMatchEvents[],
    referee: IRefereree,
}