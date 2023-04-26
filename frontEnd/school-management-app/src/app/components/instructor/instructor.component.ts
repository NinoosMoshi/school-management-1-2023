import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Course } from 'src/app/model/course';
import { Instructor } from 'src/app/model/instructor';
import { CourseService } from 'src/app/services/course.service';
import { InstructorService } from 'src/app/services/instructor.service';
import { UserService } from 'src/app/services/user.service';
import { EmailExistsValidator } from 'src/app/validators/email-exists-validator';

@Component({
  selector: 'app-instructor',
  templateUrl: './instructor.component.html',
  styleUrls: ['./instructor.component.css']
})
export class InstructorComponent implements OnInit {


  instructors: Instructor[] = [];
  courses:Course[] = [];

  searchFormGroup:FormGroup;
  instructorFormGroup:FormGroup;
  submitted:boolean = false;
  deleteInstructor:Instructor;
  modalInstructor:Instructor;


  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;


  constructor(private modalService:NgbModal,
              private fb: FormBuilder,
              private instructorService: InstructorService,
              private userService:UserService,
              private courseService:CourseService
              ) { }

  ngOnInit(): void {
    this.myForm();
    this.handleSearchInstructor();
    this.getAllCourses();
  }


  myForm(){
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    })

    this.instructorFormGroup = this.fb.group({
      firstName: ["",Validators.required],
      lastName: ["",Validators.required],
      summary: ["",Validators.required],
      user:this.fb.group({
        email:["",[Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")], [EmailExistsValidator.validate(this.userService)]],
        password: ["",Validators.required]
      })
    })

  }



  handleSearchInstructor(){
    let keyword = this.searchFormGroup.value.keyword;
    this.instructorService.searchInstructors(keyword, this.thePageNumber - 1, this.thePageSize).subscribe({
      next: response =>{
        this.instructors = response.content;
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
    this.instructorService.searchInstructors(keyword, this.thePageNumber - 1, this.thePageSize).subscribe({
      next: response =>{
        this.instructors = response.content;
        this.thePageNumber = response.number + 1;
        this.thePageSize = response.size;
        this.theTotalElements = response.totalElements;
      },
      error: err =>{
        alert('there is an error occure ' + err.message)
      }
    })
  }


  onCloseModal(modal:any){
    modal.close();
    this.instructorFormGroup.reset();
  }



  getCreateInstructorModal(createInstructorContent:any){
    this.submitted = false;
    this.modalService.open(createInstructorContent, {size:'xl'})
  }






  onSaveInstructor(createModal:any){

    console.log(this.instructorFormGroup.value)

    this.submitted = true;
    if(this.instructorFormGroup.invalid) return;

    this.instructorService.createInstructor(this.instructorFormGroup.value).subscribe({
      next:response =>{
        alert("success saving instructor");
        this.handleSearchInstructor();
        this.instructorFormGroup.reset();
        this.submitted = false;
        createModal.close();
     },
     error:err =>{

     }
    })

  }







  getDeleteInstructorModal(temp:Instructor, deleteInstructorContent:any){
    this.deleteInstructor = temp;
    this.modalService.open(deleteInstructorContent, {size:'l'})
  }


  onDeleteInstructor(tempDelete:Instructor,deleteInstructorContent:any){
    this.instructorService.deleteInstructor(tempDelete).subscribe({
      next:response =>{
        this.handleSearchInstructor();
        deleteInstructorContent.close();
      },
      error:err =>{

      }
    })
  }




  getCoursesModal(temp:Instructor,coursesContent:any){
     this.modalInstructor = temp;
     this.handleSearchCourses(temp);
     this.modalService.open(coursesContent, {size:'xl'})
  }


  handleSearchCourses(temp:Instructor){
    this.courseService.getCoursesByInstructor(temp.instructorId, this.thePageNumber - 1, this.thePageSize).subscribe({
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


  getAllCourses(){
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
