import { User } from './user';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Tasks';
  upperText: string = 'Display uppercase text';
  lowerText: string = 'Display lowercase text';
  percentValue: number = 0.5;
  date: Date = new Date();
  money: number = 598;
  isAdmin = true;
  profile: number = 8;
  user : User = {
    name: 'Jean',
    age: 29
  }
}
