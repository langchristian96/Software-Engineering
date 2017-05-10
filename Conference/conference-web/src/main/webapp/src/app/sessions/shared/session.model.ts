export class Session{
  id: number;
  date: string;
  conferenceId: number;
  sessionChairId: number;
  constructor(date: string, conferenceId: number, sessionChairId: number){
    this.date = date;
    this.conferenceId = conferenceId;
    this.sessionChairId = sessionChairId;
  }
}
