// Generated from /home/olinchy/workspace/ems-mos/develop/source-code/tools/expressions/src/com/zte/exp/mos/Mos_exp.g4 by ANTLR 4.5.1
package com.zte.exp.mos;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Mos_expParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, SLASH=13, LIKE=14, MINUS=15, ADD=16, DIVID=17, 
		MULTI=18, BAR=19, AMPERSAND=20, RIGHT=21, LEFT=22, HEX=23, STRING=24, 
		INT=25, NOTEQUAL=26, CONTAINS=27, IN=28, NOT=29, OR=30, AND=31, SMALLER=32, 
		BIGGER=33, EQUAL=34, SMALLEROREQUAL=35, BIGGEROREQUAL=36, TEXT=37, WS=38;
	public static final int
		RULE_init = 0, RULE_dbexp = 1, RULE_dnexp = 2, RULE_dnsec = 3, RULE_dnterm = 4, 
		RULE_dnele = 5, RULE_element = 6, RULE_neid = 7, RULE_wildcard = 8, RULE_index = 9, 
		RULE_comparasion = 10, RULE_array = 11, RULE_arrayexp = 12, RULE_boolean_expr = 13, 
		RULE_boolean_term = 14, RULE_boolean_element = 15, RULE_extendedExp = 16, 
		RULE_calculateExp = 17, RULE_calcAddEle = 18, RULE_calcMultiEle = 19, 
		RULE_calcTerm = 20, RULE_localexp = 21, RULE_moexp = 22, RULE_propertyName = 23, 
		RULE_className = 24, RULE_value = 25, RULE_dbString = 26, RULE_pureString = 27, 
		RULE_intValue = 28, RULE_hexValue = 29, RULE_octValue = 30, RULE_name = 31;
	public static final String[] ruleNames = {
		"init", "dbexp", "dnexp", "dnsec", "dnterm", "dnele", "element", "neid", 
		"wildcard", "index", "comparasion", "array", "arrayexp", "boolean_expr", 
		"boolean_term", "boolean_element", "extendedExp", "calculateExp", "calcAddEle", 
		"calcMultiEle", "calcTerm", "localexp", "moexp", "propertyName", "className", 
		"value", "dbString", "pureString", "intValue", "hexValue", "octValue", 
		"name"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'mw.nr'", "'mw.nr8000tr='", "'['", "']'", "','", 
		"'true'", "'false'", "'${'", "'}'", "'.'", "'/'", null, "'-'", "'+'", 
		"'div'", "'*'", "'|'", "'&'", "'>>'", "'<<'", null, null, null, "'!='", 
		"'contains'", null, null, null, null, "'<'", "'>'", "'='", "'<='", "'>='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "SLASH", "LIKE", "MINUS", "ADD", "DIVID", "MULTI", "BAR", "AMPERSAND", 
		"RIGHT", "LEFT", "HEX", "STRING", "INT", "NOTEQUAL", "CONTAINS", "IN", 
		"NOT", "OR", "AND", "SMALLER", "BIGGER", "EQUAL", "SMALLEROREQUAL", "BIGGEROREQUAL", 
		"TEXT", "WS"
	};
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

	@Override
	public String getGrammarFileName() { return "Mos_exp.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Mos_expParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class InitContext extends ParserRuleContext {
		public Boolean_exprContext boolean_expr() {
			return getRuleContext(Boolean_exprContext.class,0);
		}
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterInit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitInit(this);
		}
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_init);
		try {
			setState(69);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				match(T__0);
				setState(65);
				boolean_expr();
				setState(66);
				match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				boolean_expr();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DbexpContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public DbexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dbexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterDbexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitDbexp(this);
		}
	}

	public final DbexpContext dbexp() throws RecognitionException {
		DbexpContext _localctx = new DbexpContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_dbexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DnexpContext extends ParserRuleContext {
		public DnsecContext dnsec() {
			return getRuleContext(DnsecContext.class,0);
		}
		public DnexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dnexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterDnexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitDnexp(this);
		}
	}

	public final DnexpContext dnexp() throws RecognitionException {
		DnexpContext _localctx = new DnexpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_dnexp);
		try {
			setState(78);
			switch (_input.LA(1)) {
			case T__9:
			case SLASH:
			case TEXT:
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				dnsec();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				match(T__0);
				setState(75);
				dnsec();
				setState(76);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DnsecContext extends ParserRuleContext {
		public DnsecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dnsec; }
	 
		public DnsecContext() { }
		public void copyFrom(DnsecContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OneDNTermContext extends DnsecContext {
		public DntermContext dnterm() {
			return getRuleContext(DntermContext.class,0);
		}
		public OneDNTermContext(DnsecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterOneDNTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitOneDNTerm(this);
		}
	}
	public static class TwoDNTermContext extends DnsecContext {
		public List<DntermContext> dnterm() {
			return getRuleContexts(DntermContext.class);
		}
		public DntermContext dnterm(int i) {
			return getRuleContext(DntermContext.class,i);
		}
		public TwoDNTermContext(DnsecContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterTwoDNTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitTwoDNTerm(this);
		}
	}

	public final DnsecContext dnsec() throws RecognitionException {
		DnsecContext _localctx = new DnsecContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_dnsec);
		try {
			setState(84);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new OneDNTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				dnterm();
				}
				break;
			case 2:
				_localctx = new TwoDNTermContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				dnterm();
				setState(82);
				dnterm();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DntermContext extends ParserRuleContext {
		public MoexpContext moexp() {
			return getRuleContext(MoexpContext.class,0);
		}
		public LocalexpContext localexp() {
			return getRuleContext(LocalexpContext.class,0);
		}
		public DneleContext dnele() {
			return getRuleContext(DneleContext.class,0);
		}
		public DntermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dnterm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterDnterm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitDnterm(this);
		}
	}

	public final DntermContext dnterm() throws RecognitionException {
		DntermContext _localctx = new DntermContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_dnterm);
		try {
			setState(89);
			switch (_input.LA(1)) {
			case TEXT:
				enterOuterAlt(_localctx, 1);
				{
				setState(86);
				moexp();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				localexp();
				}
				break;
			case SLASH:
				enterOuterAlt(_localctx, 3);
				{
				setState(88);
				dnele();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DneleContext extends ParserRuleContext {
		public DneleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dnele; }
	 
		public DneleContext() { }
		public void copyFrom(DneleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DnElementsContext extends DneleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public DnElementsContext(DneleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterDnElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitDnElements(this);
		}
	}
	public static class RootDNContext extends DneleContext {
		public TerminalNode SLASH() { return getToken(Mos_expParser.SLASH, 0); }
		public RootDNContext(DneleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterRootDN(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitRootDN(this);
		}
	}
	public static class WildDNContext extends DneleContext {
		public TerminalNode SLASH() { return getToken(Mos_expParser.SLASH, 0); }
		public WildcardContext wildcard() {
			return getRuleContext(WildcardContext.class,0);
		}
		public WildDNContext(DneleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterWildDN(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitWildDN(this);
		}
	}

	public final DneleContext dnele() throws RecognitionException {
		DneleContext _localctx = new DneleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_dnele);
		try {
			int _alt;
			setState(101);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new DnElementsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(94); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(91);
						element();
						setState(92);
						element();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(96); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			case 2:
				_localctx = new RootDNContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(98);
				match(SLASH);
				}
				break;
			case 3:
				_localctx = new WildDNContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(99);
				match(SLASH);
				setState(100);
				wildcard();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
	 
		public ElementContext() { }
		public void copyFrom(ElementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SlashExpContext extends ElementContext {
		public TerminalNode SLASH() { return getToken(Mos_expParser.SLASH, 0); }
		public MoexpContext moexp() {
			return getRuleContext(MoexpContext.class,0);
		}
		public LocalexpContext localexp() {
			return getRuleContext(LocalexpContext.class,0);
		}
		public SlashExpContext(ElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterSlashExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitSlashExp(this);
		}
	}
	public static class SlashValueContext extends ElementContext {
		public TerminalNode SLASH() { return getToken(Mos_expParser.SLASH, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public DbStringContext dbString() {
			return getRuleContext(DbStringContext.class,0);
		}
		public NeidContext neid() {
			return getRuleContext(NeidContext.class,0);
		}
		public WildcardContext wildcard() {
			return getRuleContext(WildcardContext.class,0);
		}
		public SlashValueContext(ElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterSlashValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitSlashValue(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_element);
		try {
			setState(116);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				_localctx = new SlashExpContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				match(SLASH);
				setState(106);
				switch (_input.LA(1)) {
				case TEXT:
					{
					setState(104);
					moexp();
					}
					break;
				case T__9:
					{
					setState(105);
					localexp();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				_localctx = new SlashValueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				match(SLASH);
				setState(114);
				switch (_input.LA(1)) {
				case TEXT:
					{
					setState(109);
					name();
					}
					break;
				case INT:
					{
					setState(110);
					index();
					}
					break;
				case STRING:
					{
					setState(111);
					dbString();
					}
					break;
				case T__2:
				case T__3:
					{
					setState(112);
					neid();
					}
					break;
				case MULTI:
					{
					setState(113);
					wildcard();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NeidContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(Mos_expParser.INT, 0); }
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public NeidContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_neid; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterNeid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitNeid(this);
		}
	}

	public final NeidContext neid() throws RecognitionException {
		NeidContext _localctx = new NeidContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_neid);
		try {
			setState(124);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				match(T__2);
				setState(119);
				match(INT);
				setState(120);
				match(EQUAL);
				setState(121);
				index();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				match(T__3);
				setState(123);
				index();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WildcardContext extends ParserRuleContext {
		public WildcardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wildcard; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterWildcard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitWildcard(this);
		}
	}

	public final WildcardContext wildcard() throws RecognitionException {
		WildcardContext _localctx = new WildcardContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_wildcard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(MULTI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(Mos_expParser.INT, 0); }
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitIndex(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparasionContext extends ParserRuleContext {
		public ComparasionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparasion; }
	 
		public ComparasionContext() { }
		public void copyFrom(ComparasionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EqualContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(Mos_expParser.EQUAL, 0); }
		public EqualContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitEqual(this);
		}
	}
	public static class SmallerContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode SMALLER() { return getToken(Mos_expParser.SMALLER, 0); }
		public SmallerContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterSmaller(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitSmaller(this);
		}
	}
	public static class ContainsContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode CONTAINS() { return getToken(Mos_expParser.CONTAINS, 0); }
		public ContainsContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterContains(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitContains(this);
		}
	}
	public static class InContext extends ComparasionContext {
		public ExtendedExpContext extendedExp() {
			return getRuleContext(ExtendedExpContext.class,0);
		}
		public TerminalNode IN() { return getToken(Mos_expParser.IN, 0); }
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public InContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterIn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitIn(this);
		}
	}
	public static class LikeContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode LIKE() { return getToken(Mos_expParser.LIKE, 0); }
		public LikeContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterLike(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitLike(this);
		}
	}
	public static class BiggerContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode BIGGER() { return getToken(Mos_expParser.BIGGER, 0); }
		public BiggerContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterBigger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitBigger(this);
		}
	}
	public static class NotEqualContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode NOTEQUAL() { return getToken(Mos_expParser.NOTEQUAL, 0); }
		public NotEqualContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterNotEqual(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitNotEqual(this);
		}
	}
	public static class SOrEContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode SMALLEROREQUAL() { return getToken(Mos_expParser.SMALLEROREQUAL, 0); }
		public SOrEContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterSOrE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitSOrE(this);
		}
	}
	public static class BOrEContext extends ComparasionContext {
		public List<ExtendedExpContext> extendedExp() {
			return getRuleContexts(ExtendedExpContext.class);
		}
		public ExtendedExpContext extendedExp(int i) {
			return getRuleContext(ExtendedExpContext.class,i);
		}
		public TerminalNode BIGGEROREQUAL() { return getToken(Mos_expParser.BIGGEROREQUAL, 0); }
		public BOrEContext(ComparasionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterBOrE(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitBOrE(this);
		}
	}

	public final ComparasionContext comparasion() throws RecognitionException {
		ComparasionContext _localctx = new ComparasionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_comparasion);
		try {
			setState(166);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new EqualContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				extendedExp();
				setState(131);
				match(EQUAL);
				setState(132);
				extendedExp();
				}
				break;
			case 2:
				_localctx = new BiggerContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				extendedExp();
				setState(135);
				match(BIGGER);
				setState(136);
				extendedExp();
				}
				break;
			case 3:
				_localctx = new SmallerContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(138);
				extendedExp();
				setState(139);
				match(SMALLER);
				setState(140);
				extendedExp();
				}
				break;
			case 4:
				_localctx = new InContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(142);
				extendedExp();
				setState(143);
				match(IN);
				setState(144);
				array();
				}
				break;
			case 5:
				_localctx = new ContainsContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(146);
				extendedExp();
				setState(147);
				match(CONTAINS);
				setState(148);
				extendedExp();
				}
				break;
			case 6:
				_localctx = new NotEqualContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(150);
				extendedExp();
				setState(151);
				match(NOTEQUAL);
				setState(152);
				extendedExp();
				}
				break;
			case 7:
				_localctx = new BOrEContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(154);
				extendedExp();
				setState(155);
				match(BIGGEROREQUAL);
				setState(156);
				extendedExp();
				}
				break;
			case 8:
				_localctx = new SOrEContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(158);
				extendedExp();
				setState(159);
				match(SMALLEROREQUAL);
				setState(160);
				extendedExp();
				}
				break;
			case 9:
				_localctx = new LikeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(162);
				extendedExp();
				setState(163);
				match(LIKE);
				setState(164);
				extendedExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayContext extends ParserRuleContext {
		public ArrayexpContext arrayexp() {
			return getRuleContext(ArrayexpContext.class,0);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitArray(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_array);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(T__4);
			setState(169);
			arrayexp();
			setState(170);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayexpContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ArrayexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterArrayexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitArrayexp(this);
		}
	}

	public final ArrayexpContext arrayexp() throws RecognitionException {
		ArrayexpContext _localctx = new ArrayexpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_arrayexp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			value();
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(173);
				match(T__6);
				setState(174);
				value();
				}
				}
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_exprContext extends ParserRuleContext {
		public Boolean_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_expr; }
	 
		public Boolean_exprContext() { }
		public void copyFrom(Boolean_exprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class OrContext extends Boolean_exprContext {
		public List<Boolean_termContext> boolean_term() {
			return getRuleContexts(Boolean_termContext.class);
		}
		public Boolean_termContext boolean_term(int i) {
			return getRuleContext(Boolean_termContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(Mos_expParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(Mos_expParser.OR, i);
		}
		public OrContext(Boolean_exprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitOr(this);
		}
	}
	public static class BoolTermContext extends Boolean_exprContext {
		public Boolean_termContext boolean_term() {
			return getRuleContext(Boolean_termContext.class,0);
		}
		public BoolTermContext(Boolean_exprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterBoolTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitBoolTerm(this);
		}
	}

	public final Boolean_exprContext boolean_expr() throws RecognitionException {
		Boolean_exprContext _localctx = new Boolean_exprContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_boolean_expr);
		int _la;
		try {
			setState(188);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new BoolTermContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(180);
				boolean_term();
				}
				break;
			case 2:
				_localctx = new OrContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(181);
				boolean_term();
				setState(184); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(182);
					match(OR);
					setState(183);
					boolean_term();
					}
					}
					setState(186); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==OR );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_termContext extends ParserRuleContext {
		public Boolean_termContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_term; }
	 
		public Boolean_termContext() { }
		public void copyFrom(Boolean_termContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AndContext extends Boolean_termContext {
		public List<Boolean_elementContext> boolean_element() {
			return getRuleContexts(Boolean_elementContext.class);
		}
		public Boolean_elementContext boolean_element(int i) {
			return getRuleContext(Boolean_elementContext.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(Mos_expParser.AND); }
		public TerminalNode AND(int i) {
			return getToken(Mos_expParser.AND, i);
		}
		public AndContext(Boolean_termContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitAnd(this);
		}
	}
	public static class BoolElementContext extends Boolean_termContext {
		public Boolean_elementContext boolean_element() {
			return getRuleContext(Boolean_elementContext.class,0);
		}
		public BoolElementContext(Boolean_termContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterBoolElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitBoolElement(this);
		}
	}

	public final Boolean_termContext boolean_term() throws RecognitionException {
		Boolean_termContext _localctx = new Boolean_termContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_boolean_term);
		int _la;
		try {
			setState(198);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new BoolElementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(190);
				boolean_element();
				}
				break;
			case 2:
				_localctx = new AndContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(191);
				boolean_element();
				setState(194); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(192);
					match(AND);
					setState(193);
					boolean_element();
					}
					}
					setState(196); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==AND );
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Boolean_elementContext extends ParserRuleContext {
		public Boolean_elementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolean_element; }
	 
		public Boolean_elementContext() { }
		public void copyFrom(Boolean_elementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FalseValueContext extends Boolean_elementContext {
		public FalseValueContext(Boolean_elementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterFalseValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitFalseValue(this);
		}
	}
	public static class NotContext extends Boolean_elementContext {
		public TerminalNode NOT() { return getToken(Mos_expParser.NOT, 0); }
		public Boolean_exprContext boolean_expr() {
			return getRuleContext(Boolean_exprContext.class,0);
		}
		public NotContext(Boolean_elementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitNot(this);
		}
	}
	public static class TrueValueContext extends Boolean_elementContext {
		public TrueValueContext(Boolean_elementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterTrueValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitTrueValue(this);
		}
	}
	public static class ReadCompareContext extends Boolean_elementContext {
		public ComparasionContext comparasion() {
			return getRuleContext(ComparasionContext.class,0);
		}
		public ReadCompareContext(Boolean_elementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterReadCompare(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitReadCompare(this);
		}
	}
	public static class BoolExprContext extends Boolean_elementContext {
		public Boolean_exprContext boolean_expr() {
			return getRuleContext(Boolean_exprContext.class,0);
		}
		public BoolExprContext(Boolean_elementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitBoolExpr(this);
		}
	}

	public final Boolean_elementContext boolean_element() throws RecognitionException {
		Boolean_elementContext _localctx = new Boolean_elementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_boolean_element);
		try {
			setState(212);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new TrueValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(200);
				match(T__7);
				}
				break;
			case 2:
				_localctx = new FalseValueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(201);
				match(T__8);
				}
				break;
			case 3:
				_localctx = new ReadCompareContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(202);
				comparasion();
				}
				break;
			case 4:
				_localctx = new BoolExprContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(203);
				match(T__0);
				setState(204);
				boolean_expr();
				setState(205);
				match(T__1);
				}
				break;
			case 5:
				_localctx = new NotContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(207);
				match(NOT);
				setState(208);
				match(T__0);
				setState(209);
				boolean_expr();
				setState(210);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExtendedExpContext extends ParserRuleContext {
		public MoexpContext moexp() {
			return getRuleContext(MoexpContext.class,0);
		}
		public LocalexpContext localexp() {
			return getRuleContext(LocalexpContext.class,0);
		}
		public DnexpContext dnexp() {
			return getRuleContext(DnexpContext.class,0);
		}
		public DbexpContext dbexp() {
			return getRuleContext(DbexpContext.class,0);
		}
		public CalculateExpContext calculateExp() {
			return getRuleContext(CalculateExpContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ExtendedExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_extendedExp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterExtendedExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitExtendedExp(this);
		}
	}

	public final ExtendedExpContext extendedExp() throws RecognitionException {
		ExtendedExpContext _localctx = new ExtendedExpContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_extendedExp);
		try {
			setState(220);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(214);
				moexp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(215);
				localexp();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(216);
				dnexp();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(217);
				dbexp();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(218);
				calculateExp();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(219);
				value();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CalculateExpContext extends ParserRuleContext {
		public CalculateExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_calculateExp; }
	 
		public CalculateExpContext() { }
		public void copyFrom(CalculateExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AddContext extends CalculateExpContext {
		public List<CalcAddEleContext> calcAddEle() {
			return getRuleContexts(CalcAddEleContext.class);
		}
		public CalcAddEleContext calcAddEle(int i) {
			return getRuleContext(CalcAddEleContext.class,i);
		}
		public TerminalNode ADD() { return getToken(Mos_expParser.ADD, 0); }
		public AddContext(CalculateExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitAdd(this);
		}
	}
	public static class MinusContext extends CalculateExpContext {
		public List<CalcAddEleContext> calcAddEle() {
			return getRuleContexts(CalcAddEleContext.class);
		}
		public CalcAddEleContext calcAddEle(int i) {
			return getRuleContext(CalcAddEleContext.class,i);
		}
		public TerminalNode MINUS() { return getToken(Mos_expParser.MINUS, 0); }
		public MinusContext(CalculateExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterMinus(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitMinus(this);
		}
	}
	public static class CalcaddTContext extends CalculateExpContext {
		public CalcAddEleContext calcAddEle() {
			return getRuleContext(CalcAddEleContext.class,0);
		}
		public CalcaddTContext(CalculateExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterCalcaddT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitCalcaddT(this);
		}
	}

	public final CalculateExpContext calculateExp() throws RecognitionException {
		CalculateExpContext _localctx = new CalculateExpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_calculateExp);
		try {
			setState(231);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new CalcaddTContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				calcAddEle();
				}
				break;
			case 2:
				_localctx = new AddContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				calcAddEle();
				setState(224);
				match(ADD);
				setState(225);
				calcAddEle();
				}
				break;
			case 3:
				_localctx = new MinusContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(227);
				calcAddEle();
				setState(228);
				match(MINUS);
				setState(229);
				calcAddEle();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CalcAddEleContext extends ParserRuleContext {
		public CalcAddEleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_calcAddEle; }
	 
		public CalcAddEleContext() { }
		public void copyFrom(CalcAddEleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CalcmultiContext extends CalcAddEleContext {
		public CalcMultiEleContext calcMultiEle() {
			return getRuleContext(CalcMultiEleContext.class,0);
		}
		public CalcmultiContext(CalcAddEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterCalcmulti(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitCalcmulti(this);
		}
	}
	public static class DividContext extends CalcAddEleContext {
		public List<CalcMultiEleContext> calcMultiEle() {
			return getRuleContexts(CalcMultiEleContext.class);
		}
		public CalcMultiEleContext calcMultiEle(int i) {
			return getRuleContext(CalcMultiEleContext.class,i);
		}
		public TerminalNode DIVID() { return getToken(Mos_expParser.DIVID, 0); }
		public DividContext(CalcAddEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterDivid(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitDivid(this);
		}
	}
	public static class MultiContext extends CalcAddEleContext {
		public List<CalcMultiEleContext> calcMultiEle() {
			return getRuleContexts(CalcMultiEleContext.class);
		}
		public CalcMultiEleContext calcMultiEle(int i) {
			return getRuleContext(CalcMultiEleContext.class,i);
		}
		public TerminalNode MULTI() { return getToken(Mos_expParser.MULTI, 0); }
		public MultiContext(CalcAddEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterMulti(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitMulti(this);
		}
	}

	public final CalcAddEleContext calcAddEle() throws RecognitionException {
		CalcAddEleContext _localctx = new CalcAddEleContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_calcAddEle);
		try {
			setState(242);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				_localctx = new CalcmultiContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(233);
				calcMultiEle();
				}
				break;
			case 2:
				_localctx = new MultiContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(234);
				calcMultiEle();
				setState(235);
				match(MULTI);
				setState(236);
				calcMultiEle();
				}
				break;
			case 3:
				_localctx = new DividContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(238);
				calcMultiEle();
				setState(239);
				match(DIVID);
				setState(240);
				calcMultiEle();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CalcMultiEleContext extends ParserRuleContext {
		public CalcMultiEleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_calcMultiEle; }
	 
		public CalcMultiEleContext() { }
		public void copyFrom(CalcMultiEleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BarContext extends CalcMultiEleContext {
		public List<CalcTermContext> calcTerm() {
			return getRuleContexts(CalcTermContext.class);
		}
		public CalcTermContext calcTerm(int i) {
			return getRuleContext(CalcTermContext.class,i);
		}
		public TerminalNode BAR() { return getToken(Mos_expParser.BAR, 0); }
		public BarContext(CalcMultiEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterBar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitBar(this);
		}
	}
	public static class LeftContext extends CalcMultiEleContext {
		public List<CalcTermContext> calcTerm() {
			return getRuleContexts(CalcTermContext.class);
		}
		public CalcTermContext calcTerm(int i) {
			return getRuleContext(CalcTermContext.class,i);
		}
		public TerminalNode LEFT() { return getToken(Mos_expParser.LEFT, 0); }
		public LeftContext(CalcMultiEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterLeft(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitLeft(this);
		}
	}
	public static class AmpersandContext extends CalcMultiEleContext {
		public List<CalcTermContext> calcTerm() {
			return getRuleContexts(CalcTermContext.class);
		}
		public CalcTermContext calcTerm(int i) {
			return getRuleContext(CalcTermContext.class,i);
		}
		public TerminalNode AMPERSAND() { return getToken(Mos_expParser.AMPERSAND, 0); }
		public AmpersandContext(CalcMultiEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterAmpersand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitAmpersand(this);
		}
	}
	public static class RightContext extends CalcMultiEleContext {
		public List<CalcTermContext> calcTerm() {
			return getRuleContexts(CalcTermContext.class);
		}
		public CalcTermContext calcTerm(int i) {
			return getRuleContext(CalcTermContext.class,i);
		}
		public TerminalNode RIGHT() { return getToken(Mos_expParser.RIGHT, 0); }
		public RightContext(CalcMultiEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterRight(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitRight(this);
		}
	}
	public static class CalcTContext extends CalcMultiEleContext {
		public CalcTermContext calcTerm() {
			return getRuleContext(CalcTermContext.class,0);
		}
		public CalcTContext(CalcMultiEleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterCalcT(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitCalcT(this);
		}
	}

	public final CalcMultiEleContext calcMultiEle() throws RecognitionException {
		CalcMultiEleContext _localctx = new CalcMultiEleContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_calcMultiEle);
		try {
			setState(261);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				_localctx = new CalcTContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(244);
				calcTerm();
				}
				break;
			case 2:
				_localctx = new LeftContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(245);
				calcTerm();
				setState(246);
				match(LEFT);
				setState(247);
				calcTerm();
				}
				break;
			case 3:
				_localctx = new RightContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(249);
				calcTerm();
				setState(250);
				match(RIGHT);
				setState(251);
				calcTerm();
				}
				break;
			case 4:
				_localctx = new AmpersandContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(253);
				calcTerm();
				setState(254);
				match(AMPERSAND);
				setState(255);
				calcTerm();
				}
				break;
			case 5:
				_localctx = new BarContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(257);
				calcTerm();
				setState(258);
				match(BAR);
				setState(259);
				calcTerm();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CalcTermContext extends ParserRuleContext {
		public IntValueContext intValue() {
			return getRuleContext(IntValueContext.class,0);
		}
		public CalculateExpContext calculateExp() {
			return getRuleContext(CalculateExpContext.class,0);
		}
		public DbexpContext dbexp() {
			return getRuleContext(DbexpContext.class,0);
		}
		public CalcTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_calcTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterCalcTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitCalcTerm(this);
		}
	}

	public final CalcTermContext calcTerm() throws RecognitionException {
		CalcTermContext _localctx = new CalcTermContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_calcTerm);
		try {
			setState(269);
			switch (_input.LA(1)) {
			case HEX:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				intValue();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 2);
				{
				setState(264);
				match(T__0);
				setState(265);
				calculateExp();
				setState(266);
				match(T__1);
				}
				break;
			case TEXT:
				enterOuterAlt(_localctx, 3);
				{
				setState(268);
				dbexp();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalexpContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public LocalexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterLocalexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitLocalexp(this);
		}
	}

	public final LocalexpContext localexp() throws RecognitionException {
		LocalexpContext _localctx = new LocalexpContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_localexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(T__9);
			setState(272);
			name();
			setState(273);
			match(T__10);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MoexpContext extends ParserRuleContext {
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public PropertyNameContext propertyName() {
			return getRuleContext(PropertyNameContext.class,0);
		}
		public MoexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_moexp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterMoexp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitMoexp(this);
		}
	}

	public final MoexpContext moexp() throws RecognitionException {
		MoexpContext _localctx = new MoexpContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_moexp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			className();
			setState(276);
			match(T__11);
			setState(277);
			propertyName();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyNameContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(Mos_expParser.TEXT, 0); }
		public PropertyNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterPropertyName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitPropertyName(this);
		}
	}

	public final PropertyNameContext propertyName() throws RecognitionException {
		PropertyNameContext _localctx = new PropertyNameContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_propertyName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassNameContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(Mos_expParser.TEXT, 0); }
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterClassName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitClassName(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_className);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(281);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public IntValueContext intValue() {
			return getRuleContext(IntValueContext.class,0);
		}
		public PureStringContext pureString() {
			return getRuleContext(PureStringContext.class,0);
		}
		public DbStringContext dbString() {
			return getRuleContext(DbStringContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitValue(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_value);
		try {
			setState(286);
			switch (_input.LA(1)) {
			case HEX:
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(283);
				intValue();
				}
				break;
			case TEXT:
				enterOuterAlt(_localctx, 2);
				{
				setState(284);
				pureString();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(285);
				dbString();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DbStringContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(Mos_expParser.STRING, 0); }
		public DbStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dbString; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterDbString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitDbString(this);
		}
	}

	public final DbStringContext dbString() throws RecognitionException {
		DbStringContext _localctx = new DbStringContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_dbString);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PureStringContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(Mos_expParser.TEXT, 0); }
		public PureStringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pureString; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterPureString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitPureString(this);
		}
	}

	public final PureStringContext pureString() throws RecognitionException {
		PureStringContext _localctx = new PureStringContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_pureString);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntValueContext extends ParserRuleContext {
		public OctValueContext octValue() {
			return getRuleContext(OctValueContext.class,0);
		}
		public HexValueContext hexValue() {
			return getRuleContext(HexValueContext.class,0);
		}
		public IntValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterIntValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitIntValue(this);
		}
	}

	public final IntValueContext intValue() throws RecognitionException {
		IntValueContext _localctx = new IntValueContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_intValue);
		try {
			setState(294);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(292);
				octValue();
				}
				break;
			case HEX:
				enterOuterAlt(_localctx, 2);
				{
				setState(293);
				hexValue();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HexValueContext extends ParserRuleContext {
		public TerminalNode HEX() { return getToken(Mos_expParser.HEX, 0); }
		public HexValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hexValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterHexValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitHexValue(this);
		}
	}

	public final HexValueContext hexValue() throws RecognitionException {
		HexValueContext _localctx = new HexValueContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_hexValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(296);
			match(HEX);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OctValueContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(Mos_expParser.INT, 0); }
		public OctValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_octValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterOctValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitOctValue(this);
		}
	}

	public final OctValueContext octValue() throws RecognitionException {
		OctValueContext _localctx = new OctValueContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_octValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(298);
			match(INT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(Mos_expParser.TEXT, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Mos_expListener ) ((Mos_expListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			match(TEXT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3(\u0131\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\3\2\3\2\3\2\3\2\3\2\5\2H\n\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\5\4Q\n\4"+
		"\3\5\3\5\3\5\3\5\5\5W\n\5\3\6\3\6\3\6\5\6\\\n\6\3\7\3\7\3\7\6\7a\n\7\r"+
		"\7\16\7b\3\7\3\7\3\7\5\7h\n\7\3\b\3\b\3\b\5\bm\n\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\5\bu\n\b\5\bw\n\b\3\t\3\t\3\t\3\t\3\t\3\t\5\t\177\n\t\3\n\3\n\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\5\f\u00a9\n\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\7\16\u00b2\n\16"+
		"\f\16\16\16\u00b5\13\16\3\17\3\17\3\17\3\17\6\17\u00bb\n\17\r\17\16\17"+
		"\u00bc\5\17\u00bf\n\17\3\20\3\20\3\20\3\20\6\20\u00c5\n\20\r\20\16\20"+
		"\u00c6\5\20\u00c9\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\5\21\u00d7\n\21\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00df"+
		"\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00ea\n\23\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00f5\n\24\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\5\25\u0108\n\25\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0110\n\26\3\27\3"+
		"\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\5"+
		"\33\u0121\n\33\3\34\3\34\3\35\3\35\3\36\3\36\5\36\u0129\n\36\3\37\3\37"+
		"\3 \3 \3!\3!\3!\2\2\"\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,."+
		"\60\62\64\668:<>@\2\2\u0142\2G\3\2\2\2\4I\3\2\2\2\6P\3\2\2\2\bV\3\2\2"+
		"\2\n[\3\2\2\2\fg\3\2\2\2\16v\3\2\2\2\20~\3\2\2\2\22\u0080\3\2\2\2\24\u0082"+
		"\3\2\2\2\26\u00a8\3\2\2\2\30\u00aa\3\2\2\2\32\u00ae\3\2\2\2\34\u00be\3"+
		"\2\2\2\36\u00c8\3\2\2\2 \u00d6\3\2\2\2\"\u00de\3\2\2\2$\u00e9\3\2\2\2"+
		"&\u00f4\3\2\2\2(\u0107\3\2\2\2*\u010f\3\2\2\2,\u0111\3\2\2\2.\u0115\3"+
		"\2\2\2\60\u0119\3\2\2\2\62\u011b\3\2\2\2\64\u0120\3\2\2\2\66\u0122\3\2"+
		"\2\28\u0124\3\2\2\2:\u0128\3\2\2\2<\u012a\3\2\2\2>\u012c\3\2\2\2@\u012e"+
		"\3\2\2\2BC\7\3\2\2CD\5\34\17\2DE\7\4\2\2EH\3\2\2\2FH\5\34\17\2GB\3\2\2"+
		"\2GF\3\2\2\2H\3\3\2\2\2IJ\5@!\2J\5\3\2\2\2KQ\5\b\5\2LM\7\3\2\2MN\5\b\5"+
		"\2NO\7\4\2\2OQ\3\2\2\2PK\3\2\2\2PL\3\2\2\2Q\7\3\2\2\2RW\5\n\6\2ST\5\n"+
		"\6\2TU\5\n\6\2UW\3\2\2\2VR\3\2\2\2VS\3\2\2\2W\t\3\2\2\2X\\\5.\30\2Y\\"+
		"\5,\27\2Z\\\5\f\7\2[X\3\2\2\2[Y\3\2\2\2[Z\3\2\2\2\\\13\3\2\2\2]^\5\16"+
		"\b\2^_\5\16\b\2_a\3\2\2\2`]\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3\2\2\2ch\3"+
		"\2\2\2dh\7\17\2\2ef\7\17\2\2fh\5\22\n\2g`\3\2\2\2gd\3\2\2\2ge\3\2\2\2"+
		"h\r\3\2\2\2il\7\17\2\2jm\5.\30\2km\5,\27\2lj\3\2\2\2lk\3\2\2\2mw\3\2\2"+
		"\2nt\7\17\2\2ou\5@!\2pu\5\24\13\2qu\5\66\34\2ru\5\20\t\2su\5\22\n\2to"+
		"\3\2\2\2tp\3\2\2\2tq\3\2\2\2tr\3\2\2\2ts\3\2\2\2uw\3\2\2\2vi\3\2\2\2v"+
		"n\3\2\2\2w\17\3\2\2\2xy\7\5\2\2yz\7\33\2\2z{\7$\2\2{\177\5\24\13\2|}\7"+
		"\6\2\2}\177\5\24\13\2~x\3\2\2\2~|\3\2\2\2\177\21\3\2\2\2\u0080\u0081\7"+
		"\24\2\2\u0081\23\3\2\2\2\u0082\u0083\7\33\2\2\u0083\25\3\2\2\2\u0084\u0085"+
		"\5\"\22\2\u0085\u0086\7$\2\2\u0086\u0087\5\"\22\2\u0087\u00a9\3\2\2\2"+
		"\u0088\u0089\5\"\22\2\u0089\u008a\7#\2\2\u008a\u008b\5\"\22\2\u008b\u00a9"+
		"\3\2\2\2\u008c\u008d\5\"\22\2\u008d\u008e\7\"\2\2\u008e\u008f\5\"\22\2"+
		"\u008f\u00a9\3\2\2\2\u0090\u0091\5\"\22\2\u0091\u0092\7\36\2\2\u0092\u0093"+
		"\5\30\r\2\u0093\u00a9\3\2\2\2\u0094\u0095\5\"\22\2\u0095\u0096\7\35\2"+
		"\2\u0096\u0097\5\"\22\2\u0097\u00a9\3\2\2\2\u0098\u0099\5\"\22\2\u0099"+
		"\u009a\7\34\2\2\u009a\u009b\5\"\22\2\u009b\u00a9\3\2\2\2\u009c\u009d\5"+
		"\"\22\2\u009d\u009e\7&\2\2\u009e\u009f\5\"\22\2\u009f\u00a9\3\2\2\2\u00a0"+
		"\u00a1\5\"\22\2\u00a1\u00a2\7%\2\2\u00a2\u00a3\5\"\22\2\u00a3\u00a9\3"+
		"\2\2\2\u00a4\u00a5\5\"\22\2\u00a5\u00a6\7\20\2\2\u00a6\u00a7\5\"\22\2"+
		"\u00a7\u00a9\3\2\2\2\u00a8\u0084\3\2\2\2\u00a8\u0088\3\2\2\2\u00a8\u008c"+
		"\3\2\2\2\u00a8\u0090\3\2\2\2\u00a8\u0094\3\2\2\2\u00a8\u0098\3\2\2\2\u00a8"+
		"\u009c\3\2\2\2\u00a8\u00a0\3\2\2\2\u00a8\u00a4\3\2\2\2\u00a9\27\3\2\2"+
		"\2\u00aa\u00ab\7\7\2\2\u00ab\u00ac\5\32\16\2\u00ac\u00ad\7\b\2\2\u00ad"+
		"\31\3\2\2\2\u00ae\u00b3\5\64\33\2\u00af\u00b0\7\t\2\2\u00b0\u00b2\5\64"+
		"\33\2\u00b1\u00af\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\33\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00bf\5\36\20"+
		"\2\u00b7\u00ba\5\36\20\2\u00b8\u00b9\7 \2\2\u00b9\u00bb\5\36\20\2\u00ba"+
		"\u00b8\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2"+
		"\2\2\u00bd\u00bf\3\2\2\2\u00be\u00b6\3\2\2\2\u00be\u00b7\3\2\2\2\u00bf"+
		"\35\3\2\2\2\u00c0\u00c9\5 \21\2\u00c1\u00c4\5 \21\2\u00c2\u00c3\7!\2\2"+
		"\u00c3\u00c5\5 \21\2\u00c4\u00c2\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c4"+
		"\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c9\3\2\2\2\u00c8\u00c0\3\2\2\2\u00c8"+
		"\u00c1\3\2\2\2\u00c9\37\3\2\2\2\u00ca\u00d7\7\n\2\2\u00cb\u00d7\7\13\2"+
		"\2\u00cc\u00d7\5\26\f\2\u00cd\u00ce\7\3\2\2\u00ce\u00cf\5\34\17\2\u00cf"+
		"\u00d0\7\4\2\2\u00d0\u00d7\3\2\2\2\u00d1\u00d2\7\37\2\2\u00d2\u00d3\7"+
		"\3\2\2\u00d3\u00d4\5\34\17\2\u00d4\u00d5\7\4\2\2\u00d5\u00d7\3\2\2\2\u00d6"+
		"\u00ca\3\2\2\2\u00d6\u00cb\3\2\2\2\u00d6\u00cc\3\2\2\2\u00d6\u00cd\3\2"+
		"\2\2\u00d6\u00d1\3\2\2\2\u00d7!\3\2\2\2\u00d8\u00df\5.\30\2\u00d9\u00df"+
		"\5,\27\2\u00da\u00df\5\6\4\2\u00db\u00df\5\4\3\2\u00dc\u00df\5$\23\2\u00dd"+
		"\u00df\5\64\33\2\u00de\u00d8\3\2\2\2\u00de\u00d9\3\2\2\2\u00de\u00da\3"+
		"\2\2\2\u00de\u00db\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00dd\3\2\2\2\u00df"+
		"#\3\2\2\2\u00e0\u00ea\5&\24\2\u00e1\u00e2\5&\24\2\u00e2\u00e3\7\22\2\2"+
		"\u00e3\u00e4\5&\24\2\u00e4\u00ea\3\2\2\2\u00e5\u00e6\5&\24\2\u00e6\u00e7"+
		"\7\21\2\2\u00e7\u00e8\5&\24\2\u00e8\u00ea\3\2\2\2\u00e9\u00e0\3\2\2\2"+
		"\u00e9\u00e1\3\2\2\2\u00e9\u00e5\3\2\2\2\u00ea%\3\2\2\2\u00eb\u00f5\5"+
		"(\25\2\u00ec\u00ed\5(\25\2\u00ed\u00ee\7\24\2\2\u00ee\u00ef\5(\25\2\u00ef"+
		"\u00f5\3\2\2\2\u00f0\u00f1\5(\25\2\u00f1\u00f2\7\23\2\2\u00f2\u00f3\5"+
		"(\25\2\u00f3\u00f5\3\2\2\2\u00f4\u00eb\3\2\2\2\u00f4\u00ec\3\2\2\2\u00f4"+
		"\u00f0\3\2\2\2\u00f5\'\3\2\2\2\u00f6\u0108\5*\26\2\u00f7\u00f8\5*\26\2"+
		"\u00f8\u00f9\7\30\2\2\u00f9\u00fa\5*\26\2\u00fa\u0108\3\2\2\2\u00fb\u00fc"+
		"\5*\26\2\u00fc\u00fd\7\27\2\2\u00fd\u00fe\5*\26\2\u00fe\u0108\3\2\2\2"+
		"\u00ff\u0100\5*\26\2\u0100\u0101\7\26\2\2\u0101\u0102\5*\26\2\u0102\u0108"+
		"\3\2\2\2\u0103\u0104\5*\26\2\u0104\u0105\7\25\2\2\u0105\u0106\5*\26\2"+
		"\u0106\u0108\3\2\2\2\u0107\u00f6\3\2\2\2\u0107\u00f7\3\2\2\2\u0107\u00fb"+
		"\3\2\2\2\u0107\u00ff\3\2\2\2\u0107\u0103\3\2\2\2\u0108)\3\2\2\2\u0109"+
		"\u0110\5:\36\2\u010a\u010b\7\3\2\2\u010b\u010c\5$\23\2\u010c\u010d\7\4"+
		"\2\2\u010d\u0110\3\2\2\2\u010e\u0110\5\4\3\2\u010f\u0109\3\2\2\2\u010f"+
		"\u010a\3\2\2\2\u010f\u010e\3\2\2\2\u0110+\3\2\2\2\u0111\u0112\7\f\2\2"+
		"\u0112\u0113\5@!\2\u0113\u0114\7\r\2\2\u0114-\3\2\2\2\u0115\u0116\5\62"+
		"\32\2\u0116\u0117\7\16\2\2\u0117\u0118\5\60\31\2\u0118/\3\2\2\2\u0119"+
		"\u011a\7\'\2\2\u011a\61\3\2\2\2\u011b\u011c\7\'\2\2\u011c\63\3\2\2\2\u011d"+
		"\u0121\5:\36\2\u011e\u0121\58\35\2\u011f\u0121\5\66\34\2\u0120\u011d\3"+
		"\2\2\2\u0120\u011e\3\2\2\2\u0120\u011f\3\2\2\2\u0121\65\3\2\2\2\u0122"+
		"\u0123\7\32\2\2\u0123\67\3\2\2\2\u0124\u0125\7\'\2\2\u01259\3\2\2\2\u0126"+
		"\u0129\5> \2\u0127\u0129\5<\37\2\u0128\u0126\3\2\2\2\u0128\u0127\3\2\2"+
		"\2\u0129;\3\2\2\2\u012a\u012b\7\31\2\2\u012b=\3\2\2\2\u012c\u012d\7\33"+
		"\2\2\u012d?\3\2\2\2\u012e\u012f\7\'\2\2\u012fA\3\2\2\2\32GPV[bgltv~\u00a8"+
		"\u00b3\u00bc\u00be\u00c6\u00c8\u00d6\u00de\u00e9\u00f4\u0107\u010f\u0120"+
		"\u0128";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}