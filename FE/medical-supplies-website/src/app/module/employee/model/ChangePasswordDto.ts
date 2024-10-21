export class ChangePasswordDto {
  private username?: string;
  private presentPassword?: string;
  private confirmPassword?: string;


  constructor(username: string, presentPassword: string, confirmPassword: string) {
    this.username = username;
    this.presentPassword = presentPassword;
    this.confirmPassword = confirmPassword;
  }

}
