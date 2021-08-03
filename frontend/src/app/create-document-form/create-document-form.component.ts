import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NzMessageService} from 'ng-zorro-antd/message';
import {DocumentService} from '../services/document.service';
import {NzModalRef} from 'ng-zorro-antd/modal';


@Component({
  selector: 'app-create-document-form',
  templateUrl: './create-document-form.component.html',
  styleUrls: ['./create-document-form.component.css']
})
export class CreateDocumentFormComponent implements OnInit {

  /**
   * The document form group, not null and not undefined
   */
  documentForm: FormGroup;

  constructor(private fb: FormBuilder,
              private message: NzMessageService,
              public documentService: DocumentService,
              private modalRef: NzModalRef) {}

  ngOnInit(): void {
    this.documentForm = this.fb.group({
      title: [null, Validators.required],
      docNumber: [null],
      conclusionDate: [null],
      terminationDate: [null],
      imageUrl: [null]
    });
  }

  /**
   * Submit the form
   */
  submitForm(): void {
    if (!this.documentForm.valid) {
      this.message.error('Bitte überprüfen Sie Ihre Angaben.');
    } else {
      const docData = this.documentForm.value;
      // docData.userId = this.userId; get an id of the current user of the session
      this.documentService.createDocument(docData);
      this.documentForm.reset();
      this.modalRef.destroy();
    }
  }

  /**
   * Destroys the modal (floating form for creating a document)
   */
  destroyModal(): void {
    this.modalRef.destroy();
  }
}
