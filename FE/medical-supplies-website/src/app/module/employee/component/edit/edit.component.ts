import {Component, OnInit} from '@angular/core';
import {Position} from '../../model/Position';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {Employee} from '../../model/Employee';
import {EmployeeService} from '../../service/employee.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AngularFireStorage} from '@angular/fire/storage';
import {Title} from '@angular/platform-browser';
import {DatePipe, formatDate} from '@angular/common';
import {finalize} from 'rxjs/operators';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
  existPhone: boolean;
  existIdCard: boolean;
  existEmail: boolean;


  constructor(private employeeService: EmployeeService, private router: Router,
              private storage: AngularFireStorage, private title: Title, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.paramMap.subscribe(next => {
      const id = next.get('id');
      if (id != null) {
        this.employeeService.getEmployeeById(+id).subscribe(next1 => {
          this.employeeEdit = next1;
          this.imgSrc = next1.employeeImg;
          this.employeeEdit.dateOfBirth = new DatePipe('en-US').transform(new Date(this.employeeEdit.dateOfBirth), 'yyyy-MM-dd');
          this.getFormEdit();
        }, error => {
          console.log(error);
        }, () => {
          this.ngOnInit();
          this.dob = new DatePipe('en-US').transform(new Date(this.employeeEdit.dateOfBirth), 'yyyy-MM-dd');
        });
      }
    });
  }

  dob: string;
  positions: Position[] = [];
  employeeEditForm: FormGroup;
  employeeEdit: Employee;
  inputImage: any = null;
  maxSize = false;
  employees: Employee[] = [];
  employeeCode: string;
  imgSrc: string;
  selectedValue: number;
  regexname: string = '^[AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+ [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+(?: [AÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬBCDĐEÈẺẼÉẸÊỀỂỄẾỆFGHIÌỈĨÍỊJKLMNOÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢPQRSTUÙỦŨÚỤƯỪỬỮỨỰVWXYỲỶỸÝỴZ][aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]*)*';

  ngOnInit(): void {
    this.employeeService.getAllPos().subscribe(next => {
      this.positions = next;
    });
  }

  onItemChange(selectedValue: number) {
    this.selectedValue = selectedValue;
    console.log(' Value is : ', selectedValue);
    return this.selectedValue;
  }

  compareFn(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  getFormEdit() {
    this.employeeEditForm = new FormGroup({
      employeeCode: new FormControl(this.employeeEdit.employeeCode, [Validators.required]),
      employeeName: new FormControl(this.employeeEdit.employeeName, [Validators.required, Validators.minLength(5),
        Validators.maxLength(50), Validators.pattern(this.regexname)]),
      email: new FormControl(this.employeeEdit.email, [Validators.required, Validators.minLength(6),
        Validators.maxLength(50), Validators.pattern('^\\s*[a-zA-Z0-9_.+-]+@gmail.com+\\s*$')]),
      phone: new FormControl(this.employeeEdit.phone, [Validators.required]),
      employeeAddress: new FormControl(this.employeeEdit.employeeAddress, [Validators.required, Validators.maxLength(100)]),
      gender: new FormControl(this.employeeEdit.gender ? 'true' : 'false', [Validators.required]),
      idCard: new FormControl(this.employeeEdit.idCard, [Validators.required, Validators.pattern('^\\s*\\d{12}\\s*$')]),
      dateOfBirth: new FormControl(this.employeeEdit.dateOfBirth, [Validators.required, this.isOver18, this.isOver50]),
      employeeImg: new FormControl(this.employeeEdit.employeeImg),
      position: new FormControl(this.employeeEdit.position),
    });
  }

  selectImg(event: any) {
    this.inputImage = event.target.files[0];
    if (this.inputImage.size > 1048576 && this.inputImage != null) {
      this.maxSize = true;
      event.target.value = null;
      this.inputImage = null;
    } else if (this.inputImage) {
      this.maxSize = false;
      const reader = new FileReader();
      reader.readAsDataURL(this.inputImage);
      reader.onload = (e: any) => {
        this.imgSrc = e.target.result;
      };
    }
  }

  isOver50(control: AbstractControl): any {
    const dob = new Date(control.value);
    const currentDate = new Date();
    let age = currentDate.getFullYear() - dob.getFullYear();
    const monthDiff = currentDate.getMonth() - dob.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && currentDate.getDate() < dob.getDate())) {
      age--;
    }
    if (age > 50) {
      return {isOver50: true};
    }
  }

  isOver18(control: AbstractControl): any {
    const dob = new Date(control.value);
    const currentDate = new Date();
    let age = currentDate.getFullYear() - dob.getFullYear();
    const monthDiff = currentDate.getMonth() - dob.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && currentDate.getDate() < dob.getDate())) {
      age--;
    }
    if (age < 18) {
      return {isOver18: true};
    }
  }

  updateEmployee() {
    if (this.inputImage != null && this.maxSize !== true) {
      const nameImg = formatDate(new Date(), 'dd-MM-yyyy_hh:mm:ss:a_', 'en-US') + this.inputImage.name;
      const fileRef = this.storage.ref(nameImg);
      this.storage.upload(nameImg, this.inputImage).snapshotChanges().pipe(
        finalize(() => {
          fileRef.getDownloadURL().subscribe(url => {
            this.employeeEditForm.patchValue({employeeImg: url});
            this.employeeService.updateEmployee(this.employeeEditForm.value, this.employeeEdit.employeeId).subscribe(next => {
              this.router.navigateByUrl('employees');
              const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                  toast.addEventListener('mouseenter', Swal.stopTimer);
                  toast.addEventListener('mouseleave', Swal.resumeTimer);
                }
              });
              Toast.fire({
                icon: 'success',
                title: 'Cập nhật thông tin nhân viên thành công!'
              });
            }, error => {
              console.log(error);
              if (error.error.duplicateEmail) {
                this.existEmail = true;
              } else this.existEmail = false;
              if (error.error.duplicatePhone) {
                this.existPhone = true;
              } else this.existPhone = false;
              if (error.error.duplicateIdCard) {
                this.existIdCard = true;
              } else this.existIdCard = false;
            });
          });
        })
      ).subscribe();
    } else {
      this.employeeService.updateEmployee(this.employeeEditForm.value, this.employeeEdit.employeeId).subscribe(next => {
        this.router.navigateByUrl('/employees').then(() => {
          Swal.fire('Thành công',
            'Đã chỉnh sửa thông tin nhân viên thành công',
            'success');
        });
      }, error => {
        console.log(error);
        if (error.error.duplicateEmail) {
          this.existEmail = true;
        } else this.existEmail = false;
        if (error.error.duplicatePhone) {
          this.existPhone = true;
        } else this.existPhone = false;
        if (error.error.duplicateIdCard) {
          this.existIdCard = true;
        } else this.existIdCard = false;
      });
    }
  }

  setFlagEmail() {
    this.existEmail = false;
  }

  setFlagPhone() {
    this.existPhone = false;
  }

  setFlagIdCard() {
    this.existIdCard = false;
  }
}
