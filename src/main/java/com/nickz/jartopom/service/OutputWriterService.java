package com.nickz.jartopom.service;

import com.nickz.jartopom.model.OutputRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OutputWriterService {

  public void generateXmlDependencies(String fileName, List<OutputRow> outputRows,
                                      String scope, String folderPathFromProjectDir) {
    File result = new File(fileName);
    try (FileOutputStream out = new FileOutputStream(result);
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out))) {
      for (OutputRow outputRow : outputRows) {
        List<String> xmlLines = new ArrayList<>();
        xmlLines.add(String.format("<!-- %s -->", outputRow.getJarName()));
        xmlLines.add("<dependency>");
        xmlLines.add(String.format("<groupId>%s</groupId>", outputRow.getGroupId()));
        xmlLines.add(String.format("<artifactId>%s</artifactId>", outputRow.getArtifactId()));
        xmlLines.add(String.format("<version>%s</version>", outputRow.getVersion()));
        if (!outputRow.isNexusFound()) {
          xmlLines.add(String.format("<scope>%s</scope>", "system"));
          String path = String.format("${project.basedir}/%s/%s",
              folderPathFromProjectDir, outputRow.getJarName());
          xmlLines.add(String.format("<systemPath>%s</systemPath>", path));
        } else if (!scope.equals("compile")) {
          xmlLines.add(String.format("<scope>%s</scope>", scope));
        }
        xmlLines.add("</dependency>");

        for (String line : xmlLines) {
          bw.write(line);
          bw.newLine();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void generateCsvFile(String fileName, List<OutputRow> outputRows) {
    String[] headers = {"file_name", "nexus_found", "error_message", "maven_id"};
    try (FileWriter out = new FileWriter(fileName);
         CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))) {
      for (OutputRow row : outputRows) {
        printer.printRecord(row.getJarName(), String.valueOf(row.isNexusFound()),
            row.getErrorMessage(), row.getMavenId());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
