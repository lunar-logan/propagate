package org.propagate.domain;

import java.util.Arrays;
import java.util.Objects;

public class Interpreter {
    enum Code {
        MOV("mov"),
        INC("inc"),
        DEC("dec");

        private final String name;

        Code(String name) {
            this.name = Objects.requireNonNull(name);
        }

        public String getName() {
            return name;
        }

        static Code fromName(String name) {
            return Arrays.stream(values())
                    .filter(code -> code.getName().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown name [" + name + "]"));
        }
    }

    static class Instruction {
        final Code code;
        final String[] params;

        public Instruction(Code code, String... params) {
            this.code = code;
            this.params = params;
        }

        @Override
        public String toString() {
            return "Instruction{" +
                    "code=" + code +
                    ", params=" + Arrays.toString(params) +
                    '}';
        }
    }

    interface InstructionReader {
        Instruction read(String ins);
    }

    static class DefaultInstructionReaderImpl implements InstructionReader {
        @Override
        public Instruction read(String ins) {
            return read0(ins.trim());
        }

        private Instruction read0(String ins) {
            int firstSpace = ins.indexOf(' ');
            String name = ins.substring(0, firstSpace).toLowerCase();
            String[] params = readParams(ins.substring(firstSpace).trim());
            return new Instruction(Code.fromName(name), params);
        }

        private String[] readParams(String paramStr) {
            return paramStr.split(",");
        }
    }

    public static void main(String[] args) {
        InstructionReader reader  = new DefaultInstructionReaderImpl();
        System.out.println(reader.read("mov x, y"));
        System.out.println(reader.read("proc-fib:"));
    }
}
