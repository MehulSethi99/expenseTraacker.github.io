import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ExpenseComponent } from './components/expense/expense.component';
import { LoginComponent } from './components/login/login.component';
import { AuthService } from './services/auth.service';
import { AddnewdataComponent } from './components/addnewdata/addnewdata.component';
import { LendingComponent } from './components/lending/lending.component';
import { SavingComponent } from './components/saving/saving.component';
import { DebtComponent } from './components/debt/debt.component';
import { LongPress } from './long-press';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { ProfileComponent } from './components/profile/profile.component';
import { RegisterComponent } from './components/register/register.component';
import { AlertComponent } from './components/alert/alert.component';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ExpenseComponent,
    LoginComponent,
    AddnewdataComponent,
    LendingComponent,
    SavingComponent,
    DebtComponent,
    LongPress,
    ProfileComponent,
    RegisterComponent,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
