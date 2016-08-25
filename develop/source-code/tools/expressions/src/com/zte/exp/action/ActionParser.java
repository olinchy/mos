// Generated from Action.g4 by ANTLR 4.1
package com.zte.exp.action;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class ActionParser extends Parser
{
    public static final int
            T__19 = 1, T__18 = 2, T__17 = 3, T__16 = 4, T__15 = 5, T__14 = 6, T__13 = 7, T__12 = 8,
            T__11 = 9, T__10 = 10, T__9 = 11, T__8 = 12, T__7 = 13, T__6 = 14, T__5 = 15, T__4 = 16,
            T__3 = 17, T__2 = 18, T__1 = 19, T__0 = 20, ALL = 21, SLASH = 22, LIKE = 23, MINUS = 24,
            ADD = 25, DIVID = 26, MULTI = 27, BAR = 28, AMPERSAND = 29, RIGHT = 30, LEFT = 31, HEX =
            32,
            STRING = 33, INT = 34, NOTEQUAL = 35, CONTAINS = 36, IN = 37, NOT = 38, OR = 39, AND =
            40,
            SMALLER = 41, BIGGER = 42, EQUAL = 43, SMALLEROREQUAL = 44, BIGGEROREQUAL = 45,
            TEXT = 46, WS = 47;
    public static final String[] tokenNames = {
            "<INVALID>", "'onSelect('", "']'", "')'", "'.'", "','", "'['", "'('",
            "':'", "'tree('", "'${'", "'false'", "'version('", "'mw.nr8250='", "'json('",
            "'ne('", "'select('", "'mw.nr8120='", "'commit('", "'}'", "'true'", "ALL",
            "SLASH", "LIKE", "'-'", "'+'", "DIVID", "'*'", "'|'", "'&'", "'>>'", "'<<'",
            "HEX", "STRING", "INT", "'!='", "'contains'", "IN", "NOT", "OR", "AND",
            "'<'", "'>'", "'='", "'<='", "'>='", "TEXT", "WS"
    };
    public static final int
            RULE_action = 0, RULE_ne = 1, RULE_json = 2, RULE_jsonObject = 3, RULE_para = 4,
            RULE_exp = 5, RULE_expvalue = 6, RULE_init = 7, RULE_dbexp = 8, RULE_dnexp = 9,
            RULE_dnsec = 10, RULE_dnterm = 11, RULE_dnele = 12, RULE_element = 13,
            RULE_neid = 14, RULE_index = 15, RULE_comparasion = 16, RULE_array = 17,
            RULE_arrayexp = 18, RULE_boolean_expr = 19, RULE_boolean_term = 20,
            RULE_boolean_element = 21,
            RULE_extendedExp = 22, RULE_calculateExp = 23, RULE_calcAddEle = 24, RULE_calcMultiEle =
            25,
            RULE_calcTerm = 26, RULE_localexp = 27, RULE_moexp = 28, RULE_propertyName = 29,
            RULE_className = 30, RULE_value = 31, RULE_dbString = 32, RULE_pureString = 33,
            RULE_intValue = 34, RULE_hexValue = 35, RULE_octValue = 36, RULE_name = 37;
    public static final String[] ruleNames = {
            "action", "ne", "json", "jsonObject", "para", "exp", "expvalue", "init",
            "dbexp", "dnexp", "dnsec", "dnterm", "dnele", "element", "neid", "index",
            "comparasion", "array", "arrayexp", "boolean_expr", "boolean_term", "boolean_element",
            "extendedExp", "calculateExp", "calcAddEle", "calcMultiEle", "calcTerm",
            "localexp", "moexp", "propertyName", "className", "value", "dbString",
            "pureString", "intValue", "hexValue", "octValue", "name"
    };
    public static final String _serializedATN =
            "\3\uacf5\uee8c\u4f5d\u8b0d\u4a45\u78bd\u1b2f\u3378\3\61\u0166\4\2\t\2" +
                    "\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\2\3\2\3\2\3" +
                    "\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2" +
                    "\5\2g\n\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\6\6\6v\n\6" +
                    "\r\6\16\6w\3\7\3\7\3\7\3\7\3\b\3\b\5\b\u0080\n\b\3\t\3\t\3\t\3\t\3\t\5" +
                    "\t\u0087\n\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\5\13\u0090\n\13\3\f\3\f" +
                    "\3\f\3\f\5\f\u0096\n\f\3\r\3\r\3\r\5\r\u009b\n\r\3\16\3\16\3\16\6\16\u00a0" +
                    "\n\16\r\16\16\16\u00a1\3\17\3\17\3\17\5\17\u00a7\n\17\3\17\3\17\3\17\3" +
                    "\17\3\17\5\17\u00ae\n\17\5\17\u00b0\n\17\3\20\3\20\3\20\3\20\5\20\u00b6" +
                    "\n\20\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22" +
                    "\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22" +
                    "\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00de\n\22" +
                    "\3\23\3\23\3\23\3\23\3\24\3\24\3\24\7\24\u00e7\n\24\f\24\16\24\u00ea\13" +
                    "\24\3\25\3\25\3\25\3\25\6\25\u00f0\n\25\r\25\16\25\u00f1\5\25\u00f4\n" +
                    "\25\3\26\3\26\3\26\3\26\6\26\u00fa\n\26\r\26\16\26\u00fb\5\26\u00fe\n" +
                    "\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u010c" +
                    "\n\27\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0114\n\30\3\31\3\31\3\31\3\31" +
                    "\3\31\3\31\3\31\3\31\3\31\5\31\u011f\n\31\3\32\3\32\3\32\3\32\3\32\3\32" +
                    "\3\32\3\32\3\32\5\32\u012a\n\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33" +
                    "\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u013d\n\33\3\34\3\34" +
                    "\3\34\3\34\3\34\3\34\5\34\u0145\n\34\3\35\3\35\3\35\3\35\3\36\3\36\3\36" +
                    "\3\36\3\37\3\37\3 \3 \3!\3!\3!\5!\u0156\n!\3\"\3\"\3#\3#\3$\3$\5$\u015e" +
                    "\n$\3%\3%\3&\3&\3\'\3\'\3\'\2(\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36" +
                    " \"$&(*,.\60\62\64\668:<>@BDFHJL\2\3\4\2\35\35$$\u0174\2f\3\2\2\2\4h\3" +
                    "\2\2\2\6l\3\2\2\2\bp\3\2\2\2\nr\3\2\2\2\fy\3\2\2\2\16\177\3\2\2\2\20\u0086" +
                    "\3\2\2\2\22\u0088\3\2\2\2\24\u008f\3\2\2\2\26\u0095\3\2\2\2\30\u009a\3" +
                    "\2\2\2\32\u009f\3\2\2\2\34\u00af\3\2\2\2\36\u00b5\3\2\2\2 \u00b7\3\2\2" +
                    "\2\"\u00dd\3\2\2\2$\u00df\3\2\2\2&\u00e3\3\2\2\2(\u00f3\3\2\2\2*\u00fd" +
                    "\3\2\2\2,\u010b\3\2\2\2.\u0113\3\2\2\2\60\u011e\3\2\2\2\62\u0129\3\2\2" +
                    "\2\64\u013c\3\2\2\2\66\u0144\3\2\2\28\u0146\3\2\2\2:\u014a\3\2\2\2<\u014e" +
                    "\3\2\2\2>\u0150\3\2\2\2@\u0155\3\2\2\2B\u0157\3\2\2\2D\u0159\3\2\2\2F" +
                    "\u015d\3\2\2\2H\u015f\3\2\2\2J\u0161\3\2\2\2L\u0163\3\2\2\2NO\7\13\2\2" +
                    "OP\5\n\6\2PQ\7\5\2\2Qg\3\2\2\2RS\7\22\2\2ST\5\4\3\2TU\7\7\2\2UV\5\6\4" +
                    "\2VW\7\5\2\2Wg\3\2\2\2XY\7\24\2\2YZ\5\4\3\2Z[\7\7\2\2[\\\5\6\4\2\\]\7" +
                    "\5\2\2]g\3\2\2\2^_\7\3\2\2_`\5\20\t\2`a\7\5\2\2ag\3\2\2\2bc\7\16\2\2c" +
                    "d\5\4\3\2de\7\5\2\2eg\3\2\2\2fN\3\2\2\2fR\3\2\2\2fX\3\2\2\2f^\3\2\2\2" +
                    "fb\3\2\2\2g\3\3\2\2\2hi\7\21\2\2ij\5\24\13\2jk\7\5\2\2k\5\3\2\2\2lm\7" +
                    "\20\2\2mn\5\b\5\2no\7\5\2\2o\7\3\2\2\2pq\7\27\2\2q\t\3\2\2\2ru\5\f\7\2" +
                    "st\7\7\2\2tv\5\f\7\2us\3\2\2\2vw\3\2\2\2wu\3\2\2\2wx\3\2\2\2x\13\3\2\2" +
                    "\2yz\5L\'\2z{\7\n\2\2{|\5\16\b\2|\r\3\2\2\2}\u0080\5\20\t\2~\u0080\5@" +
                    "!\2\177}\3\2\2\2\177~\3\2\2\2\u0080\17\3\2\2\2\u0081\u0082\7\t\2\2\u0082" +
                    "\u0083\5(\25\2\u0083\u0084\7\5\2\2\u0084\u0087\3\2\2\2\u0085\u0087\5(" +
                    "\25\2\u0086\u0081\3\2\2\2\u0086\u0085\3\2\2\2\u0087\21\3\2\2\2\u0088\u0089" +
                    "\5L\'\2\u0089\23\3\2\2\2\u008a\u0090\5\26\f\2\u008b\u008c\7\t\2\2\u008c" +
                    "\u008d\5\26\f\2\u008d\u008e\7\5\2\2\u008e\u0090\3\2\2\2\u008f\u008a\3" +
                    "\2\2\2\u008f\u008b\3\2\2\2\u0090\25\3\2\2\2\u0091\u0096\5\30\r\2\u0092" +
                    "\u0093\5\30\r\2\u0093\u0094\5\30\r\2\u0094\u0096\3\2\2\2\u0095\u0091\3" +
                    "\2\2\2\u0095\u0092\3\2\2\2\u0096\27\3\2\2\2\u0097\u009b\5:\36\2\u0098" +
                    "\u009b\58\35\2\u0099\u009b\5\32\16\2\u009a\u0097\3\2\2\2\u009a\u0098\3" +
                    "\2\2\2\u009a\u0099\3\2\2\2\u009b\31\3\2\2\2\u009c\u009d\5\34\17\2\u009d" +
                    "\u009e\5\34\17\2\u009e\u00a0\3\2\2\2\u009f\u009c\3\2\2\2\u00a0\u00a1\3" +
                    "\2\2\2\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\33\3\2\2\2\u00a3" +
                    "\u00a6\7\30\2\2\u00a4\u00a7\5:\36\2\u00a5\u00a7\58\35\2\u00a6\u00a4\3" +
                    "\2\2\2\u00a6\u00a5\3\2\2\2\u00a7\u00b0\3\2\2\2\u00a8\u00ad\7\30\2\2\u00a9" +
                    "\u00ae\5L\'\2\u00aa\u00ae\5 \21\2\u00ab\u00ae\5B\"\2\u00ac\u00ae\5\36" +
                    "\20\2\u00ad\u00a9\3\2\2\2\u00ad\u00aa\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad" +
                    "\u00ac\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00a3\3\2\2\2\u00af\u00a8\3\2" +
                    "\2\2\u00b0\35\3\2\2\2\u00b1\u00b2\7\23\2\2\u00b2\u00b6\5 \21\2\u00b3\u00b4" +
                    "\7\17\2\2\u00b4\u00b6\5 \21\2\u00b5\u00b1\3\2\2\2\u00b5\u00b3\3\2\2\2" +
                    "\u00b6\37\3\2\2\2\u00b7\u00b8\t\2\2\2\u00b8!\3\2\2\2\u00b9\u00ba\5.\30" +
                    "\2\u00ba\u00bb\7-\2\2\u00bb\u00bc\5.\30\2\u00bc\u00de\3\2\2\2\u00bd\u00be" +
                    "\5.\30\2\u00be\u00bf\7,\2\2\u00bf\u00c0\5.\30\2\u00c0\u00de\3\2\2\2\u00c1" +
                    "\u00c2\5.\30\2\u00c2\u00c3\7+\2\2\u00c3\u00c4\5.\30\2\u00c4\u00de\3\2" +
                    "\2\2\u00c5\u00c6\5.\30\2\u00c6\u00c7\7\'\2\2\u00c7\u00c8\5$\23\2\u00c8" +
                    "\u00de\3\2\2\2\u00c9\u00ca\5.\30\2\u00ca\u00cb\7&\2\2\u00cb\u00cc\5.\30" +
                    "\2\u00cc\u00de\3\2\2\2\u00cd\u00ce\5.\30\2\u00ce\u00cf\7%\2\2\u00cf\u00d0" +
                    "\5.\30\2\u00d0\u00de\3\2\2\2\u00d1\u00d2\5.\30\2\u00d2\u00d3\7/\2\2\u00d3" +
                    "\u00d4\5.\30\2\u00d4\u00de\3\2\2\2\u00d5\u00d6\5.\30\2\u00d6\u00d7\7." +
                    "\2\2\u00d7\u00d8\5.\30\2\u00d8\u00de\3\2\2\2\u00d9\u00da\5.\30\2\u00da" +
                    "\u00db\7\31\2\2\u00db\u00dc\5.\30\2\u00dc\u00de\3\2\2\2\u00dd\u00b9\3" +
                    "\2\2\2\u00dd\u00bd\3\2\2\2\u00dd\u00c1\3\2\2\2\u00dd\u00c5\3\2\2\2\u00dd" +
                    "\u00c9\3\2\2\2\u00dd\u00cd\3\2\2\2\u00dd\u00d1\3\2\2\2\u00dd\u00d5\3\2" +
                    "\2\2\u00dd\u00d9\3\2\2\2\u00de#\3\2\2\2\u00df\u00e0\7\b\2\2\u00e0\u00e1" +
                    "\5&\24\2\u00e1\u00e2\7\4\2\2\u00e2%\3\2\2\2\u00e3\u00e8\5@!\2\u00e4\u00e5" +
                    "\7\7\2\2\u00e5\u00e7\5@!\2\u00e6\u00e4\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8" +
                    "\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\'\3\2\2\2\u00ea\u00e8\3\2\2\2" +
                    "\u00eb\u00f4\5*\26\2\u00ec\u00ef\5*\26\2\u00ed\u00ee\7)\2\2\u00ee\u00f0" +
                    "\5*\26\2\u00ef\u00ed\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1" +
                    "\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00eb\3\2\2\2\u00f3\u00ec\3\2" +
                    "\2\2\u00f4)\3\2\2\2\u00f5\u00fe\5,\27\2\u00f6\u00f9\5,\27\2\u00f7\u00f8" +
                    "\7*\2\2\u00f8\u00fa\5,\27\2\u00f9\u00f7\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb" +
                    "\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fe\3\2\2\2\u00fd\u00f5\3\2" +
                    "\2\2\u00fd\u00f6\3\2\2\2\u00fe+\3\2\2\2\u00ff\u010c\7\26\2\2\u0100\u010c" +
                    "\7\r\2\2\u0101\u010c\5\"\22\2\u0102\u0103\7\t\2\2\u0103\u0104\5(\25\2" +
                    "\u0104\u0105\7\5\2\2\u0105\u010c\3\2\2\2\u0106\u0107\7(\2\2\u0107\u0108" +
                    "\7\t\2\2\u0108\u0109\5(\25\2\u0109\u010a\7\5\2\2\u010a\u010c\3\2\2\2\u010b" +
                    "\u00ff\3\2\2\2\u010b\u0100\3\2\2\2\u010b\u0101\3\2\2\2\u010b\u0102\3\2" +
                    "\2\2\u010b\u0106\3\2\2\2\u010c-\3\2\2\2\u010d\u0114\5:\36\2\u010e\u0114" +
                    "\58\35\2\u010f\u0114\5\24\13\2\u0110\u0114\5\22\n\2\u0111\u0114\5\60\31" +
                    "\2\u0112\u0114\5@!\2\u0113\u010d\3\2\2\2\u0113\u010e\3\2\2\2\u0113\u010f" +
                    "\3\2\2\2\u0113\u0110\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0112\3\2\2\2\u0114" +
                    "/\3\2\2\2\u0115\u011f\5\62\32\2\u0116\u0117\5\62\32\2\u0117\u0118\7\33" +
                    "\2\2\u0118\u0119\5\62\32\2\u0119\u011f\3\2\2\2\u011a\u011b\5\62\32\2\u011b" +
                    "\u011c\7\32\2\2\u011c\u011d\5\62\32\2\u011d\u011f\3\2\2\2\u011e\u0115" +
                    "\3\2\2\2\u011e\u0116\3\2\2\2\u011e\u011a\3\2\2\2\u011f\61\3\2\2\2\u0120" +
                    "\u012a\5\64\33\2\u0121\u0122\5\64\33\2\u0122\u0123\7\35\2\2\u0123\u0124" +
                    "\5\64\33\2\u0124\u012a\3\2\2\2\u0125\u0126\5\64\33\2\u0126\u0127\7\34" +
                    "\2\2\u0127\u0128\5\64\33\2\u0128\u012a\3\2\2\2\u0129\u0120\3\2\2\2\u0129" +
                    "\u0121\3\2\2\2\u0129\u0125\3\2\2\2\u012a\63\3\2\2\2\u012b\u013d\5\66\34" +
                    "\2\u012c\u012d\5\66\34\2\u012d\u012e\7!\2\2\u012e\u012f\5\66\34\2\u012f" +
                    "\u013d\3\2\2\2\u0130\u0131\5\66\34\2\u0131\u0132\7 \2\2\u0132\u0133\5" +
                    "\66\34\2\u0133\u013d\3\2\2\2\u0134\u0135\5\66\34\2\u0135\u0136\7\37\2" +
                    "\2\u0136\u0137\5\66\34\2\u0137\u013d\3\2\2\2\u0138\u0139\5\66\34\2\u0139" +
                    "\u013a\7\36\2\2\u013a\u013b\5\66\34\2\u013b\u013d\3\2\2\2\u013c\u012b" +
                    "\3\2\2\2\u013c\u012c\3\2\2\2\u013c\u0130\3\2\2\2\u013c\u0134\3\2\2\2\u013c" +
                    "\u0138\3\2\2\2\u013d\65\3\2\2\2\u013e\u0145\5F$\2\u013f\u0140\7\t\2\2" +
                    "\u0140\u0141\5\60\31\2\u0141\u0142\7\5\2\2\u0142\u0145\3\2\2\2\u0143\u0145" +
                    "\5\22\n\2\u0144\u013e\3\2\2\2\u0144\u013f\3\2\2\2\u0144\u0143\3\2\2\2" +
                    "\u0145\67\3\2\2\2\u0146\u0147\7\f\2\2\u0147\u0148\5L\'\2\u0148\u0149\7" +
                    "\25\2\2\u01499\3\2\2\2\u014a\u014b\5> \2\u014b\u014c\7\6\2\2\u014c\u014d" +
                    "\5<\37\2\u014d;\3\2\2\2\u014e\u014f\7\60\2\2\u014f=\3\2\2\2\u0150\u0151" +
                    "\7\60\2\2\u0151?\3\2\2\2\u0152\u0156\5F$\2\u0153\u0156\5D#\2\u0154\u0156" +
                    "\5B\"\2\u0155\u0152\3\2\2\2\u0155\u0153\3\2\2\2\u0155\u0154\3\2\2\2\u0156" +
                    "A\3\2\2\2\u0157\u0158\7#\2\2\u0158C\3\2\2\2\u0159\u015a\7\60\2\2\u015a" +
                    "E\3\2\2\2\u015b\u015e\5J&\2\u015c\u015e\5H%\2\u015d\u015b\3\2\2\2\u015d" +
                    "\u015c\3\2\2\2\u015eG\3\2\2\2\u015f\u0160\7\"\2\2\u0160I\3\2\2\2\u0161" +
                    "\u0162\7$\2\2\u0162K\3\2\2\2\u0163\u0164\7\60\2\2\u0164M\3\2\2\2\34fw" +
                    "\177\u0086\u008f\u0095\u009a\u00a1\u00a6\u00ad\u00af\u00b5\u00dd\u00e8" +
                    "\u00f1\u00f3\u00fb\u00fd\u010b\u0113\u011e\u0129\u013c\u0144\u0155\u015d";
    public static final ATN _ATN =
            ATNSimulator.deserialize(_serializedATN.toCharArray());

    protected static final DFA[] _decisionToDFA;
    static
    {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++)
        {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();

    public ActionParser(TokenStream input)
    {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName()
    {
        return "Action.g4";
    }

    @Override
    public String[] getTokenNames()
    {
        return tokenNames;
    }

    @Override
    public String[] getRuleNames()
    {
        return ruleNames;
    }

    @Override
    public ATN getATN()
    {
        return _ATN;
    }

    public final ActionContext action() throws RecognitionException
    {
        ActionContext _localctx = new ActionContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_action);
        try
        {
            setState(100);
            switch (_input.LA(1))
            {
            case 9:
                _localctx = new TreeActContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(76);
                match(9);
                setState(77);
                para();
                setState(78);
                match(3);
            }
            break;
            case 16:
                _localctx = new SelectContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(80);
                match(16);
                setState(81);
                ne();
                setState(82);
                match(5);
                setState(83);
                json();
                setState(84);
                match(3);
            }
            break;
            case 18:
                _localctx = new CommitContext(_localctx);
                enterOuterAlt(_localctx, 3);
            {
                setState(86);
                match(18);
                setState(87);
                ne();
                setState(88);
                match(5);
                setState(89);
                json();
                setState(90);
                match(3);
            }
            break;
            case 1:
                _localctx = new OnSelectContext(_localctx);
                enterOuterAlt(_localctx, 4);
            {
                setState(92);
                match(1);
                setState(93);
                init();
                setState(94);
                match(3);
            }
            break;
            case 12:
                _localctx = new VerContext(_localctx);
                enterOuterAlt(_localctx, 5);
            {
                setState(96);
                match(12);
                setState(97);
                ne();
                setState(98);
                match(3);
            }
            break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final NeContext ne() throws RecognitionException
    {
        NeContext _localctx = new NeContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_ne);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(102);
                match(15);
                setState(103);
                dnexp();
                setState(104);
                match(3);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final JsonContext json() throws RecognitionException
    {
        JsonContext _localctx = new JsonContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_json);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(106);
                match(14);
                setState(107);
                jsonObject();
                setState(108);
                match(3);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final JsonObjectContext jsonObject() throws RecognitionException
    {
        JsonObjectContext _localctx = new JsonObjectContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_jsonObject);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(110);
                match(ALL);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ParaContext para() throws RecognitionException
    {
        ParaContext _localctx = new ParaContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_para);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(112);
                exp();
                setState(115);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do
                {
                    {
                        {
                            setState(113);
                            match(5);
                            setState(114);
                            exp();
                        }
                    }
                    setState(117);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                while (_la == 5);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ExpContext exp() throws RecognitionException
    {
        ExpContext _localctx = new ExpContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_exp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(119);
                name();
                setState(120);
                match(8);
                setState(121);
                expvalue();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ExpvalueContext expvalue() throws RecognitionException
    {
        ExpvalueContext _localctx = new ExpvalueContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_expvalue);
        try
        {
            setState(125);
            switch (getInterpreter().adaptivePredict(_input, 2, _ctx))
            {
            case 1:
                enterOuterAlt(_localctx, 1);
            {
                setState(123);
                init();
            }
            break;

            case 2:
                enterOuterAlt(_localctx, 2);
            {
                setState(124);
                value();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final InitContext init() throws RecognitionException
    {
        InitContext _localctx = new InitContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_init);
        try
        {
            setState(132);
            switch (getInterpreter().adaptivePredict(_input, 3, _ctx))
            {
            case 1:
                enterOuterAlt(_localctx, 1);
            {
                setState(127);
                match(7);
                setState(128);
                boolean_expr();
                setState(129);
                match(3);
            }
            break;

            case 2:
                enterOuterAlt(_localctx, 2);
            {
                setState(131);
                boolean_expr();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final DbexpContext dbexp() throws RecognitionException
    {
        DbexpContext _localctx = new DbexpContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_dbexp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(134);
                name();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final DnexpContext dnexp() throws RecognitionException
    {
        DnexpContext _localctx = new DnexpContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_dnexp);
        try
        {
            setState(141);
            switch (_input.LA(1))
            {
            case 10:
            case SLASH:
            case TEXT:
                enterOuterAlt(_localctx, 1);
            {
                setState(136);
                dnsec();
            }
            break;
            case 7:
                enterOuterAlt(_localctx, 2);
            {
                setState(137);
                match(7);
                setState(138);
                dnsec();
                setState(139);
                match(3);
            }
            break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final DnsecContext dnsec() throws RecognitionException
    {
        DnsecContext _localctx = new DnsecContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_dnsec);
        try
        {
            setState(147);
            switch (getInterpreter().adaptivePredict(_input, 5, _ctx))
            {
            case 1:
                _localctx = new OneDNTermContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(143);
                dnterm();
            }
            break;

            case 2:
                _localctx = new TwoDNTermContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(144);
                dnterm();
                setState(145);
                dnterm();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final DntermContext dnterm() throws RecognitionException
    {
        DntermContext _localctx = new DntermContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_dnterm);
        try
        {
            setState(152);
            switch (_input.LA(1))
            {
            case TEXT:
                enterOuterAlt(_localctx, 1);
            {
                setState(149);
                moexp();
            }
            break;
            case 10:
                enterOuterAlt(_localctx, 2);
            {
                setState(150);
                localexp();
            }
            break;
            case SLASH:
                enterOuterAlt(_localctx, 3);
            {
                setState(151);
                dnele();
            }
            break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final DneleContext dnele() throws RecognitionException
    {
        DneleContext _localctx = new DneleContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_dnele);
        try
        {
            int _alt;
            _localctx = new DnElementsContext(_localctx);
            enterOuterAlt(_localctx, 1);
            {
                setState(157);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
                do
                {
                    switch (_alt)
                    {
                    case 1:
                    {
                        {
                            setState(154);
                            element();
                            setState(155);
                            element();
                        }
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                    }
                    setState(159);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 7, _ctx);
                }
                while (_alt != 2 && _alt != -1);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ElementContext element() throws RecognitionException
    {
        ElementContext _localctx = new ElementContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_element);
        try
        {
            setState(173);
            switch (getInterpreter().adaptivePredict(_input, 10, _ctx))
            {
            case 1:
                _localctx = new SlashExpContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(161);
                match(SLASH);
                setState(164);
                switch (_input.LA(1))
                {
                case TEXT:
                {
                    setState(162);
                    moexp();
                }
                break;
                case 10:
                {
                    setState(163);
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
                setState(166);
                match(SLASH);
                setState(171);
                switch (_input.LA(1))
                {
                case TEXT:
                {
                    setState(167);
                    name();
                }
                break;
                case MULTI:
                case INT:
                {
                    setState(168);
                    index();
                }
                break;
                case STRING:
                {
                    setState(169);
                    dbString();
                }
                break;
                case 13:
                case 17:
                {
                    setState(170);
                    neid();
                }
                break;
                default:
                    throw new NoViableAltException(this);
                }
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final NeidContext neid() throws RecognitionException
    {
        NeidContext _localctx = new NeidContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_neid);
        try
        {
            setState(179);
            switch (_input.LA(1))
            {
            case 17:
                enterOuterAlt(_localctx, 1);
            {
                setState(175);
                match(17);
                setState(176);
                index();
            }
            break;
            case 13:
                enterOuterAlt(_localctx, 2);
            {
                setState(177);
                match(13);
                setState(178);
                index();
            }
            break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final IndexContext index() throws RecognitionException
    {
        IndexContext _localctx = new IndexContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_index);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(181);
                _la = _input.LA(1);
                if (!(_la == MULTI || _la == INT))
                {
                    _errHandler.recoverInline(this);
                }
                consume();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ComparasionContext comparasion() throws RecognitionException
    {
        ComparasionContext _localctx = new ComparasionContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_comparasion);
        try
        {
            setState(219);
            switch (getInterpreter().adaptivePredict(_input, 12, _ctx))
            {
            case 1:
                _localctx = new EqualContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(183);
                extendedExp();
                setState(184);
                match(EQUAL);
                setState(185);
                extendedExp();
            }
            break;

            case 2:
                _localctx = new BiggerContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(187);
                extendedExp();
                setState(188);
                match(BIGGER);
                setState(189);
                extendedExp();
            }
            break;

            case 3:
                _localctx = new SmallerContext(_localctx);
                enterOuterAlt(_localctx, 3);
            {
                setState(191);
                extendedExp();
                setState(192);
                match(SMALLER);
                setState(193);
                extendedExp();
            }
            break;

            case 4:
                _localctx = new InContext(_localctx);
                enterOuterAlt(_localctx, 4);
            {
                setState(195);
                extendedExp();
                setState(196);
                match(IN);
                setState(197);
                array();
            }
            break;

            case 5:
                _localctx = new ContainsContext(_localctx);
                enterOuterAlt(_localctx, 5);
            {
                setState(199);
                extendedExp();
                setState(200);
                match(CONTAINS);
                setState(201);
                extendedExp();
            }
            break;

            case 6:
                _localctx = new NotEqualContext(_localctx);
                enterOuterAlt(_localctx, 6);
            {
                setState(203);
                extendedExp();
                setState(204);
                match(NOTEQUAL);
                setState(205);
                extendedExp();
            }
            break;

            case 7:
                _localctx = new BOrEContext(_localctx);
                enterOuterAlt(_localctx, 7);
            {
                setState(207);
                extendedExp();
                setState(208);
                match(BIGGEROREQUAL);
                setState(209);
                extendedExp();
            }
            break;

            case 8:
                _localctx = new SOrEContext(_localctx);
                enterOuterAlt(_localctx, 8);
            {
                setState(211);
                extendedExp();
                setState(212);
                match(SMALLEROREQUAL);
                setState(213);
                extendedExp();
            }
            break;

            case 9:
                _localctx = new LikeContext(_localctx);
                enterOuterAlt(_localctx, 9);
            {
                setState(215);
                extendedExp();
                setState(216);
                match(LIKE);
                setState(217);
                extendedExp();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ArrayContext array() throws RecognitionException
    {
        ArrayContext _localctx = new ArrayContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_array);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(221);
                match(6);
                setState(222);
                arrayexp();
                setState(223);
                match(2);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ArrayexpContext arrayexp() throws RecognitionException
    {
        ArrayexpContext _localctx = new ArrayexpContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_arrayexp);
        int _la;
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(225);
                value();
                setState(230);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == 5)
                {
                    {
                        {
                            setState(226);
                            match(5);
                            setState(227);
                            value();
                        }
                    }
                    setState(232);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final Boolean_exprContext boolean_expr() throws RecognitionException
    {
        Boolean_exprContext _localctx = new Boolean_exprContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_boolean_expr);
        int _la;
        try
        {
            setState(241);
            switch (getInterpreter().adaptivePredict(_input, 15, _ctx))
            {
            case 1:
                _localctx = new BoolTermContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(233);
                boolean_term();
            }
            break;

            case 2:
                _localctx = new OrContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(234);
                boolean_term();
                setState(237);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do
                {
                    {
                        {
                            setState(235);
                            match(OR);
                            setState(236);
                            boolean_term();
                        }
                    }
                    setState(239);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                while (_la == OR);
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final Boolean_termContext boolean_term() throws RecognitionException
    {
        Boolean_termContext _localctx = new Boolean_termContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_boolean_term);
        int _la;
        try
        {
            setState(251);
            switch (getInterpreter().adaptivePredict(_input, 17, _ctx))
            {
            case 1:
                _localctx = new BoolElementContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(243);
                boolean_element();
            }
            break;

            case 2:
                _localctx = new AndContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(244);
                boolean_element();
                setState(247);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do
                {
                    {
                        {
                            setState(245);
                            match(AND);
                            setState(246);
                            boolean_element();
                        }
                    }
                    setState(249);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                while (_la == AND);
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final Boolean_elementContext boolean_element() throws RecognitionException
    {
        Boolean_elementContext _localctx = new Boolean_elementContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_boolean_element);
        try
        {
            setState(265);
            switch (getInterpreter().adaptivePredict(_input, 18, _ctx))
            {
            case 1:
                _localctx = new TrueValueContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(253);
                match(20);
            }
            break;

            case 2:
                _localctx = new FalseValueContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(254);
                match(11);
            }
            break;

            case 3:
                _localctx = new ReadCompareContext(_localctx);
                enterOuterAlt(_localctx, 3);
            {
                setState(255);
                comparasion();
            }
            break;

            case 4:
                _localctx = new BoolExprContext(_localctx);
                enterOuterAlt(_localctx, 4);
            {
                setState(256);
                match(7);
                setState(257);
                boolean_expr();
                setState(258);
                match(3);
            }
            break;

            case 5:
                _localctx = new NotContext(_localctx);
                enterOuterAlt(_localctx, 5);
            {
                setState(260);
                match(NOT);
                setState(261);
                match(7);
                setState(262);
                boolean_expr();
                setState(263);
                match(3);
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ExtendedExpContext extendedExp() throws RecognitionException
    {
        ExtendedExpContext _localctx = new ExtendedExpContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_extendedExp);
        try
        {
            setState(273);
            switch (getInterpreter().adaptivePredict(_input, 19, _ctx))
            {
            case 1:
                enterOuterAlt(_localctx, 1);
            {
                setState(267);
                moexp();
            }
            break;

            case 2:
                enterOuterAlt(_localctx, 2);
            {
                setState(268);
                localexp();
            }
            break;

            case 3:
                enterOuterAlt(_localctx, 3);
            {
                setState(269);
                dnexp();
            }
            break;

            case 4:
                enterOuterAlt(_localctx, 4);
            {
                setState(270);
                dbexp();
            }
            break;

            case 5:
                enterOuterAlt(_localctx, 5);
            {
                setState(271);
                calculateExp();
            }
            break;

            case 6:
                enterOuterAlt(_localctx, 6);
            {
                setState(272);
                value();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final CalculateExpContext calculateExp() throws RecognitionException
    {
        CalculateExpContext _localctx = new CalculateExpContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_calculateExp);
        try
        {
            setState(284);
            switch (getInterpreter().adaptivePredict(_input, 20, _ctx))
            {
            case 1:
                _localctx = new CalcaddTContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(275);
                calcAddEle();
            }
            break;

            case 2:
                _localctx = new AddContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(276);
                calcAddEle();
                setState(277);
                match(ADD);
                setState(278);
                calcAddEle();
            }
            break;

            case 3:
                _localctx = new MinusContext(_localctx);
                enterOuterAlt(_localctx, 3);
            {
                setState(280);
                calcAddEle();
                setState(281);
                match(MINUS);
                setState(282);
                calcAddEle();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final CalcAddEleContext calcAddEle() throws RecognitionException
    {
        CalcAddEleContext _localctx = new CalcAddEleContext(_ctx, getState());
        enterRule(_localctx, 48, RULE_calcAddEle);
        try
        {
            setState(295);
            switch (getInterpreter().adaptivePredict(_input, 21, _ctx))
            {
            case 1:
                _localctx = new CalcmultiContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(286);
                calcMultiEle();
            }
            break;

            case 2:
                _localctx = new MultiContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(287);
                calcMultiEle();
                setState(288);
                match(MULTI);
                setState(289);
                calcMultiEle();
            }
            break;

            case 3:
                _localctx = new DividContext(_localctx);
                enterOuterAlt(_localctx, 3);
            {
                setState(291);
                calcMultiEle();
                setState(292);
                match(DIVID);
                setState(293);
                calcMultiEle();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final CalcMultiEleContext calcMultiEle() throws RecognitionException
    {
        CalcMultiEleContext _localctx = new CalcMultiEleContext(_ctx, getState());
        enterRule(_localctx, 50, RULE_calcMultiEle);
        try
        {
            setState(314);
            switch (getInterpreter().adaptivePredict(_input, 22, _ctx))
            {
            case 1:
                _localctx = new CalcTContext(_localctx);
                enterOuterAlt(_localctx, 1);
            {
                setState(297);
                calcTerm();
            }
            break;

            case 2:
                _localctx = new LeftContext(_localctx);
                enterOuterAlt(_localctx, 2);
            {
                setState(298);
                calcTerm();
                setState(299);
                match(LEFT);
                setState(300);
                calcTerm();
            }
            break;

            case 3:
                _localctx = new RightContext(_localctx);
                enterOuterAlt(_localctx, 3);
            {
                setState(302);
                calcTerm();
                setState(303);
                match(RIGHT);
                setState(304);
                calcTerm();
            }
            break;

            case 4:
                _localctx = new AmpersandContext(_localctx);
                enterOuterAlt(_localctx, 4);
            {
                setState(306);
                calcTerm();
                setState(307);
                match(AMPERSAND);
                setState(308);
                calcTerm();
            }
            break;

            case 5:
                _localctx = new BarContext(_localctx);
                enterOuterAlt(_localctx, 5);
            {
                setState(310);
                calcTerm();
                setState(311);
                match(BAR);
                setState(312);
                calcTerm();
            }
            break;
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final CalcTermContext calcTerm() throws RecognitionException
    {
        CalcTermContext _localctx = new CalcTermContext(_ctx, getState());
        enterRule(_localctx, 52, RULE_calcTerm);
        try
        {
            setState(322);
            switch (_input.LA(1))
            {
            case HEX:
            case INT:
                enterOuterAlt(_localctx, 1);
            {
                setState(316);
                intValue();
            }
            break;
            case 7:
                enterOuterAlt(_localctx, 2);
            {
                setState(317);
                match(7);
                setState(318);
                calculateExp();
                setState(319);
                match(3);
            }
            break;
            case TEXT:
                enterOuterAlt(_localctx, 3);
            {
                setState(321);
                dbexp();
            }
            break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final LocalexpContext localexp() throws RecognitionException
    {
        LocalexpContext _localctx = new LocalexpContext(_ctx, getState());
        enterRule(_localctx, 54, RULE_localexp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(324);
                match(10);
                setState(325);
                name();
                setState(326);
                match(19);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final MoexpContext moexp() throws RecognitionException
    {
        MoexpContext _localctx = new MoexpContext(_ctx, getState());
        enterRule(_localctx, 56, RULE_moexp);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(328);
                className();
                setState(329);
                match(4);
                setState(330);
                propertyName();
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final PropertyNameContext propertyName() throws RecognitionException
    {
        PropertyNameContext _localctx = new PropertyNameContext(_ctx, getState());
        enterRule(_localctx, 58, RULE_propertyName);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(332);
                match(TEXT);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ClassNameContext className() throws RecognitionException
    {
        ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
        enterRule(_localctx, 60, RULE_className);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(334);
                match(TEXT);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final ValueContext value() throws RecognitionException
    {
        ValueContext _localctx = new ValueContext(_ctx, getState());
        enterRule(_localctx, 62, RULE_value);
        try
        {
            setState(339);
            switch (_input.LA(1))
            {
            case HEX:
            case INT:
                enterOuterAlt(_localctx, 1);
            {
                setState(336);
                intValue();
            }
            break;
            case TEXT:
                enterOuterAlt(_localctx, 2);
            {
                setState(337);
                pureString();
            }
            break;
            case STRING:
                enterOuterAlt(_localctx, 3);
            {
                setState(338);
                dbString();
            }
            break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final DbStringContext dbString() throws RecognitionException
    {
        DbStringContext _localctx = new DbStringContext(_ctx, getState());
        enterRule(_localctx, 64, RULE_dbString);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(341);
                match(STRING);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final PureStringContext pureString() throws RecognitionException
    {
        PureStringContext _localctx = new PureStringContext(_ctx, getState());
        enterRule(_localctx, 66, RULE_pureString);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(343);
                match(TEXT);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final IntValueContext intValue() throws RecognitionException
    {
        IntValueContext _localctx = new IntValueContext(_ctx, getState());
        enterRule(_localctx, 68, RULE_intValue);
        try
        {
            setState(347);
            switch (_input.LA(1))
            {
            case INT:
                enterOuterAlt(_localctx, 1);
            {
                setState(345);
                octValue();
            }
            break;
            case HEX:
                enterOuterAlt(_localctx, 2);
            {
                setState(346);
                hexValue();
            }
            break;
            default:
                throw new NoViableAltException(this);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final HexValueContext hexValue() throws RecognitionException
    {
        HexValueContext _localctx = new HexValueContext(_ctx, getState());
        enterRule(_localctx, 70, RULE_hexValue);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(349);
                match(HEX);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final OctValueContext octValue() throws RecognitionException
    {
        OctValueContext _localctx = new OctValueContext(_ctx, getState());
        enterRule(_localctx, 72, RULE_octValue);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(351);
                match(INT);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public final NameContext name() throws RecognitionException
    {
        NameContext _localctx = new NameContext(_ctx, getState());
        enterRule(_localctx, 74, RULE_name);
        try
        {
            enterOuterAlt(_localctx, 1);
            {
                setState(353);
                match(TEXT);
            }
        }
        catch (RecognitionException re)
        {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        }
        finally
        {
            exitRule();
        }
        return _localctx;
    }

    public static class ActionContext extends ParserRuleContext
    {
        public ActionContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ActionContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_action;
        }

        public void copyFrom(ActionContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class OnSelectContext extends ActionContext
    {
        public OnSelectContext(ActionContext ctx)
        {
            copyFrom(ctx);
        }

        public InitContext init()
        {
            return getRuleContext(InitContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterOnSelect(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitOnSelect(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitOnSelect(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class CommitContext extends ActionContext
    {
        public CommitContext(ActionContext ctx)
        {
            copyFrom(ctx);
        }

        public NeContext ne()
        {
            return getRuleContext(NeContext.class, 0);
        }

        public JsonContext json()
        {
            return getRuleContext(JsonContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterCommit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitCommit(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitCommit(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class TreeActContext extends ActionContext
    {
        public TreeActContext(ActionContext ctx)
        {
            copyFrom(ctx);
        }

        public ParaContext para()
        {
            return getRuleContext(ParaContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterTreeAct(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitTreeAct(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitTreeAct(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class SelectContext extends ActionContext
    {
        public SelectContext(ActionContext ctx)
        {
            copyFrom(ctx);
        }

        public NeContext ne()
        {
            return getRuleContext(NeContext.class, 0);
        }

        public JsonContext json()
        {
            return getRuleContext(JsonContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterSelect(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitSelect(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitSelect(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class VerContext extends ActionContext
    {
        public VerContext(ActionContext ctx)
        {
            copyFrom(ctx);
        }

        public NeContext ne()
        {
            return getRuleContext(NeContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterVer(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitVer(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitVer(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class NeContext extends ParserRuleContext
    {
        public NeContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public DnexpContext dnexp()
        {
            return getRuleContext(DnexpContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_ne;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterNe(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitNe(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitNe(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class JsonContext extends ParserRuleContext
    {
        public JsonContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public JsonObjectContext jsonObject()
        {
            return getRuleContext(JsonObjectContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_json;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterJson(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitJson(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitJson(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class JsonObjectContext extends ParserRuleContext
    {
        public JsonObjectContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode ALL()
        {
            return getToken(ActionParser.ALL, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_jsonObject;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterJsonObject(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitJsonObject(
                        this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitJsonObject(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ParaContext extends ParserRuleContext
    {
        public ParaContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public List<ExpContext> exp()
        {
            return getRuleContexts(ExpContext.class);
        }

        public ExpContext exp(int i)
        {
            return getRuleContext(ExpContext.class, i);
        }

        @Override public int getRuleIndex()
        {
            return RULE_para;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterPara(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitPara(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitPara(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ExpContext extends ParserRuleContext
    {
        public ExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ExpvalueContext expvalue()
        {
            return getRuleContext(ExpvalueContext.class, 0);
        }

        public NameContext name()
        {
            return getRuleContext(NameContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_exp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitExp(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ExpvalueContext extends ParserRuleContext
    {
        public ExpvalueContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ValueContext value()
        {
            return getRuleContext(ValueContext.class, 0);
        }

        public InitContext init()
        {
            return getRuleContext(InitContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_expvalue;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterExpvalue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitExpvalue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitExpvalue(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class InitContext extends ParserRuleContext
    {
        public InitContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public Boolean_exprContext boolean_expr()
        {
            return getRuleContext(Boolean_exprContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_init;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterInit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitInit(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitInit(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class DbexpContext extends ParserRuleContext
    {
        public DbexpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public NameContext name()
        {
            return getRuleContext(NameContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_dbexp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterDbexp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitDbexp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitDbexp(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class DnexpContext extends ParserRuleContext
    {
        public DnexpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public DnsecContext dnsec()
        {
            return getRuleContext(DnsecContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_dnexp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterDnexp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitDnexp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitDnexp(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class DnsecContext extends ParserRuleContext
    {
        public DnsecContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public DnsecContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_dnsec;
        }

        public void copyFrom(DnsecContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class OneDNTermContext extends DnsecContext
    {
        public OneDNTermContext(DnsecContext ctx)
        {
            copyFrom(ctx);
        }

        public DntermContext dnterm()
        {
            return getRuleContext(DntermContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterOneDNTerm(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitOneDNTerm(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitOneDNTerm(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class TwoDNTermContext extends DnsecContext
    {
        public TwoDNTermContext(DnsecContext ctx)
        {
            copyFrom(ctx);
        }

        public List<DntermContext> dnterm()
        {
            return getRuleContexts(DntermContext.class);
        }

        public DntermContext dnterm(int i)
        {
            return getRuleContext(DntermContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterTwoDNTerm(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitTwoDNTerm(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitTwoDNTerm(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class DntermContext extends ParserRuleContext
    {
        public DntermContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public LocalexpContext localexp()
        {
            return getRuleContext(LocalexpContext.class, 0);
        }

        public MoexpContext moexp()
        {
            return getRuleContext(MoexpContext.class, 0);
        }

        public DneleContext dnele()
        {
            return getRuleContext(DneleContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_dnterm;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterDnterm(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitDnterm(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitDnterm(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class DneleContext extends ParserRuleContext
    {
        public DneleContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public DneleContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_dnele;
        }

        public void copyFrom(DneleContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class DnElementsContext extends DneleContext
    {
        public DnElementsContext(DneleContext ctx)
        {
            copyFrom(ctx);
        }

        public List<ElementContext> element()
        {
            return getRuleContexts(ElementContext.class);
        }

        public ElementContext element(int i)
        {
            return getRuleContext(ElementContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterDnElements(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitDnElements(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitDnElements(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ElementContext extends ParserRuleContext
    {
        public ElementContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ElementContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_element;
        }

        public void copyFrom(ElementContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class SlashExpContext extends ElementContext
    {
        public SlashExpContext(ElementContext ctx)
        {
            copyFrom(ctx);
        }

        public LocalexpContext localexp()
        {
            return getRuleContext(LocalexpContext.class, 0);
        }

        public MoexpContext moexp()
        {
            return getRuleContext(MoexpContext.class, 0);
        }

        public TerminalNode SLASH()
        {
            return getToken(ActionParser.SLASH, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterSlashExp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitSlashExp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitSlashExp(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class SlashValueContext extends ElementContext
    {
        public SlashValueContext(ElementContext ctx)
        {
            copyFrom(ctx);
        }

        public IndexContext index()
        {
            return getRuleContext(IndexContext.class, 0);
        }

        public DbStringContext dbString()
        {
            return getRuleContext(DbStringContext.class, 0);
        }

        public NeidContext neid()
        {
            return getRuleContext(NeidContext.class, 0);
        }

        public TerminalNode SLASH()
        {
            return getToken(ActionParser.SLASH, 0);
        }

        public NameContext name()
        {
            return getRuleContext(NameContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterSlashValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitSlashValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitSlashValue(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class NeidContext extends ParserRuleContext
    {
        public NeidContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public IndexContext index()
        {
            return getRuleContext(IndexContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_neid;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterNeid(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitNeid(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitNeid(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class IndexContext extends ParserRuleContext
    {
        public IndexContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode INT()
        {
            return getToken(ActionParser.INT, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_index;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterIndex(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitIndex(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitIndex(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ComparasionContext extends ParserRuleContext
    {
        public ComparasionContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ComparasionContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_comparasion;
        }

        public void copyFrom(ComparasionContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class BOrEContext extends ComparasionContext
    {
        public BOrEContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public TerminalNode BIGGEROREQUAL()
        {
            return getToken(ActionParser.BIGGEROREQUAL, 0);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterBOrE(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitBOrE(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitBOrE(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class BiggerContext extends ComparasionContext
    {
        public BiggerContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public TerminalNode BIGGER()
        {
            return getToken(ActionParser.BIGGER, 0);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterBigger(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitBigger(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitBigger(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class SOrEContext extends ComparasionContext
    {
        public SOrEContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public TerminalNode SMALLEROREQUAL()
        {
            return getToken(ActionParser.SMALLEROREQUAL, 0);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterSOrE(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitSOrE(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitSOrE(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class EqualContext extends ComparasionContext
    {
        public EqualContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        public TerminalNode EQUAL()
        {
            return getToken(ActionParser.EQUAL, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterEqual(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitEqual(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitEqual(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ContainsContext extends ComparasionContext
    {
        public ContainsContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public TerminalNode CONTAINS()
        {
            return getToken(ActionParser.CONTAINS, 0);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterContains(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitContains(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitContains(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class NotEqualContext extends ComparasionContext
    {
        public NotEqualContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        public TerminalNode NOTEQUAL()
        {
            return getToken(ActionParser.NOTEQUAL, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterNotEqual(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitNotEqual(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitNotEqual(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class LikeContext extends ComparasionContext
    {
        public LikeContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        public TerminalNode LIKE()
        {
            return getToken(ActionParser.LIKE, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterLike(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitLike(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitLike(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class InContext extends ComparasionContext
    {
        public InContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public TerminalNode IN()
        {
            return getToken(ActionParser.IN, 0);
        }

        public ExtendedExpContext extendedExp()
        {
            return getRuleContext(ExtendedExpContext.class, 0);
        }

        public ArrayContext array()
        {
            return getRuleContext(ArrayContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterIn(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitIn(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitIn(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class SmallerContext extends ComparasionContext
    {
        public SmallerContext(ComparasionContext ctx)
        {
            copyFrom(ctx);
        }

        public ExtendedExpContext extendedExp(int i)
        {
            return getRuleContext(ExtendedExpContext.class, i);
        }

        public TerminalNode SMALLER()
        {
            return getToken(ActionParser.SMALLER, 0);
        }

        public List<ExtendedExpContext> extendedExp()
        {
            return getRuleContexts(ExtendedExpContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterSmaller(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitSmaller(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitSmaller(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ArrayContext extends ParserRuleContext
    {
        public ArrayContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ArrayexpContext arrayexp()
        {
            return getRuleContext(ArrayexpContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_array;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterArray(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitArray(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitArray(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ArrayexpContext extends ParserRuleContext
    {
        public ArrayexpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ValueContext value(int i)
        {
            return getRuleContext(ValueContext.class, i);
        }

        public List<ValueContext> value()
        {
            return getRuleContexts(ValueContext.class);
        }

        @Override public int getRuleIndex()
        {
            return RULE_arrayexp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterArrayexp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitArrayexp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitArrayexp(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class Boolean_exprContext extends ParserRuleContext
    {
        public Boolean_exprContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public Boolean_exprContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_boolean_expr;
        }

        public void copyFrom(Boolean_exprContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class BoolTermContext extends Boolean_exprContext
    {
        public BoolTermContext(Boolean_exprContext ctx)
        {
            copyFrom(ctx);
        }

        public Boolean_termContext boolean_term()
        {
            return getRuleContext(Boolean_termContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterBoolTerm(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitBoolTerm(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitBoolTerm(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class OrContext extends Boolean_exprContext
    {
        public OrContext(Boolean_exprContext ctx)
        {
            copyFrom(ctx);
        }

        public Boolean_termContext boolean_term(int i)
        {
            return getRuleContext(Boolean_termContext.class, i);
        }

        public List<TerminalNode> OR()
        {
            return getTokens(ActionParser.OR);
        }

        public List<Boolean_termContext> boolean_term()
        {
            return getRuleContexts(Boolean_termContext.class);
        }

        public TerminalNode OR(int i)
        {
            return getToken(ActionParser.OR, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterOr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitOr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitOr(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class Boolean_termContext extends ParserRuleContext
    {
        public Boolean_termContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public Boolean_termContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_boolean_term;
        }

        public void copyFrom(Boolean_termContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class BoolElementContext extends Boolean_termContext
    {
        public BoolElementContext(Boolean_termContext ctx)
        {
            copyFrom(ctx);
        }

        public Boolean_elementContext boolean_element()
        {
            return getRuleContext(Boolean_elementContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterBoolElement(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitBoolElement(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitBoolElement(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class AndContext extends Boolean_termContext
    {
        public AndContext(Boolean_termContext ctx)
        {
            copyFrom(ctx);
        }

        public Boolean_elementContext boolean_element(int i)
        {
            return getRuleContext(Boolean_elementContext.class, i);
        }

        public List<TerminalNode> AND()
        {
            return getTokens(ActionParser.AND);
        }

        public List<Boolean_elementContext> boolean_element()
        {
            return getRuleContexts(Boolean_elementContext.class);
        }

        public TerminalNode AND(int i)
        {
            return getToken(ActionParser.AND, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterAnd(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitAnd(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitAnd(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class Boolean_elementContext extends ParserRuleContext
    {
        public Boolean_elementContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public Boolean_elementContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_boolean_element;
        }

        public void copyFrom(Boolean_elementContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class NotContext extends Boolean_elementContext
    {
        public NotContext(Boolean_elementContext ctx)
        {
            copyFrom(ctx);
        }

        public Boolean_exprContext boolean_expr()
        {
            return getRuleContext(Boolean_exprContext.class, 0);
        }

        public TerminalNode NOT()
        {
            return getToken(ActionParser.NOT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterNot(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitNot(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitNot(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class TrueValueContext extends Boolean_elementContext
    {
        public TrueValueContext(Boolean_elementContext ctx)
        {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterTrueValue(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitTrueValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitTrueValue(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class FalseValueContext extends Boolean_elementContext
    {
        public FalseValueContext(Boolean_elementContext ctx)
        {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterFalseValue(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitFalseValue(
                        this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitFalseValue(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class BoolExprContext extends Boolean_elementContext
    {
        public BoolExprContext(Boolean_elementContext ctx)
        {
            copyFrom(ctx);
        }

        public Boolean_exprContext boolean_expr()
        {
            return getRuleContext(Boolean_exprContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterBoolExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitBoolExpr(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitBoolExpr(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ReadCompareContext extends Boolean_elementContext
    {
        public ReadCompareContext(Boolean_elementContext ctx)
        {
            copyFrom(ctx);
        }

        public ComparasionContext comparasion()
        {
            return getRuleContext(ComparasionContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterReadCompare(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitReadCompare(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitReadCompare(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ExtendedExpContext extends ParserRuleContext
    {
        public ExtendedExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public DbexpContext dbexp()
        {
            return getRuleContext(DbexpContext.class, 0);
        }

        public ValueContext value()
        {
            return getRuleContext(ValueContext.class, 0);
        }

        public LocalexpContext localexp()
        {
            return getRuleContext(LocalexpContext.class, 0);
        }

        public MoexpContext moexp()
        {
            return getRuleContext(MoexpContext.class, 0);
        }

        public DnexpContext dnexp()
        {
            return getRuleContext(DnexpContext.class, 0);
        }

        public CalculateExpContext calculateExp()
        {
            return getRuleContext(CalculateExpContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_extendedExp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterExtendedExp(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitExtendedExp(
                        this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitExtendedExp(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class CalculateExpContext extends ParserRuleContext
    {
        public CalculateExpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public CalculateExpContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_calculateExp;
        }

        public void copyFrom(CalculateExpContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class MinusContext extends CalculateExpContext
    {
        public MinusContext(CalculateExpContext ctx)
        {
            copyFrom(ctx);
        }

        public CalcAddEleContext calcAddEle(int i)
        {
            return getRuleContext(CalcAddEleContext.class, i);
        }

        public TerminalNode MINUS()
        {
            return getToken(ActionParser.MINUS, 0);
        }

        public List<CalcAddEleContext> calcAddEle()
        {
            return getRuleContexts(CalcAddEleContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterMinus(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitMinus(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitMinus(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class CalcaddTContext extends CalculateExpContext
    {
        public CalcaddTContext(CalculateExpContext ctx)
        {
            copyFrom(ctx);
        }

        public CalcAddEleContext calcAddEle()
        {
            return getRuleContext(CalcAddEleContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterCalcaddT(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitCalcaddT(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitCalcaddT(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class AddContext extends CalculateExpContext
    {
        public AddContext(CalculateExpContext ctx)
        {
            copyFrom(ctx);
        }

        public CalcAddEleContext calcAddEle(int i)
        {
            return getRuleContext(CalcAddEleContext.class, i);
        }

        public List<CalcAddEleContext> calcAddEle()
        {
            return getRuleContexts(CalcAddEleContext.class);
        }

        public TerminalNode ADD()
        {
            return getToken(ActionParser.ADD, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterAdd(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitAdd(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitAdd(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class CalcAddEleContext extends ParserRuleContext
    {
        public CalcAddEleContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public CalcAddEleContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_calcAddEle;
        }

        public void copyFrom(CalcAddEleContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class MultiContext extends CalcAddEleContext
    {
        public MultiContext(CalcAddEleContext ctx)
        {
            copyFrom(ctx);
        }

        public TerminalNode MULTI()
        {
            return getToken(ActionParser.MULTI, 0);
        }

        public CalcMultiEleContext calcMultiEle(int i)
        {
            return getRuleContext(CalcMultiEleContext.class, i);
        }

        public List<CalcMultiEleContext> calcMultiEle()
        {
            return getRuleContexts(CalcMultiEleContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterMulti(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitMulti(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitMulti(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class DividContext extends CalcAddEleContext
    {
        public DividContext(CalcAddEleContext ctx)
        {
            copyFrom(ctx);
        }

        public TerminalNode DIVID()
        {
            return getToken(ActionParser.DIVID, 0);
        }

        public CalcMultiEleContext calcMultiEle(int i)
        {
            return getRuleContext(CalcMultiEleContext.class, i);
        }

        public List<CalcMultiEleContext> calcMultiEle()
        {
            return getRuleContexts(CalcMultiEleContext.class);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterDivid(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitDivid(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitDivid(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class CalcmultiContext extends CalcAddEleContext
    {
        public CalcmultiContext(CalcAddEleContext ctx)
        {
            copyFrom(ctx);
        }

        public CalcMultiEleContext calcMultiEle()
        {
            return getRuleContext(CalcMultiEleContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterCalcmulti(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitCalcmulti(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitCalcmulti(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class CalcMultiEleContext extends ParserRuleContext
    {
        public CalcMultiEleContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public CalcMultiEleContext()
        {
        }

        @Override public int getRuleIndex()
        {
            return RULE_calcMultiEle;
        }

        public void copyFrom(CalcMultiEleContext ctx)
        {
            super.copyFrom(ctx);
        }
    }

    public static class CalcTContext extends CalcMultiEleContext
    {
        public CalcTContext(CalcMultiEleContext ctx)
        {
            copyFrom(ctx);
        }

        public CalcTermContext calcTerm()
        {
            return getRuleContext(CalcTermContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterCalcT(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitCalcT(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitCalcT(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class LeftContext extends CalcMultiEleContext
    {
        public LeftContext(CalcMultiEleContext ctx)
        {
            copyFrom(ctx);
        }

        public List<CalcTermContext> calcTerm()
        {
            return getRuleContexts(CalcTermContext.class);
        }

        public CalcTermContext calcTerm(int i)
        {
            return getRuleContext(CalcTermContext.class, i);
        }

        public TerminalNode LEFT()
        {
            return getToken(ActionParser.LEFT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterLeft(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitLeft(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitLeft(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class BarContext extends CalcMultiEleContext
    {
        public BarContext(CalcMultiEleContext ctx)
        {
            copyFrom(ctx);
        }

        public List<CalcTermContext> calcTerm()
        {
            return getRuleContexts(CalcTermContext.class);
        }

        public CalcTermContext calcTerm(int i)
        {
            return getRuleContext(CalcTermContext.class, i);
        }

        public TerminalNode BAR()
        {
            return getToken(ActionParser.BAR, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterBar(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitBar(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitBar(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class RightContext extends CalcMultiEleContext
    {
        public RightContext(CalcMultiEleContext ctx)
        {
            copyFrom(ctx);
        }

        public List<CalcTermContext> calcTerm()
        {
            return getRuleContexts(CalcTermContext.class);
        }

        public CalcTermContext calcTerm(int i)
        {
            return getRuleContext(CalcTermContext.class, i);
        }

        public TerminalNode RIGHT()
        {
            return getToken(ActionParser.RIGHT, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterRight(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitRight(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitRight(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class AmpersandContext extends CalcMultiEleContext
    {
        public AmpersandContext(CalcMultiEleContext ctx)
        {
            copyFrom(ctx);
        }

        public List<CalcTermContext> calcTerm()
        {
            return getRuleContexts(CalcTermContext.class);
        }

        public TerminalNode AMPERSAND()
        {
            return getToken(ActionParser.AMPERSAND, 0);
        }

        public CalcTermContext calcTerm(int i)
        {
            return getRuleContext(CalcTermContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterAmpersand(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitAmpersand(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitAmpersand(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class CalcTermContext extends ParserRuleContext
    {
        public CalcTermContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public IntValueContext intValue()
        {
            return getRuleContext(IntValueContext.class, 0);
        }

        public DbexpContext dbexp()
        {
            return getRuleContext(DbexpContext.class, 0);
        }

        public CalculateExpContext calculateExp()
        {
            return getRuleContext(CalculateExpContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_calcTerm;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterCalcTerm(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitCalcTerm(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitCalcTerm(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class LocalexpContext extends ParserRuleContext
    {
        public LocalexpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public NameContext name()
        {
            return getRuleContext(NameContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_localexp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterLocalexp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitLocalexp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitLocalexp(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class MoexpContext extends ParserRuleContext
    {
        public MoexpContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public ClassNameContext className()
        {
            return getRuleContext(ClassNameContext.class, 0);
        }

        public PropertyNameContext propertyName()
        {
            return getRuleContext(PropertyNameContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_moexp;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterMoexp(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitMoexp(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitMoexp(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class PropertyNameContext extends ParserRuleContext
    {
        public PropertyNameContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode TEXT()
        {
            return getToken(ActionParser.TEXT, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_propertyName;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterPropertyName(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitPropertyName(
                        this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitPropertyName(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ClassNameContext extends ParserRuleContext
    {
        public ClassNameContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode TEXT()
        {
            return getToken(ActionParser.TEXT, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_className;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterClassName(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitClassName(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitClassName(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class ValueContext extends ParserRuleContext
    {
        public ValueContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public PureStringContext pureString()
        {
            return getRuleContext(PureStringContext.class, 0);
        }

        public IntValueContext intValue()
        {
            return getRuleContext(IntValueContext.class, 0);
        }

        public DbStringContext dbString()
        {
            return getRuleContext(DbStringContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_value;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitValue(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class DbStringContext extends ParserRuleContext
    {
        public DbStringContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode STRING()
        {
            return getToken(ActionParser.STRING, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_dbString;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterDbString(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitDbString(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitDbString(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class PureStringContext extends ParserRuleContext
    {
        public PureStringContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode TEXT()
        {
            return getToken(ActionParser.TEXT, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_pureString;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterPureString(
                        this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitPureString(
                        this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitPureString(
                        this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class IntValueContext extends ParserRuleContext
    {
        public IntValueContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public OctValueContext octValue()
        {
            return getRuleContext(OctValueContext.class, 0);
        }

        public HexValueContext hexValue()
        {
            return getRuleContext(HexValueContext.class, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_intValue;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterIntValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitIntValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitIntValue(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class HexValueContext extends ParserRuleContext
    {
        public HexValueContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode HEX()
        {
            return getToken(ActionParser.HEX, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_hexValue;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterHexValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitHexValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitHexValue(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class OctValueContext extends ParserRuleContext
    {
        public OctValueContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode INT()
        {
            return getToken(ActionParser.INT, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_octValue;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterOctValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitOctValue(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitOctValue(this);
            else
                return visitor.visitChildren(this);
        }
    }

    public static class NameContext extends ParserRuleContext
    {
        public NameContext(ParserRuleContext parent, int invokingState)
        {
            super(parent, invokingState);
        }

        public TerminalNode TEXT()
        {
            return getToken(ActionParser.TEXT, 0);
        }

        @Override public int getRuleIndex()
        {
            return RULE_name;
        }

        @Override
        public void enterRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).enterName(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener)
        {
            if (listener instanceof ActionListener)
                ((ActionListener) listener).exitName(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor)
        {
            if (visitor instanceof ActionVisitor)
                return ((ActionVisitor<? extends T>) visitor).visitName(this);
            else
                return visitor.visitChildren(this);
        }
    }
}