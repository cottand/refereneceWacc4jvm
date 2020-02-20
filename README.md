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
   * Returns empty if the query failed, or  RefAnswer otherwise.
   */
  fun compile(prog: File, stdin: String): Optional<RefAnswer>

  /**
   * Emulates the assembly file [armProg] with standard input [stdin]
   *
   * Returns empty if the query failed, or the serialised JSON of the reference compiler otherwise.
   */
  fun emulate(armProg: File, stdin: String): Optional<EmulatorReply>
```

### Small Java Examples
#### Compiler
```$xslt
        File myWaccProg = new File("./wacc_examples/path/to/my/file.wacc");
        String stdin = "Some test input for my program to handle";
        Optional<RefAnswer> answer = ReferenceWACC.compile(myWaccProg, stdin);
        if (answer.isEmpty()) {
            System.out.println("Query failed!");
        } else {
            int exitCode = answer.get().getCode();
            String compiledAssembly = answer.get().getAssembly();
            /* ... */
        }
```
#### Emulator
```
        File myAssemblyProg = new File("./path/to/my/assembly.s");
        String stdin = "Some test input for my program to handle";
        Optional<EmulatorReply> reply = ReferenceWACC.emulate(myAssemblyProg, stdin);
        if (reply.isEmpty()) {
            System.out.println("Query failed!");
        } else {
            String runtimeExitCode = reply.get().getEmulator_exit();
            String assemblerOut = reply.get().getAssemble_out();
            String emulatorOutput = reply.get().getEmulator_out();
            /* ... */
        }
```

If you need more help on how this works or info in `EmulatorReply`
or `RefAnswer` you can take a look at the source.

## Disclaimer

This was written solely to enable JVM users to use the reference compiler programmatically
without having to use ruby or bash, and not having to start a new REST client for each request.
I do not take responsibility for any problems you may run into using this tool.

If the POST API for the reference compiler changes (which has happened in the year 2019-2020 already) 
this will stop working. If you want it fixed, please submit a pull request rather than asking me directly
to fix it. Likewise if you find any bugs!

## TODO
If iI have the time, I'll try to make this a proper library on MavenCentral or JCenter.
