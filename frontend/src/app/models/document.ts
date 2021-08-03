import {UserRole} from './userRole';
import {User} from './user';
import {DocCategory} from './docCategory';
import {Partner} from './partner';

/**
 * Model which holds information about a document
 */
export class Document {

  // id of this document
  id: number;

  // title of this document
  title: string;

  // number of this document
  docNumber: string;

  // conclusion date of the document
  conclusionDate: Date;

  // termination date of the document
  terminationDate: Date;

  // a URL for for image of the document
  imageUrl: string;

  // indicates if the document is deleted
  deleted: boolean;

  // categories of the document
  categories: Array<DocCategory>;

  // partners according to the document
  partners: Array<Partner>;

  // users, who are allowed to process the document
  users: Array<User>;

  constructor(id: number, title: string, docNumber: string, conclusionDate: Date, terminationDate: Date, imageUrl: string, deleted: boolean, categories: Array<DocCategory>, partners: Array<Partner>, users: Array<User>) {
    this.id = id;
    this.title = title;
    this.docNumber = docNumber;
    this.conclusionDate = conclusionDate;
    this.terminationDate = terminationDate;
    this.imageUrl = imageUrl;
    this.deleted = deleted;
    this.categories = categories;
    this.partners = partners;
    this.users = users;
  }
}
