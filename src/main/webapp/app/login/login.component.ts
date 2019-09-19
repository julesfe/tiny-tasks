import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'login-component',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  private model: any = {};

  @Input() form: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  @Output() eventEmitter = new EventEmitter();

  constructor(private route: ActivatedRoute, private router: Router, private httpClient: HttpClient) {
  }

  public ngOnInit(): void {
    sessionStorage.setItem('token', '');
  }

  public submit() {
    if (this.form.valid) {
      this.login();
    }
  }

  public login() {
    const url = 'http://localhost:8080/login'
    this.httpClient.post<Observable<boolean>>(url, this.form.value).subscribe(loginSuccess => {
      if (loginSuccess) {
        sessionStorage.setItem('token', this.base64EncodeUsernameAndPassword());
        this.router.navigate(['/tasks']);
      } else {
        alert('Login failed.');
      }
    });
  }

  private base64EncodeUsernameAndPassword() {
    return btoa(this.model.username + ':' + this.model.password);
  }
}