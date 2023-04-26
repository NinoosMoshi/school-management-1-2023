import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Student } from '../model/student';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StudentService {



  constructor(private http:HttpClient) { }

  // http://localhost:8082/students/all-students?page=0&size=5
  public getAllStudentsPagination(page:number, size:number):Observable<GetResponse>{
     return this.http.get<GetResponse>(`${environment.backendHost}/students/all-students?page=${page}&size=${size}`).pipe(
      map(response => response)
     )
  }


   // http://localhost:8082/students/search?keyword=co&page=0&size=5
   public searchStudents(keyword:string, page:number, size:number):Observable<GetResponse>{
    return this.http.get<GetResponse>(`${environment.backendHost}/students/search?keyword=${keyword}&page=${page}&size=${size}`).pipe(
      map(response => response)
    )
  }


  // http://localhost:8082/students/delete/{studentId}
  public deleteStudent(student:Student):Observable<any>{
    return this.http.delete<void>(`${environment.backendHost}/students/delete/${student.studentId}`);
  }


   // http://localhost:8082/students/create
   public createStudent(student: Student):Observable<Student>{
    return this.http.post<Student>(`${environment.backendHost}/students/create`,student).pipe(
      map(response => response)
    )
  }





}


interface GetResponse{
  content: Student[],
  totalPages: number,
  totalElements: number,
  size: number,
  number: number
}
