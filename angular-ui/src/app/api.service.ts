import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, of } from "rxjs";
import { catchError } from "rxjs/operators";

const API_HOST = "http://localhost:8080/spring-api/";
@Injectable({
  providedIn: "root",
})
export class ApiService {
  constructor(private httpService: HttpClient) {}

  saveSolution(solution: Solution, f: Function) {
    this.httpService
      .get<CompileResult>("https://rextester.com/rundotnet/api", {
        params: {
          LanguageChoice: "1",
          Program: solution.code,
        },
      })
      .pipe(
        catchError((e) => {
          f("error happens when sending request to https://rextester.com/rundotnet/api " + e);
          return of(null);
        })
      )
      .subscribe((r) => {
        if (r != null) {
          solution.success = true;//r.Errors == null || r.Errors.trim().length == 0;
          this.httpService
            .post<Solution>(API_HOST + "solution", solution)
            .subscribe((s) => {
              f(s);
            });
        }
      });
  }

  getTasks(): Observable<Task[]> {
    return this.httpService.get<Task[]>(API_HOST + "task");
  }
  getTops(): Observable<Top3Report[]> {
    return this.httpService.get<Top3Report[]>(API_HOST + "solution/top3");
  }
}
export class CompileResult {
  Warning: string;
  Errors: string;
  Result: any;
  Stats: string;
  Files: any;
  NotLoggedIn: Boolean;
}
export class Task {
  name: string;
  description: string;
  inputp: string;
  outputp: string;
}
export class Top3Report {
  name: string;
  count: number;
  tasks: string[];
}
export class Solution {
  name: string;
  task: Task;
  code: string;
  success?: Boolean;
  constructor() {
    this.name = null;
    this.code = "";
  }
}
