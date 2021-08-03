import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CommonModule} from '@angular/common';
import {CreateDocumentFormComponent} from './create-document-form/create-document-form.component';

// place more specific routes above less specific routes
const routes: Routes = [
  {path: 'dashboard', component: DashboardComponent},
  {path: 'new-document', component: CreateDocumentFormComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    CommonModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule{}
