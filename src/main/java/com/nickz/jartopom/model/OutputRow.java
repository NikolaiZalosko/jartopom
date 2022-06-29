package com.nickz.jartopom.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputRow {

  private String jarName;
  private boolean isNexusFound; // 1 and only 1 result
  private String errorMessage;
  private String mavenId;
  private String groupId;
  private String artifactId;
  private String version;
}
