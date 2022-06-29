package com.nickz.jartopom.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class MavenIdDto {

  private String id;

  @JsonAlias("g")
  private String groupId;

  @JsonAlias("a")
   private String artifactId;

  @JsonAlias("v")
  private String version;

  @JsonIgnore
  private String errorMessage;
}
