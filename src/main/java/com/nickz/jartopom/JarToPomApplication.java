package com.nickz.jartopom;

import com.nickz.jartopom.service.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class JarToPomApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext appContext = SpringApplication.run(JarToPomApplication.class, args);
    MainService mainService = appContext.getBean(MainService.class);

    // args: jarFolderPath, outputFolderPath
    String jarFolderPath = args[0];
    String outputFolderPath = args[1];
    String scope = args[2];
    String libPathFromProjectDir = args[3];

    mainService.execute(jarFolderPath, outputFolderPath, scope, libPathFromProjectDir);
    System.exit(0);
  }
}
