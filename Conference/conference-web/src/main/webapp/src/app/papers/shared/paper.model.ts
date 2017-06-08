/**
 * Created by Adriana on 5/4/2017.
 */
export class Paper {
  id: number;
  title: string;
  abstractText: string;
  keywords: string;
  topics: string;
  sessionId: number;
  authorsUsername: Array<string>;
  reviewersUsername: Array<string>;
  constructor(title: string, abstractText: string, keywords: string, topics: string, authorsUsername: Array<string>, sessionId: number, reviewersUsername: Array<string>){
    this.title = title;
    this.abstractText = abstractText;
    this.keywords = keywords;
    this.topics = topics;
    this.authorsUsername = authorsUsername;
    this.sessionId = sessionId;
    this.reviewersUsername = reviewersUsername;
  }
}
