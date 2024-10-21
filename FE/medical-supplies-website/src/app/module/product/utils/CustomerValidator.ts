import {Directive} from '@angular/core';
import {NG_VALIDATORS, Validator, FormControl, AbstractControl, ValidationErrors} from '@angular/forms';

export function checkFile(abstractControl: AbstractControl): ValidationErrors | null {
  const check = abstractControl.value;
  return !check.trim().toLowerCase().endsWith('.jpg') &&
  !check.trim().toLowerCase().endsWith('.png') ?
    {filewrong: true} : null;
}

@Directive({
  // tslint:disable-next-line:directive-selector
  selector: '[requiredFile]',
  providers: [
    { provide: NG_VALIDATORS, useExisting: FileValidator, multi: true },
  ]
})

export class FileValidator implements Validator {

  value: any;
  static validate(c: FormControl): {[key: string]: any} {
    return c.value == null || c.value.length === 0 ? { required : true} : null;
  }

  validate(c: FormControl): {[key: string]: any} {
    return FileValidator.validate(c);
  }
  onChange = (_) => {};
  onTouched = () => {};

  writeValue(value) {}
  registerOnChange(fn: any) { this.onChange = fn; }
  registerOnTouched(fn: any) { this.onTouched = fn; }
}
