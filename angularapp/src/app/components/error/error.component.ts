import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  errorCode: string = '404';
  errorMessage: string = 'Page Not Found';

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['code']) {
        this.errorCode = params['code'];
      }
      if (params['message']) {
        this.errorMessage = params['message'];
      }
    });
  }
}
