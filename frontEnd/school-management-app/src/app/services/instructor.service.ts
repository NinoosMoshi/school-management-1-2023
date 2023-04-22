import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Instructor } from '../model/instructor';
import { map } from 'rxjs/operators'
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InstructorService {

  constructor(private http:HttpClient) { }


  // http://localhost:8082/instructors/all-instructors
  public getAllInstructorsPagination(page:number, size:number):Observable<GetResponse>{
    return this.http.get<GetResponse>(`${environment.backendHost}/instructors/all-instructors?page=${page}&size=${size}`).pipe(
      map(response => response)
    )
  }


  // http://localhost:8082/instructors/all
  public getInstructorList():Observable<Instructor[]>{
    return this.http.get<Instructor[]>(`${environment.backendHost}/instructors/all`).pipe(
      map(response => response)
    )
  }


   // http://localhost:8082/instructors/search?keyword=co&page=0&size=5
   public searchInstructors(keyword:string, page:number, size:number):Observable<GetResponse>{
    return this.http.get<GetResponse>(`${environment.backendHost}/instructors/search?keyword=${keyword}&page=${page}&size=${size}`).pipe(
      map(response => response)
    )
  }


  // http://localhost:8082/instructors/delete/{instructorId}
  public deleteInstructor(instructor:Instructor):Observable<any>{
    return this.http.delete<void>(`${environment.backendHost}/instructors/delete/${instructor.instructorId}`);
  }


  // http://localhost:8082/instructors/create
  public createInstructor(instructor: Instructor):Observable<Instructor>{
    return this.http.post<Instructor>(`${environment.backendHost}/instructors/create`,instructor).pipe(
      map(response => response)
    )
  }



}


interface GetResponse{
  content: Instructor[],
  totalPages: number,
  totalElements: number,
  size: number,
  number: number
}
