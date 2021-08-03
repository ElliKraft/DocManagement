import { Injectable } from '@angular/core';
import {Document} from '../models/document';
import {HttpClient} from '@angular/common/http';
import {NzMessageService} from 'ng-zorro-antd/message';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {
  currentDocument: Document;
  docList: Array<Document>;

  constructor(public http: HttpClient, public message: NzMessageService) {
    this.docList = new Array<Document>();
  }

  /**
   * Compares two documents and return true if they are the same
   * Important: ID will not be compared!
   * @param document first document
   * @param otherDocument second document
   */
  equals(document: Document, otherDocument: Document): boolean{
    return document.title === otherDocument.title
      && document.docNumber === otherDocument.docNumber
      && document.conclusionDate === otherDocument.conclusionDate
      && document.terminationDate === otherDocument.terminationDate
      && document.imageUrl === otherDocument.imageUrl;
  }

  /**
   * Requests the creation of a new document within the backend
   * @param document, the data of a document
   */
  createDocument(document: Document): void {
    this.http.post<Document>(environment.backendUrl + 'documents/', document).subscribe(
      (data: Document) => {
        if (this.equals(document, data)) {
          this.message.success('Ein neues Dokument "' + document.title + '" wurde erfolgreich angelegt.');
        } else {
          this.message.error('Ein Fehler ist aufgetreten. Bitte versuchen Sie es nochmal.');
        }
      }
    );
  }

  /**
   * Edits the given document
   * @param document, the updated document data
   */
  editUser(document: Document): void {
    this.http.put(environment.backendUrl + 'documents/', document).subscribe(
      (data: Document) => {
        if (this.equals(document, data)) {
          this.message.success('Die Änderungen wurden gespeichert');
        } else {
          this.message.error('Ein Fehler ist aufgetreten. Bitte versuchen Sie es nochmal.');
        }
      },
      (error) => {
        console.log(error);
        if (error.status === 404) {
          this.message.error('Der Dokument wurde nicht gefunden.');
        }
      }
    );
  }

  /**
   * Deletes the given document
   * @param document, the document to delete
   */
  deleteUser(document: Document): void {
    this.http.delete(environment.backendUrl + 'documents/' + document.id).subscribe(
      (data: Document) => {
        if (this.equals(document, data)) {
          this.message.success('Das Dokument "' + document.title + '" ist gelöscht');
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
   * Gets a document by the given id
   * @param id document id of the document
   */
  getDocumentById(id: number): void {
    this.http.get<Document>(environment.backendUrl + 'documents/' + id.toString()).subscribe(response => {
      this.currentDocument = response;
      console.log(this.currentDocument);
    }, error => {
      console.log(error);
      this.message.error('Das Dokument wurde nicht gefunden');
    });
  }
}
