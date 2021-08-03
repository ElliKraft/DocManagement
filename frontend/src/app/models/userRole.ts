import {User} from './user';

/**
 * Model which holds the role constraint of a user
 */
export class UserRole {

  // id of the constraint
  id: number;

  // user of the constraint
  user: User;

  // role of the user in the shop
  role: string;

  constructor(id: number, user: User, role: string) {
    this.id = id;
    this.user = user;
    this.role = role;
  }
}
