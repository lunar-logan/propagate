// Generated from /Users/anurag/git/propagate/query-lang/src/main/antlr/PropagateQueryExpression.g4 by ANTLR 4.9
package org.propagate.query.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PropagateQueryExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, EQ=3, NEQ=4, AND=5, OR=6, ID=7, NEWLINE=8, TEXT=9, WS=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "EQ", "NEQ", "AND", "OR", "ID", "NEWLINE", "TEXT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'='", "'!='", "'AND'", "'OR'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "EQ", "NEQ", "AND", "OR", "ID", "NEWLINE", "TEXT", 
			"WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public PropagateQueryExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PropagateQueryExpression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\fG\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3"+
		"\b\6\b)\n\b\r\b\16\b*\3\b\7\b.\n\b\f\b\16\b\61\13\b\3\t\5\t\64\n\t\3\t"+
		"\3\t\3\n\3\n\7\n:\n\n\f\n\16\n=\13\n\3\n\3\n\3\13\6\13B\n\13\r\13\16\13"+
		"C\3\13\3\13\3;\2\f\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\3\2\5"+
		"\4\2C\\c|\6\2\62;C\\aac|\4\2\13\13\"\"\2K\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\3\27\3\2\2\2\5\31\3\2\2\2\7\33\3\2\2\2\t"+
		"\35\3\2\2\2\13 \3\2\2\2\r$\3\2\2\2\17(\3\2\2\2\21\63\3\2\2\2\23\67\3\2"+
		"\2\2\25A\3\2\2\2\27\30\7*\2\2\30\4\3\2\2\2\31\32\7+\2\2\32\6\3\2\2\2\33"+
		"\34\7?\2\2\34\b\3\2\2\2\35\36\7#\2\2\36\37\7?\2\2\37\n\3\2\2\2 !\7C\2"+
		"\2!\"\7P\2\2\"#\7F\2\2#\f\3\2\2\2$%\7Q\2\2%&\7T\2\2&\16\3\2\2\2\')\t\2"+
		"\2\2(\'\3\2\2\2)*\3\2\2\2*(\3\2\2\2*+\3\2\2\2+/\3\2\2\2,.\t\3\2\2-,\3"+
		"\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\20\3\2\2\2\61/\3\2\2\2\62"+
		"\64\7\17\2\2\63\62\3\2\2\2\63\64\3\2\2\2\64\65\3\2\2\2\65\66\7\f\2\2\66"+
		"\22\3\2\2\2\67;\7$\2\28:\13\2\2\298\3\2\2\2:=\3\2\2\2;<\3\2\2\2;9\3\2"+
		"\2\2<>\3\2\2\2=;\3\2\2\2>?\7$\2\2?\24\3\2\2\2@B\t\4\2\2A@\3\2\2\2BC\3"+
		"\2\2\2CA\3\2\2\2CD\3\2\2\2DE\3\2\2\2EF\b\13\2\2F\26\3\2\2\2\b\2*/\63;"+
		"C\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}