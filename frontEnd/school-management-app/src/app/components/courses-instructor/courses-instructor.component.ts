import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Course } from 'src/app/model/course';
import { Instructor } from 'src/app/model/instructor';
import { CourseService } from 'src/app/services/course.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-courses-instructor',
  templateUrl: './courses-instructor.component.html',
  styleUrls: ['./courses-instructor.component.css']
})
export class CoursesInstructorComponent implements OnInit {

  instructorId:number;
  currentInstructor:Instructor;
  courses:Course[] = [];
  courseFormGroup:FormGroup;
  submitted:boolean = false;


  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;

  //  let result1 = this.activatedRoute.snapshot.paramMap.has('id');
  //  let categoryId = this.activatedRoute.snapshot.paramMap.get('id');

  constructor(private route:ActivatedRoute,
              private courseService:CourseService,
              private fb: FormBuilder,
              private modalService:NgbModal) { }


  ngOnInit(): void {
    this.instructorId = +this.route.snapshot.paramMap.get('id');
    this.fillCurrentInstructor();
    this.handleSearchInstructorCourses();
  }


  private fillCurrentInstructor(){
     this.currentInstructor = {
      instructorId: this.instructorId,
      firstName: "",
      lastName: "",
      summary: "",
      user:{email:"", password:""}
     }
  }


  handleSearchInstructorCourses(){
    this.courseService.getCoursesByInstructor(this.instructorId, this.thePageNumber - 1, this.thePageSize).subscribe({
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


  onCloseModal(createModal:any){
    createModal.close();
    this.courseFormGroup.reset();
  }


  getCreateModal(createCourseContent:any){
    this.submitted = false;

    this.courseFormGroup = this.fb.group({
      courseName: ['',Validators.required],
      courseDuration: ['',Validators.required],
      courseDescription: ['',Validators.required],
      instructor: [this.currentInstructor,Validators.required],
    })

    this.modalService.open(createCourseContent, {size:'xl'})
  }


  onSaveCourse(createModal:any){
    this.submitted = true;
    if(this.courseFormGroup.invalid) return;

    this.courseService.createCourse(this.courseFormGroup.value).subscribe({
      next:response =>{
         alert("success saving course");
         this.handleSearchInstructorCourses();
         this.courseFormGroup.reset();
         this.submitted = false;
         createModal.close();
      },
      error:err =>{

      }
    })

  }





}
