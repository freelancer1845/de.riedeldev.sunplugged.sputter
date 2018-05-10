package de.riedeldev.sunplugged.sputter.backend.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.fazecast.jSerialComm.SerialPort;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "evara")
@Getter
@Setter
public class EvraPumpConfiguration {

  private String port;

  private boolean start = false;

  private boolean autoRestart = false;

  private int baudrate = 9600;

  private int datalength = 8;

  private int stopbit = 1;

  private int parity = SerialPort.NO_PARITY;

}
