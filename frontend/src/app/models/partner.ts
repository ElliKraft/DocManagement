/**
 * Model which holds information about a partner
 */
export class Partner{

  // id of a partner
  id: number;

  // name of the partner
  name: string;

  // phone number of the partner
  phone: string;

  // email of the partner
  email: string;

  // city of the partner
  city: string;

  // postal code of the partner
  postalCode: string;

  // street of the partner
  street: string;

  // house number of the partner
  houseNumber: string;

  // indicator, if the partner ist deleted
  deleted: boolean;

  constructor(id: number, name: string, phone: string, email: string, city: string, postalCode: string, street: string, houseNumber: string, deleted: boolean) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.city = city;
    this.postalCode = postalCode;
    this.street = street;
    this.houseNumber = houseNumber;
    this.deleted = deleted;
  }
}
