# Snippet Resource Filter

This project contains an additional filter for the `maven-resource-plugin` maven plugin.

```xml
<dependency>
    <groupId>io.metaloom.maven</groupId>
    <artifactId>snippet-resource-filter</artifactId>
    <version>${project.version}</version>
</dependency>
```

It can be used to resolve [doxia style snippet macros](https://maven.apache.org/doxia/macros/index.html#snippet-macro) in resources.

## Example

.Example

```bash
# My Document

Include snippet here:
%{snippet|id=snippet|file=.github/md/snippet.txt}

```

.testSnippet.txt
```bash
# SNIPPET START firstId
The content that should be included
# SNIPPET END firstId
```

## Maven

This configuration will filter the `${project.basedir}/.github/md/README.md` file and place the output in `${project.basedir}/README.md`.

```xml
%{snippet|id=plugin|file=pom.xml}
```
