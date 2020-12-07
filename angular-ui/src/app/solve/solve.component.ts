import { Component, OnInit } from "@angular/core";
import { NgForm } from "@angular/forms";
import { ApiService, Solution, Task } from "../api.service";

@Component({
  selector: "app-solve",
  templateUrl: "./solve.component.html",
  styleUrls: ["./solve.component.scss"],
})
export class SolveComponent implements OnInit {
  tasks: Task[] = [];
  case: Solution = new Solution();
  submit = true;
  error: string | null = null;

  constructor(private api: ApiService) {
    api.getTasks().subscribe((ts) => {
      this.case = new Solution();
      this.tasks = ts;
      this.case.task = ts[0];
    });
  }

  ngOnInit() {}
  onSubmit(form: NgForm) {
    console.log(this.case);
    this.api.saveSolution(this.case, (s: Solution | string) => {
      if (s == null) return;
      if (typeof s === "string") {
        this.error = s;
      } else {
        Object.assign(this.case, s);
        this.error =
          "Solution is " + (this.case.success ? "successful" : "failed");
        this.submit = true;
        setTimeout(() => {
          this.error = null;
          this.submit = true;
          form.reset();
          this.case = new Solution();
          this.case.task = this.tasks[0];
        }, 5000);
      }
    });
  }
}
