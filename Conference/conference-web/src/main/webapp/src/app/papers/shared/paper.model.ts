/**
 * Created by Adriana on 5/4/2017.
 */
export class Paper {
  id: number;
  title: string;
  author: string;
  content: string;
  constructor(title: string, author: string, content: string){
    this.title = title;
    this.author = author;
    this.content = content;
  }
}
