import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { IUser } from 'src/app/interfaces/user';
import { LeagueService } from 'src/app/services/league.service';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  status: number = 0;
  user: IUser[]= [];
  userLogin: any;
  message: any;
  

  constructor(private log: LoginService, private league: LeagueService) { 
  }

  login_(login: any, password: any){
    if(password!=="" && login!==""){
      this.message="";
      this.log.loginUser(login,password).subscribe(data=>{
        console.log(data);
        if(data==0){
          this.userLogin=login;
          this.message="Zalogowano"
          this.status=1;
          localStorage.setItem("status","1");
          localStorage.setItem("user",login);
          
          this.log.setUser(login);
          this.log.setIS(1);
          window.location.reload();
        }
        else if(data==1){
          this.status=0;
          localStorage.setItem("status",status);
          this.message="Nieprawidłowy login lub hasło";
        }
        else if(data==2){
          this.status=0;
          localStorage.setItem("status",status);
          this.message="Błąd logowania";
        }
      })
    }
    else{
      this.status=0;
      localStorage.setItem("status",status);
      this.message="Login i hasło nie mogą być puste";
    }
  }
  register(login: any, password: any){
    if(password!=="" && login!==""){
      this.message="";
      this.log.registerUser(login,password).subscribe(data=>{
        if(data==0){
          this.message="Zarejestrowano";
        }
        else if(data==1){
          this.message="Użytkownik o tym loginie już istnieje";
        }
      })
    }
    else{
      this.message="Login i hasło nie mogą być puste";
    }
    
  }

  logout(){
    this.status = 0;
    localStorage.setItem("status","0");
    console.log(this.getStatus(),"status");
    localStorage.setItem("user","");
    localStorage.setItem("status","0");
    localStorage.setItem("ulubione","[]");
    this.log.setIS(0);
    this.log.setUser("");
    this.message="";
    window.location.reload();
  }

  getStatus(): number{
    let x = localStorage.getItem("status");
    let y =Number(x);
    console.log(y);
    return y;
  }
  getUser(): string{
    let x = localStorage.getItem("user");
    let y = String(x);
    return y;
  }
  ngOnInit(): void {
  }

}
