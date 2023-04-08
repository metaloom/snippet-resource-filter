# Snippet Resource Filter

This project contains an additional filter for the `maven-resource-plugin` maven plugin.

```xml
<dependency>
    <groupId>io.metaloom.maven</groupId>
    <artifactId>snippet-resource-filter</artifactId>
    <version>0.1.2</version>
</dependency>
```

It can be used to resolve [doxia style snippet macros](https://maven.apache.org/doxia/macros/index.html#snippet-macro) in resources.

## Example

.Example

```bash
# My Document

Include snippet here:
%{snippet|id=firstId|file=src/test/resources/base/snippet/testSnippet.txt}

```

.testSnippet.txt
```bash
# SNIPPET START firstId
The content that should be included
# SNIPPET END firstId
```

## Maven

This configuration will filter the `.github/md/README.md` file and place the output in `README.md`.

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-resources-plugin</artifactId>
  <version>3.3.1</version>
  <!-- We don't want submodules to run the execution as well -->
  <inherited>false</inherited>
  <executions>
    <execution>
      <id>readme-md</id>
      <!-- Run the filter on the `mvn clean` -->
      <phase>clean</phase>
      <goals>
        <goal>copy-resources</goal>
      </goals>
      <!-- Configuration to filter only the README.md file -->
      <configuration>
        <outputDirectory>${project.basedir}</outputDirectory>
        <resources>
          <resource>
            <directory>${project.basedir}/.github/md</directory>
            <includes>
              <include>README.md</include>
            </includes>
            <filtering>true</filtering>
          </resource>
        </resources>
        <encoding>UTF-8</encoding>
        <!-- Reference the snippet filter -->
        <mavenFilteringHints>
          <mavenFilteringHint>snippetFilter</mavenFilteringHint>
        </mavenFilteringHints>
      </configuration>
    </execution>
  </executions>
  <dependencies>
    <dependency>
      <groupId>io.metaloom.maven</groupId>
      <artifactId>snippet-resource-filter</artifactId>
      <version>0.1.2</version>
    </dependency>
  </dependencies>
</plugin>
```
