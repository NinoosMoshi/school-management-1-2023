import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Course } from 'src/app/model/course';
import { CourseService } from 'src/app/services/course.service';

@Component({
  selector: 'app-courses-student',
  templateUrl: './courses-student.component.html',
  styleUrls: ['./courses-student.component.css']
})
export class CoursesStudentComponent implements OnInit {

  courses:Course[] = [];
  coursesNonEnrolled:Course[] = [];
  studentId:number;


  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;

  thePageNumberNonEnrolled: number = 1;
  thePageSizeNonEnrolled: number = 5;
  theTotalElementsNonEnrolled: number = 0;


  constructor(private route:ActivatedRoute,
              private courseService:CourseService
    ) { }

  ngOnInit(): void {
    // this.studentId = this.route.snapshot.params['id'];
    this.studentId = +this.route.snapshot.paramMap.get('id');
    this.handleSearchStudentCourses();
    this.handleSearchNonEnrolledInCourses();
  }


  handleSearchStudentCourses(){
    this.courseService.getCoursesByStudent(this.studentId, this.thePageNumber - 1, this.thePageSize).subscribe({
      next: response =>{
        this.courses = response.content;
        this.thePageNumber = response.number + 1;
        this.thePageSize = response.size;
        this.theTotalElements = response.totalElements;
      },
      error: err =>{
        alert('there is an error occure ' + err.message)
      }
    })
  }

  handleSearchNonEnrolledInCourses(){
    this.courseService.getNonEnrolledCoursesByStudent(this.studentId, this.thePageNumberNonEnrolled - 1, this.thePageSizeNonEnrolled).subscribe({
      next: response =>{
        this.coursesNonEnrolled = response.content;
        this.thePageNumberNonEnrolled = response.number + 1;
        this.thePageSizeNonEnrolled = response.size;
        this.theTotalElementsNonEnrolled = response.totalElements;
      },
      error: err =>{
        alert('there is an error occure ' + err.message)
      }
    })

  }



  enrollIn(temp:Course){
    this.courseService.getEnrollStudentInCourse(temp.courseId, this.studentId).subscribe({
      next: () =>{
        this.handleSearchStudentCourses();
        this.handleSearchNonEnrolledInCourses();
      },
      error: err =>{
         console.log(err.message)
      }
    })
  }



}
