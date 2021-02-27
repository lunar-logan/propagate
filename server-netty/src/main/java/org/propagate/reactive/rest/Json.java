package org.propagate.reactive.rest;

import java.util.*;

public class Json {
    interface JsonReader {
        Object read();

        Map<String, Object> readObject();

        List<Object> readArray();
    }

    static class JsonReaderImpl implements JsonReader {
        private final Tokenizer tokenizer;

        public JsonReaderImpl(Tokenizer tokenizer) {
            this.tokenizer = tokenizer;
        }

        @Override
        public Object read() {
            String tok = "";
            while (tok.isBlank()) {
                tok = tokenizer.nextToken();
            }
            if (tok.equals("{")) {
                return readObject();
            }
            if (tok.equals("[")) {
                return readArray();
            }
            return tok;
        }

        @Override
        public Map<String, Object> readObject() {
            Map<String, Object> m = new LinkedHashMap<>();
            while (true) {
                String key = tokenizer.nextToken();
                if (key.equals("}")) break;
                tokenizer.nextToken();
                m.put(key, read());
                if (tokenizer.nextToken().equals("}")) break;
            }
            return m;
        }

        @Override
        public List<Object> readArray() {
            List<Object> list = new ArrayList<>();
            while (true) {
                Object val = read();
                if (String.valueOf(val).equals("]")) break;
                list.add(val);
                if (tokenizer.nextToken().equals("]")) break;
            }
            return list;
        }
    }

    static class Tokenizer{
        private StringTokenizer tokenizer;

        public Tokenizer(StringTokenizer tokenizer) {
            this.tokenizer = tokenizer;
        }

        public String nextToken() {
            String tok = "";
            while (tok.isBlank()) {
                tok = tokenizer.nextToken();
            }
            return tok;
        }
    }

    public static void main(String[] args) {
        String json = "12";
        StringTokenizer st = new StringTokenizer(json, " :{}[]\n\t,", true);
        JsonReader reader = new JsonReaderImpl(new Tokenizer(st));
        System.out.println(reader.read());
    }
}
