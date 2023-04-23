import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from 'src/app/model/course';
import { CourseService } from 'src/app/services/course.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Instructor } from 'src/app/model/instructor';
import { InstructorService } from 'src/app/services/instructor.service';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  courses:Course[] = [];
  instructors: Instructor[] = [];

  searchFormGroup:FormGroup;
  courseFormGroup:FormGroup;
  updateCourseFormGroup:FormGroup;
  submitted:boolean = false;
  defaultInstructor:Instructor;


  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;




  constructor(private fb: FormBuilder,
              private courseService: CourseService,
              private instructorService: InstructorService,
              private route: ActivatedRoute,
              private router: Router,
              private modalService:NgbModal) { }

  ngOnInit(): void {
    this.myForm()
    this.handleSearchCourses();
  }


  myForm(){
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    })

    this.courseFormGroup = this.fb.group({
      courseName: ['',Validators.required],
      courseDuration: ['',Validators.required],
      courseDescription: ['',Validators.required],
      instructor: [null,Validators.required],
    })
  }


  handleSearchCourses(){
    let keyword = this.searchFormGroup.value.keyword;
    this.courseService.searchCourses(keyword, this.thePageNumber - 1, this.thePageSize).subscribe({
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

  doSearch(keyword:string){
    this.courseService.searchCourses(keyword, this.thePageNumber - 1, this.thePageSize).subscribe({
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








  handleDeleteCourse(temp:Course){
    let conf = confirm("Are You Sure!")
    if(!conf) return;
    this.courseService.deleteCourse(temp).subscribe({
      next:response =>{
        this.handleSearchCourses();
      },
      error:err =>{

      }
    })
  }



  getModal(content:any){
    this.submitted = false;
    this.fetchInstructors();
    this.modalService.open(content, {size:'xl'})

  }



  fetchInstructors(){
     this.instructorService.getInstructorList().subscribe({
      next:response =>{
        this.instructors = response;
      },
      error:err =>{

      }
     })
  }


  onCloseModal(modal:any){
    modal.close();
    this.courseFormGroup.reset();
  }


  onSaveCourse(modal:any){
    this.submitted = true;
    if(this.courseFormGroup.invalid) return;

    this.courseService.createCourse(this.courseFormGroup.value).subscribe({
      next:response =>{
         alert("success saving course");
         this.handleSearchCourses();
         this.courseFormGroup.reset();
         this.submitted = false;
         modal.close();
      },
      error:err =>{

      }
    })

  }



  getUpdateModal(temp:Course, updateContent:any){
     this.fetchInstructors();
     this.updateCourseFormGroup = this.fb.group({
      courseId: [temp.courseId,Validators.required],
      courseName: [temp.courseName,Validators.required],
      courseDuration: [temp.courseDuration,Validators.required],
      courseDescription: [temp.courseDescription,Validators.required],
      instructor: [temp.instructor,Validators.required],
     })
     this.defaultInstructor = this.updateCourseFormGroup.controls['instructor'].value;  // or temp.instructor

     this.modalService.open(updateContent, {size:'xl'})
  }


  onUpdateCourse(updateModal:any){
    this.submitted = true;
    if(this.updateCourseFormGroup.invalid) return;

    this.courseService.updateCourse(this.updateCourseFormGroup.value, this.updateCourseFormGroup.value.courseId).subscribe({
      next:response =>{
        alert("success updating course");
        this.handleSearchCourses();
        this.submitted = false;
        updateModal.close();
      },
      error:err =>{

      }
    })

  }




}
