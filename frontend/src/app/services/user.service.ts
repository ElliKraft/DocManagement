import { Injectable } from '@angular/core';
import {User} from '../models/user';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd/message';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  currentUser: User;
  userList: Array<User>;

  /**
   * indicates if the data is loaded
   */
  dataLoaded: boolean;

  /**
   * Observable to be able to subscribe on changes in the user-list
   */
  observableDataLoaded: Subject<boolean>;


  constructor(public http: HttpClient, public router: Router, public message: NzMessageService) {
    this.userList = new Array<User>();
    this.dataLoaded = false;
    this.observableDataLoaded = new Subject<boolean>();
    this.observableDataLoaded.next(false);
  }

  /**
   * Refreshes the data from the backend
   * @param shopId from the shop
   */
/*  public refreshData(shopId: number): void {
    this.dataLoaded = false;
    this.observableDataLoaded.next(false);
    this.updateSupplierArray(shopId);
  }
*/
  /**
   * Compares two users and return true if they are the same
   * Important: ID will not be compared!
   * @param user first user
   * @param otherUser second user
   */
  equals(user: User, otherUser: User): boolean{
    return user.name === otherUser.name
      && user.surname === otherUser.surname
      && user.company === otherUser.company
      && user.email === otherUser.email
      && user.password === otherUser.password;
  }

  /**
   * Requests the creation of a new user within the backend
   * @param user, the data of the users
   */
  createUser(user: User): void {
    this.http.post<User>(environment.backendUrl + 'users/', user).subscribe(
      (data: User) => {
        if (this.equals(user, data)) {
          this.message.success('Ein neuer Benutzer "' + user.name + '" wurde erfolgreich angelegt.');
        } else {
          this.message.error('Ein Fehler ist aufgetreten. Bitte versuchen Sie es nochmal.');
        }
      },
      (error) => {
        console.log(error);
        if (error.status === 200) {
          this.message.error('Ein Benutzer mit der E-Mail Adresse bereits existiert.');
        }
      }
    );
  }

  /**
   * Edits the given user
   * @param user, the updated user data
   */
  editUser(user: User): void {
    this.http.put(environment.backendUrl + 'users/', user).subscribe(
      (data: User) => {
        if (this.equals(user, data)) {
          this.message.success('Die Änderungen wurden gespeichert');
        } else {
          this.message.error('Ein Fehler ist aufgetreten. Bitte versuchen Sie es nochmal.');
        }
      },
      (error) => {
        console.log(error);
        if (error.status === 404) {
          this.message.error('Der Benutzer wurde nicht gefunden.');
        }
      }
    );
  }

  /**
   * Deletes the given user
   * @param user, the user to delete
   */
  deleteUser(user: User): void {
    this.http.delete(environment.backendUrl + 'users/' + user.id).subscribe(
      (data: User) => {
        if (this.equals(user, data)) {
          this.message.success('Der Benutzer "' + user.name + user.surname + '" ist gelöscht');
        } else {
          this.message.error('Ein Fehler ist aufgetreten. Bitte versuchen Sie später nochmal');
        }
      },
      (error) => {
        console.log(error);
        if (error.status === 404) {
          this.message.error('Der Benutzer wurde nicht gefunden');
        }
      }
    );
  }


  /**
   * Gets the user by the given id
   * @param id userid of the current user
   */
  getUserById(id: number): void {
    this.http.get<User>(environment.backendUrl + 'users/' + id.toString()).subscribe(response => {
      this.currentUser = response;
      console.log(this.currentUser);
    }, error => {
      console.log(error);
      this.message.error('Der Benutzer wurde nicht gefunden');
    });
  }

}
