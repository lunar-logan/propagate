package org.propagate.query;

import java.io.IOException;
import java.util.Map;

public class T {
    public static void main(String[] args) throws IOException {
//        String program = "2+444/444 \n" +
//                "a=15 \n" +
//                "a*5 \n";
        String program = "(uid=\"134\" AND m_id != \"77\") ";
//        ExprParser parser = new ExprParser(new CommonTokenStream(new ExprLexer(CharStreams.fromString(program))));
//        ExprParser.ProgContext prog = parser.prog();
//        System.out.println(prog.accept(new ExprVisitorImpl()));

        final QueryFactory factory = new DefaultQueryFactoryImpl();
        Query q = factory.parse(program);

        System.out.println(q.eval(Map.of(
                "uid", "134",
                "m_id", "77io"
        )));
    }
}
