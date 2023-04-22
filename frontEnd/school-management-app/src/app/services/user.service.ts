import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators'
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

   // http://localhost:8082/users?email=${email}
   public checkIfEmailExist(email:string):Observable<boolean>{
    return this.http.get<boolean>(`${environment.backendHost}/users?email=${email}`).pipe(
      map(response => response)
    )
  }


}
