package com.nickz.jartopom.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class SelectResponseDto {

  @JsonAlias("response")
  private SelectResponseInnerDto response;
}
