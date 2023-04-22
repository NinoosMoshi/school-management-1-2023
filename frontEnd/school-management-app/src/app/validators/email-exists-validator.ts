import { AbstractControl, AsyncValidatorFn, ValidationErrors } from '@angular/forms';
import { UserService } from './../services/user.service';
import { Observable, map } from 'rxjs';
export class EmailExistsValidator {

   static validate(userService:UserService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> =>{
      return userService.checkIfEmailExist(control.value).pipe(
        map( (result: boolean) => result ? {emailAlreadyExists: true} : null)
      )
    }
   }

}
