import { Instructor } from "./instructor";

export class Course {

  courseId!: number;
  courseName!: string;
  courseDuration!: string;
  courseDescription!: string;
  instructor!: Instructor;

}
