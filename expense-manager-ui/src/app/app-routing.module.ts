import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExpenseComponent } from './components/expense/expense.component';
import { LoginComponent } from './components/login/login.component';
import { SavingComponent } from './components/saving/saving.component';
import { AddnewdataComponent } from './components/addnewdata/addnewdata.component';
import { LendingComponent } from './components/lending/lending.component';
import { DebtComponent } from './components/debt/debt.component';
import { RegisterComponent } from './components/register/register.component';
import { ProfileComponent } from './components/profile/profile.component';


const routes: Routes = [
  {path: '', component:LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'expense', component: ExpenseComponent},
  {path: 'saving', component: SavingComponent},
  {path: 'lending', component: LendingComponent},
  {path: 'debt', component: DebtComponent},
  {path: 'addinfo', component: AddnewdataComponent},
  {path: 'profile', component: ProfileComponent},
  {path: '**', component: AddnewdataComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
