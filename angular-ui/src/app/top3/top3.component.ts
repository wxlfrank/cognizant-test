import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService, Solution, Top3Report } from '../api.service';

@Component({
  selector: 'app-top3',
  templateUrl: './top3.component.html',
  styleUrls: ['./top3.component.scss']
})
export class Top3Component implements OnInit {

  top3: Observable<Top3Report[]>;
  constructor(private apiService: ApiService) {
    this.top3 = this.apiService.getTops();
  }

  ngOnInit() {
  }

}
