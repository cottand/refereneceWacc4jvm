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

## Usage
This was written with Kotlin, but you should be able to use it with Java and Scala too.
```
  /**
   * Compiles and runs [prog]  passing it [stdin].
   *
   * IMPORTANT: assumes the program is well formed, ie, there can be no compile errors. This tool should be used to
   * see the reference compiler's assembly code and to check their output, not to check semantic/syntactic checking.
   *
   * Returns null if the query failed, or  RefAnswer otherwise.
   */
  fun compile(prog: File, stdin: String): RefAnswer?

  /**
   * Emulates the assembly file [armProg] with standard input [stdin]
   *
   * Returns null if the query failed, or the serialised JSON of the reference compiler otherwise.
   */
  fun emulate(armProg: File, stdin: String): EmulatorReply?
```

### Small Java Examples
#### Compiler
```$xslt
        File myWaccProg = new File("./wacc_examples/path/to/my/file.wacc");
        String stdin = "Some test input for my program to handle";
        RefAnswer answer = ReferenceWACC.compile(myWaccProg, stdin);
        if (answer == null) {
            System.out.println("Query failed!");
        } else {
            int exitCode = answer.getCode();
            String compiledAssembly = answer.getAssembly();
            /* ... */
        }
```
#### Emulator
```
        File myAssemblyProg = new File("./path/to/my/assembly.s");
        String stdin = "Some test input for my program to handle";
        EmulatorReply reply = ReferenceWACC.emulate(myAssemblyProg, stdin);
        if (reply == null) {
            System.out.println("Query failed!");
        } else {
            String runtimeExitCode = reply.getEmulator_exit();
            String assemblerOut = reply.getAssemble_out();
            String emulatorOutput = reply.getEmulator_out();
            /* ... */
        }
```
