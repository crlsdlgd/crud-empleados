import { Component, inject } from '@angular/core';
import { Empleado } from '../../models/empleado';
import { CommonModule } from '@angular/common';
import { EmpleadoService } from '../../services/empleado.service';
import { catchError, Observable, of } from 'rxjs';

@Component({
  selector: 'app-lista-empleados',
  imports: [CommonModule],
  // standalone: true, Angular toma este valor de standalone: true por defecto, no es necesario especificarlo
  templateUrl: './lista-empleados.html',
  styleUrl: './lista-empleados.css',
})
export class ListaEmpleados {

  
  private empleadoService = inject(EmpleadoService);
  
  empleados$: Observable<Empleado[]> = this.empleadoService
  .getAllEmpleados()
  .pipe(
    catchError((error) => {
      console.error('Error cargando empleados', error);
      return of([]);
    })
  );
}
