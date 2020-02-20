# ReferenceWacc4jvm

## How to use

Add the library's jar file to your classpath and that's it!

You should be able to download it from
 [here](https://github.com/Cottand/refereneceWacc4jvm/raw/master/build/libs/referenceWacc4jvm-1.0-all.jar).
 
 ### Adding it to the classpath
 If you are building use a tool like Maven or Gradle, you will need to edit your build
 config. Start by putting the jar inside a `/libs` folder at the root of your project,
then modify your `build.gradle`, `build.gradle.kts` , or `pom.xml` as follows:
 #### Gradle
 ```
 implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
```
 #### Gradle Kotlin DSL
 ```
 implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
```

#### Maven
```
<dependency>
    <groupId>org.cottand</groupId>
    <artifactId>referenceWacc4jvm</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${basedir}/libs/referenceWacc4jvm-1.0-all.jar</systemPath>
</dependency>

```
