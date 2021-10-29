import { Component, OnInit, Output, EventEmitter } from "@angular/core";
import { Router } from "@angular/router";
import * as moment from 'moment'
import 'moment/locale/pl'


@Component({
  selector: "app-calendar",
  templateUrl: "./calendar.component.html",
  styleUrls: ["./calendar.component.css"],
})
export class CalendarComponent implements OnInit {
  
  @Output() dateEvent = new EventEmitter<string>();

  week: any = ["Pon","Wt","Śr","Czw","Pt","Sob","Nie"];
  monthSelect: any[]=[];
  dateSelect: any;
  dateValue: any;

  constructor(private router:Router) {
  }

  ngOnInit(): void {
    this.getDaysFromDate(moment().locale('pl').format("MM"), 2021)
  }
  

  getDaysFromDate(month: any, year: any) {

    const startDate = moment.utc(`${year}/${month}/01`)
    const endDate = startDate.clone().endOf('month')
    this.dateSelect = startDate;
    this.dateSelect.locale('pl')

    const diffDays = endDate.diff(startDate, 'days', true)
    const numberDays = Math.round(diffDays);

    const arrayDays = Object.keys([...Array(numberDays)]).map((a: any) => {
      a = parseInt(a) + 1;
      const dayObject = moment(`${year}-${month}-${a}`);
      return {
        name: dayObject.format("dddd"),
        value: a,
        indexWeek: dayObject.isoWeekday()
      };
    });

    this.monthSelect = arrayDays;
  }

  changeMonth(flag: any) {
    if (flag < 0) {
      const prevDate = this.dateSelect.clone().subtract(1, "month");
      this.getDaysFromDate(prevDate.locale('pl').format("MM"), prevDate.locale('pl').format("YYYY"));
    } else {
      const nextDate = this.dateSelect.clone().add(1, "month");
      this.getDaysFromDate(nextDate.locale('pl').format("MM"), nextDate.locale('pl').format("YYYY"));
    }
  }

  clickDay(day: any) {
    const monthYear = this.dateSelect.locale('pl').format('YYYY-MM');
    const parse = `${monthYear}-${day.value}`;
    const objectDate = moment(parse);
    this.dateValue = objectDate;
    this.dateEvent.emit(objectDate.format('l'));
    //this.router.navigate(['/day',objectDate.format('l')]);
  }
}