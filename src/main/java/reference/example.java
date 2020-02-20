package reference;

import java.io.File;

class Main {
    public static void main(String[] args) {
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

        File myAssemblyProg = new File("./path/to/my/assembly.s");
        EmulatorReply reply = ReferenceWACC.emulate(myAssemblyProg, stdin);
        if (reply == null) {
            System.out.println("Query failed!");
        } else {
            String runtimeExitCode = reply.getEmulator_exit();
            String assemblerOut = reply.getAssemble_out();
            String emulatorOutput = reply.getEmulator_out();
            /* ... */
        }

    }
}

