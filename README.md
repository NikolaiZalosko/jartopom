# JAR to POM
If you ever need to migrate from Ant to Maven, you can use this program to
1. find all dependencies in Maven repo,
2. generate a dependency block for a POM.

### How to run and test
#### Prerequisite:
- [JRE 11](https://www.oracle.com/cis/java/technologies/javase/jdk11-archive-downloads.html)

#### Steps:
1. `./mvnw clean package`
2. `java -jar target/jartopom-spring-boot.jar run/sample-data run/out compile run/sample-data`

Run args:
1. jar folder path
2. output folder path
3. dependencies scope
4. jar path from your project dir (in case dependency is not found, to generate a system scope block)