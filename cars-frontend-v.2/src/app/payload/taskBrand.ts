import {ThemePalette} from "@angular/material/core";

export class TaskBrand {
  constructor(name: string, completed: boolean, color: ThemePalette, subtasks: string[]) {
    this.name = name;
    this.completed = completed;
    this.color = color;
    for (let i = 0; i < subtasks.length; i++){
      this.subtasks[i].name = subtasks[i];
      this.subtasks[i].color = "primary";
      this.subtasks[i].completed = false;
    }

  }

  name: string;
  completed: boolean;
  color: ThemePalette;
  subtasks?: TaskBrand[];
}
