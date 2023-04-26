import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Student } from 'src/app/model/student';
import { StudentService } from 'src/app/services/student.service';
import { UserService } from 'src/app/services/user.service';
import { EmailExistsValidator } from 'src/app/validators/email-exists-validator';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {


  students: Student[] = [];

  searchFormGroup:FormGroup;
  studentFormGroup:FormGroup;
  submitted:boolean = false;
  deleteStudent:Student;

  thePageNumber: number = 1;
  thePageSize: number = 5;
  theTotalElements: number = 0;


  constructor(private modalService:NgbModal,
              private fb: FormBuilder,
              private studentService: StudentService,
              private userService:UserService,
              ) { }

  ngOnInit(): void {
    this.myForm();
    this.handleStudentList();
    this.handleSearchStudent();
  }


  myForm(){
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control('')
    })

    this.studentFormGroup = this.fb.group({
      firstName: ["",Validators.required],
      lastName: ["",Validators.required],
      level: ["",Validators.required],
      user:this.fb.group({
        email:["",[Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")], [EmailExistsValidator.validate(this.userService)]],
        password: ["",Validators.required]
      })
    })
  }



  handleStudentList(){
    this.studentService.getAllStudentsPagination(this.thePageNumber - 1, this.thePageSize).subscribe({
      next: response =>{
        this.students = response.content;
        this.thePageNumber = response.number + 1;
        this.thePageSize = response.size;
        this.theTotalElements = response.totalElements;
      },
      error: err =>{
        alert('there is an error occure ' + err.message)
      }
    })
  }


  handleSearchStudent(){
    let keyword = this.searchFormGroup.value.keyword;
    this.studentService.searchStudents(keyword, this.thePageNumber - 1, this.thePageSize).subscribe({
      next: response =>{
        this.students = response.content;
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
    this.studentService.searchStudents(keyword, this.thePageNumber - 1, this.thePageSize).subscribe({
      next: response =>{
        this.students = response.content;
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
    this.studentFormGroup.reset();
  }

  getDeleteStudentModal(temp:Student,deleteStudentContent:any){
    this.deleteStudent = temp;
    this.modalService.open(deleteStudentContent, {size:'l'})
  }

  onDeleteStudent(tempDelete:Student,deleteModal:any){
    this.studentService.deleteStudent(tempDelete).subscribe({
      next:response =>{
        this.handleStudentList();
        deleteModal.close();
      },
      error:err =>{

      }
    })
  }



  getCreateStudentModal(createStudentContent:any){
    this.submitted = false;
    this.modalService.open(createStudentContent, {size:'xl'})
  }

  onSaveStudent(createModal:any){
    this.submitted = true;
    if(this.studentFormGroup.invalid) return;

    this.studentService.createStudent(this.studentFormGroup.value).subscribe({
      next:response =>{
        alert("success saving instructor");
        this.handleStudentList();
        this.studentFormGroup.reset();
        this.submitted = false;
        createModal.close();
     },
     error:err =>{

     }
    })
  }



  }









