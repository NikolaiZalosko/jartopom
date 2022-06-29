package com.nickz.jartopom.service;

import com.nickz.jartopom.client.MavenClient;
import com.nickz.jartopom.dto.MavenIdDto;
import com.nickz.jartopom.dto.SelectResponseDto;
import com.nickz.jartopom.model.OutputRow;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JarService {

  private final MavenClient mavenClient;

  public List<OutputRow> getMavenIds(String folderName) {
    List<OutputRow> result = new ArrayList<>();
    File folder = new File(folderName);
    for (File jar : Objects.requireNonNull(folder.listFiles())) {
      if (jar.isDirectory()) {
        continue;
      }

      OutputRow row = new OutputRow();
      row.setJarName(jar.getName());
      String sha1 = null;
      try {
        sha1 = calcSHA1(jar);
      } catch (Exception e) {
        e.printStackTrace();
      }

      MavenIdDto mavenId = searchMavenId(sha1);
      if (mavenId == null) {
        row.setNexusFound(false);
      } else if (mavenId.getErrorMessage() != null) {
        row.setNexusFound(false);
        row.setErrorMessage(mavenId.getErrorMessage());
      } else {
        row.setNexusFound(true);
        row.setMavenId(mavenId.getId());
        row.setGroupId(mavenId.getGroupId());
        row.setArtifactId(mavenId.getArtifactId());
        row.setVersion(mavenId.getVersion());
      }

      result.add(row);
      // end for loop
    }
    return result;
  }

  private static String calcSHA1(File file) throws IOException, NoSuchAlgorithmException {

    MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
    try (InputStream input = new FileInputStream(file)) {

      byte[] buffer = new byte[8192];
      int len = input.read(buffer);

      while (len != -1) {
        sha1.update(buffer, 0, len);
        len = input.read(buffer);
      }

      return new HexBinaryAdapter().marshal(sha1.digest());
    }
  }

  private MavenIdDto searchMavenId(String sha1) {
    String query = "1:" + sha1;
    SelectResponseDto response = mavenClient.search(query, 20, "json");
    MavenIdDto[] docs = response.getResponse().getDocs();
    if (docs == null || docs.length == 0) {
      return null;
    } else if (docs.length > 1) {
      docs[0].setErrorMessage("Found more than one artifact");
    }
    return docs[0];
  }
}
