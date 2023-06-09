import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseComponent } from './components/courses/course/course.component';
import { StudentComponent } from './components/students/student/student.component';
import { InstructorComponent } from './components/instructor/instructor.component';
import { CoursesInstructorComponent } from './components/courses-instructor/courses-instructor.component';
import { CoursesStudentComponent } from './components/courses-student/courses-student.component';


const routes: Routes = [

  {path:'student', component:StudentComponent},
  {path:'course', component:CourseComponent},
  {path:'instructor', component:InstructorComponent},
  {path:'courses-instructor/:id', component:CoursesInstructorComponent},
  {path:'courses-student/:id', component:CoursesStudentComponent},

  {path:'**',redirectTo:'/course',pathMatch:'full'},
  {path:'',redirectTo:'/course',pathMatch:'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
