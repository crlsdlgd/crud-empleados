import { Routes } from '@angular/router';
import { ListaEmpleados } from './components/lista-empleados/lista-empleados';

export const routes: Routes = [
  {path: '', redirectTo: 'empleados', pathMatch: 'full'},
  {path: 'empleados', component: ListaEmpleados},
];
