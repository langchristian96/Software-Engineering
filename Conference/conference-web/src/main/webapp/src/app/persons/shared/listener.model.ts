import {Person} from "./person.model";


export class Listener extends Person{
  sessions: Array<number>;
  constructor(id: number, usern: string, password: string, name: string, affiliation: string, email: string, sessions: Array<number>) {
    super(id, usern, password, name, affiliation, email);
    this.sessions = sessions;
  }
}
