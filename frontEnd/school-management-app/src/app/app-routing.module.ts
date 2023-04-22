import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CourseComponent } from './components/courses/course/course.component';
import { StudentComponent } from './components/students/student/student.component';


const routes: Routes = [

  {path:'course', component:CourseComponent},
  {path:'student', component:StudentComponent},
  {path:'course/:keyword', component:CourseComponent},

  {path:'**',redirectTo:'/course',pathMatch:'full'},
  {path:'',redirectTo:'/course',pathMatch:'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
