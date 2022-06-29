package com.nickz.jartopom.client;

import com.nickz.jartopom.dto.SelectResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mavenClient", url = "https://search.maven.org/solrsearch")
public interface MavenClient {

  @GetMapping("/select")
  SelectResponseDto search(@RequestParam("q") String q, @RequestParam("rows") int rows, @RequestParam("wt") String wt);
}
