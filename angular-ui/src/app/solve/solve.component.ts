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
  error: string | null = null;
  formFreeze = false;

  constructor(private api: ApiService) {
    api.getTasks().subscribe((ts) => {
      this.case = new Solution();
      this.tasks = ts;
      this.case.task = ts[0];
    });
  }

  ngOnInit() {}
  onSubmit(form: NgForm) {
    this.formFreeze = true;
    this.api.saveSolution(this.case, (s: Solution, error: string) => {
      if (s == null) {
        this.error = error;
        this.formFreeze = false;
        return;
      }
      this.error =
        "Solution is " + (this.case.success ? "successful" : "failed") + (error || "");
      Object.assign(this.case, s);
      setTimeout(() => {
        this.error = null;
        this.formFreeze = false;
        form.reset();
        this.case = new Solution();
        this.case.task = this.tasks[0];
      }, 5000);
    });
  }
}
