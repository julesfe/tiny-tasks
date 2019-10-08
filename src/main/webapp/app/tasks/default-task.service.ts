import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { BASE_URL } from '../app.tokens';
import { Task } from './task';
import { TaskService } from './task.service';

@Injectable()
export class DefaultTaskService implements TaskService {

  constructor(private http: HttpClient, @Inject(BASE_URL) private baseUrl: string) {
  }

  create(name: string, username: string): Observable<Task> {
    return this.http.post<Task>(this.baseUrl + '/tasks', {name: name, user: {username: username}} as Task);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(this.baseUrl + '/tasks/' + id);
  }

  getAllByUsername(username: string): Observable<Task[]> {
    return this.http.get<Task[]>(this.baseUrl + '/tasks/' + username);
  }
}