import { Document } from './document';

/**
 * Model which holds information about the category of a document constraint
 */
export class DocCategory{

  // is of the constraint
  id: number;

  // document of the constraint
  document: Document;

  // category of the constraint
  category: string;

  constructor(id: number, document: Document, category: string) {
    this.id = id;
    this.document = document;
    this.category = category;
  }

}
