export class GlobalApp {

  constructor() {
  }

  public localStorageItem(id: string): boolean {
    if(localStorage.getItem(id))
      return true;
    return false;
  }


  public getLocalStorageItem(id: string): string {
    return localStorage.getItem(id);

  }

}
