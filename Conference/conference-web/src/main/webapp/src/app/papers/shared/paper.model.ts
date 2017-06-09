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
  conferenceId: number;
  authorsUsername: Array<string>;
  reviewersUsername: Array<string>;
  grade: number;

  constructor(id: number, title: string, abstractText: string, keywords: string, topics: string, sessionId: number, authorsUsername: Array<string>, reviewersUsername: Array<string>, grade: number, conferenceId: number) {
    this.id = id;
    this.title = title;
    this.abstractText = abstractText;
    this.keywords = keywords;
    this.topics = topics;
    this.sessionId = sessionId;
    this.authorsUsername = authorsUsername;
    this.reviewersUsername = reviewersUsername;
    this.grade = grade;
    this.conferenceId = conferenceId;;
  }
}
