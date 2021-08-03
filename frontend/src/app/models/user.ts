/**
 * Model which holds information about a user
 */
import {UserRole} from './userRole';

export class User {

  // id of this user
  id: number;

  // name of this user
  name: string;

  // surname of this user
  surname: string;

  // company of this user
  company: string;

  // email of this user
  email: string;

  // encrypted password of this user
  password: string;

  // decides if user is deleted
  deleted: boolean;

  // roles of a user
  userRoles: Array<UserRole>;


  constructor(id: number, name: string, surname: string, company: string, email: string, password: string, deleted: boolean, userRoles: Array<UserRole>) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.company = company;
    this.email = email;
    this.password = password;
    this.deleted = deleted;
  }
}
