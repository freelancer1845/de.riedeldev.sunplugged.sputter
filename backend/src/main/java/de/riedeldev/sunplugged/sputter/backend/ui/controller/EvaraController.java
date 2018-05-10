package de.riedeldev.sunplugged.sputter.backend.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.riedeldev.sunplugged.sputter.backend.evra.EvraCommand;
import de.riedeldev.sunplugged.sputter.backend.evra.EvraPackage;
import de.riedeldev.sunplugged.sputter.backend.evra.EvraPump;
import de.riedeldev.sunplugged.sputter.backend.utils.StandardStates;

@CrossOrigin(origins = CrossOriginConfig.ALLOWED_CROSS_ORIGIN, maxAge = CrossOriginConfig.MAX_AGE)
@RestController
@RequestMapping({"/api/evara"})
public class EvaraController {

  private static final String TOPIC_EXECUTE_COMMAND_ANSWER = "/topic/evara/execute/answer";

  private EvraPump pump;

  private SimpMessagingTemplate simp;


  @Autowired
  public EvaraController(EvraPump pump, SimpMessagingTemplate simp) {
    this.pump = pump;
    this.simp = simp;
  }


  @MessageMapping("/evara/execute")
  public void executeCommand(String command) {
    if (pump.getState() != StandardStates.RUNNING) {
      simp.convertAndSend(TOPIC_EXECUTE_COMMAND_ANSWER, "Pump is not connected.");
    } else {
      EvraCommand cmdToExecute = new EvraCommand(command, cmd -> {
        String answer = cmd.getPackageAnswers().stream().map(EvraPackage::getMessage).reduce("",
            (a, b) -> a + " | " + b);
        if (answer.isEmpty()) {
          simp.convertAndSend(TOPIC_EXECUTE_COMMAND_ANSWER, "No answer");
        } else {
          simp.convertAndSend(TOPIC_EXECUTE_COMMAND_ANSWER, answer);
        }
      });
      this.pump.queueCommand(cmdToExecute);
    }
  }



}
