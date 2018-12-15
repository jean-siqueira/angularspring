import { Observable } from 'rxjs';
import { Comment } from './comment.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient ) { }

  commentUrl = 'https://jsonplaceholder.typicode.com/comments';

  getComments():Observable<Comment[]>{
    return this.http.get<Comment[]>(this.commentUrl);
  }
}
