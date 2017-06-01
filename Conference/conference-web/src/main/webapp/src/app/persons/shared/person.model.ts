export class Person {
  id: number;
  usern: string;
  password: string;
  name: string;
  affiliation: string;
  email: string;

  constructor(id: number, usern: string, password: string, name: string, affiliation: string, email: string) {
    this.id = id;
    this.usern = usern;
    this.password = password;
    this.name = name;
    this.affiliation = affiliation;
    this.email = email;
  }
}
