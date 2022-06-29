package com.nickz.jartopom.service;

import com.nickz.jartopom.model.OutputRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

  private final JarService jarService;
  private final OutputWriterService outputWriterService;

  public void execute(String jarFolderPath, String outputFolderPath, String dependencyScope, String libPathFromProjectDir) {
    List<OutputRow> outputRows = jarService.getMavenIds(jarFolderPath);

    String folderName = new File(jarFolderPath).getName();
    createIfNotExists(new File(outputFolderPath));
    String csvFilePath = outputFolderPath + File.separator + folderName + "_nexus_found.csv";
    String xmlDependenciesFilePath = outputFolderPath + File.separator + folderName + "_dependencies.xml";

    outputWriterService.generateCsvFile(csvFilePath, outputRows);
    outputWriterService.generateXmlDependencies(xmlDependenciesFilePath, outputRows,
        dependencyScope, libPathFromProjectDir);
  }

  private void createIfNotExists(File folder) {
    if (!folder.exists()) {
      folder.mkdir();
    }
  }
}
