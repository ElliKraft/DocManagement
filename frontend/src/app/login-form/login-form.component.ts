import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzModalRef} from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  /**
   * holds login Form
   */
  loginForm!: FormGroup; // ! after loginForm is definite assignment assertion operator

  constructor(private fb: FormBuilder,
              // private authService: AuthentificationService,
              private message: NzMessageService,
              private modal: NzModalRef) { }

  ngOnInit(): void {
  }

}
