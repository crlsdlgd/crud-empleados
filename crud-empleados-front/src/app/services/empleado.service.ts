import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Empleado } from '../models/empleado';

@Injectable({
  providedIn: 'root',
})
export class EmpleadoService {
  //Esta URL obtiene el listado de todos los empleados en el backend
  private baseUrl = 'http://localhost:8090/api/v1/empleado';
  
  constructor(private httpClient: HttpClient) {}

  //Este metodo nos sirve para obtener los empleados
  getAllEmpleados():Observable<Empleado[]> {
    return this.httpClient.get<Empleado[]>(`${this.baseUrl}/`);
  }
}
