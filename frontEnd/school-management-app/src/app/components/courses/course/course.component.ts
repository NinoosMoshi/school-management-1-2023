import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from 'src/app/model/course';
import { CourseService } from 'src/app/services/course.service';
import { BehaviorSubject, Subject } from 'rxjs';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {


  courses: Course[] = [];
  selectedCourse:Course = new Course();
  selectedCourseEdit:Course = new Course();


  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;



  constructor(private courseService: CourseService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() =>{
      this.listCourses();
    })

  }

  listCourses(){
      this.handleListCourses();
    }


  handleListCourses(){
    this.courseService.getCourseList(this.thePageNumber - 1, this.thePageSize).subscribe({
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














}
