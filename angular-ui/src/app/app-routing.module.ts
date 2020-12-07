import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SolveComponent } from './solve/solve.component';
import { Top3Component } from './top3/top3.component';


const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: "solve"},
  {path: "solve", component: SolveComponent},
  {path: "top3", component: Top3Component}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
