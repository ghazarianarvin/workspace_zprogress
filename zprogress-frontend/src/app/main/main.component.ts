import { Component, OnInit } from '@angular/core';
import {MainService} from '../shared/MainService';
import {AuthenticationService} from '../shared/AuthenticationService';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor(private mainService: MainService,
              private authenticationService: AuthenticationService,
              private router: Router) { }

  ngOnInit() {
    this.mainService.callBase();
  }

  logout() {
    console.log('logout');
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

}
