package de.riedeldev.sunplugged.sputter.backend.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.riedeldev.sunplugged.sputter.backend.evra.EvraPump;

@CrossOrigin(origins = CrossOriginConfig.ALLOWED_CROSS_ORIGIN, maxAge = CrossOriginConfig.MAX_AGE)
@RestController
@RequestMapping({"/api/evara"})
public class EvaraController {

  private EvraPump pump;


  @Autowired
  public EvaraController(EvraPump pump) {
    this.pump = pump;

  }



}
