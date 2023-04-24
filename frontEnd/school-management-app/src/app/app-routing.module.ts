import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseComponent } from './components/courses/course/course.component';
import { StudentComponent } from './components/students/student/student.component';
import { InstructorComponent } from './components/instructor/instructor.component';


const routes: Routes = [

  {path:'student', component:StudentComponent},

  {path:'course', component:CourseComponent},
  {path:'course/:keyword', component:CourseComponent},

  {path:'instructor', component:InstructorComponent},
  {path:'instructor/:keyword', component:InstructorComponent},

  {path:'**',redirectTo:'/course',pathMatch:'full'},
  {path:'',redirectTo:'/course',pathMatch:'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
