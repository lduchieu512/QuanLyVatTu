import {Position} from './Position';
import {Account} from '../../account/model/Account';

export interface EmployeeInfo {
  employeeCode?: string;
  employeeName?: string;
  email?: string;
  phone?: string;
  employeeAddress?: string;
  gender?: boolean;
  idCard?: string;
  dateOfBirth?: Date;
  employeeImg?: string;
  position?: Position | string;
  account?: Account;
}
