import {ThemePalette} from "@angular/material/core";

export class Task {

  constructor(name: string, completed: boolean, subtasks: Task[]) {
    this.name = name;
    this.completed = completed;
    this.subtasks = subtasks;
  }

  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: Task[];
}
