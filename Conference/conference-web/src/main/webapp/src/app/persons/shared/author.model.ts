import {Person} from "./person.model";
/**
 * Created by CristianCosmin on 31.05.2017.
 */
export class Author extends Person{
  papers: Array<String>;
  constructor(id: number, usern: string, password: string, name: string, affiliation: string, email: string, papers: Array<String>) {
    super(id, usern, password, name, affiliation, email);
    this.papers = papers;
  }
}
