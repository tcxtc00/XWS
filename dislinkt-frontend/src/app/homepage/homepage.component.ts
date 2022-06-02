import { Component, OnInit } from '@angular/core';
import { skipWhile, take } from 'rxjs/operators';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  constructor(
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    console.log("Uspesno");
    this.authService.token$.pipe(
      skipWhile(value => !value),
      take(1))
      .subscribe(value => console.log(value));
  }

  logout(): void {
    this.authService.logout();
  }

}
