import { Component, OnInit } from '@angular/core';
import { EvaraService } from '../services/evara.service';
import { EvaraState } from '../model/evaraState';
import { StandardStates} from '../model/standardStates';

@Component({
  selector: 'app-evara-debug',
  templateUrl: './evara-debug.component.html',
  styleUrls: ['./evara-debug.component.css']
})
export class EvaraDebugComponent implements OnInit {

  cmdText: string;
  answer: string;
  
  standardStates = StandardStates;
  state: EvaraState = new EvaraState();

  constructor(private service: EvaraService) {
    service.subscribeToCommandAnswer(ans => {
      this.answer = ans;
      console.log("test");
    });

    service.subscribeToState(state => {
      Object.assign(this.state, state);
    })

   }

  executeCommand() {
    this.answer = undefined;
    this.service.issueCommand(this.cmdText)
    
  
  }

  ngOnInit() {
  }

}
