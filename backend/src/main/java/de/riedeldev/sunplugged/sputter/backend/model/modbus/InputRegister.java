package de.riedeldev.sunplugged.sputter.backend.model.modbus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputRegister {

  public InputRegister(@JsonProperty("id") String id, @JsonProperty("name") String name,
      @JsonProperty("address") Integer address) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.value = 0;
  }

  private final String id;

  private final String name;

  private final Integer address;

  private Integer value;

}
