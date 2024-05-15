import { Component, OnInit } from '@angular/core';
import { Subscription, Observable } from 'rxjs';
import { AlertService } from '../../services/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css']
})
export class AlertComponent implements OnInit {

  private subscription: Subscription;
  message: any;
  private timer: Observable<any>;

  constructor(private alertService: AlertService) { 
    this.subscription = alertService.getMessage().subscribe(message => {
      this.message = message;
    });
  }

  ngOnInit() {    
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
  closeMessage() {
    this.alertService.clearAlertMessage();    
  } 

}
