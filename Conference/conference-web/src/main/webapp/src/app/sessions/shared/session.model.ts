export class Session{
  id: number;
  date: string;
  conferenceId: number;
  chairId: number;
  constructor(date: string, conferenceId: number, chairId: number){
    this.date = date;
    this.conferenceId = conferenceId;
    this.chairId = chairId;
  }
}
