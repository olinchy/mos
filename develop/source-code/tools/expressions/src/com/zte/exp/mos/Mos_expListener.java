// Generated from /home/olinchy/workspace/ems-mos/develop/source-code/tools/expressions/src/com/zte/exp/mos/Mos_exp.g4 by ANTLR 4.5.1
package com.zte.exp.mos;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Mos_expParser}.
 */
public interface Mos_expListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInit(Mos_expParser.InitContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInit(Mos_expParser.InitContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#dbexp}.
	 * @param ctx the parse tree
	 */
	void enterDbexp(Mos_expParser.DbexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#dbexp}.
	 * @param ctx the parse tree
	 */
	void exitDbexp(Mos_expParser.DbexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#dnexp}.
	 * @param ctx the parse tree
	 */
	void enterDnexp(Mos_expParser.DnexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#dnexp}.
	 * @param ctx the parse tree
	 */
	void exitDnexp(Mos_expParser.DnexpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code oneDNTerm}
	 * labeled alternative in {@link Mos_expParser#dnsec}.
	 * @param ctx the parse tree
	 */
	void enterOneDNTerm(Mos_expParser.OneDNTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code oneDNTerm}
	 * labeled alternative in {@link Mos_expParser#dnsec}.
	 * @param ctx the parse tree
	 */
	void exitOneDNTerm(Mos_expParser.OneDNTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code twoDNTerm}
	 * labeled alternative in {@link Mos_expParser#dnsec}.
	 * @param ctx the parse tree
	 */
	void enterTwoDNTerm(Mos_expParser.TwoDNTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code twoDNTerm}
	 * labeled alternative in {@link Mos_expParser#dnsec}.
	 * @param ctx the parse tree
	 */
	void exitTwoDNTerm(Mos_expParser.TwoDNTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#dnterm}.
	 * @param ctx the parse tree
	 */
	void enterDnterm(Mos_expParser.DntermContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#dnterm}.
	 * @param ctx the parse tree
	 */
	void exitDnterm(Mos_expParser.DntermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dnElements}
	 * labeled alternative in {@link Mos_expParser#dnele}.
	 * @param ctx the parse tree
	 */
	void enterDnElements(Mos_expParser.DnElementsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dnElements}
	 * labeled alternative in {@link Mos_expParser#dnele}.
	 * @param ctx the parse tree
	 */
	void exitDnElements(Mos_expParser.DnElementsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rootDN}
	 * labeled alternative in {@link Mos_expParser#dnele}.
	 * @param ctx the parse tree
	 */
	void enterRootDN(Mos_expParser.RootDNContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rootDN}
	 * labeled alternative in {@link Mos_expParser#dnele}.
	 * @param ctx the parse tree
	 */
	void exitRootDN(Mos_expParser.RootDNContext ctx);
	/**
	 * Enter a parse tree produced by the {@code wildDN}
	 * labeled alternative in {@link Mos_expParser#dnele}.
	 * @param ctx the parse tree
	 */
	void enterWildDN(Mos_expParser.WildDNContext ctx);
	/**
	 * Exit a parse tree produced by the {@code wildDN}
	 * labeled alternative in {@link Mos_expParser#dnele}.
	 * @param ctx the parse tree
	 */
	void exitWildDN(Mos_expParser.WildDNContext ctx);
	/**
	 * Enter a parse tree produced by the {@code slashExp}
	 * labeled alternative in {@link Mos_expParser#element}.
	 * @param ctx the parse tree
	 */
	void enterSlashExp(Mos_expParser.SlashExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code slashExp}
	 * labeled alternative in {@link Mos_expParser#element}.
	 * @param ctx the parse tree
	 */
	void exitSlashExp(Mos_expParser.SlashExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code slashValue}
	 * labeled alternative in {@link Mos_expParser#element}.
	 * @param ctx the parse tree
	 */
	void enterSlashValue(Mos_expParser.SlashValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code slashValue}
	 * labeled alternative in {@link Mos_expParser#element}.
	 * @param ctx the parse tree
	 */
	void exitSlashValue(Mos_expParser.SlashValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#neid}.
	 * @param ctx the parse tree
	 */
	void enterNeid(Mos_expParser.NeidContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#neid}.
	 * @param ctx the parse tree
	 */
	void exitNeid(Mos_expParser.NeidContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#wildcard}.
	 * @param ctx the parse tree
	 */
	void enterWildcard(Mos_expParser.WildcardContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#wildcard}.
	 * @param ctx the parse tree
	 */
	void exitWildcard(Mos_expParser.WildcardContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#index}.
	 * @param ctx the parse tree
	 */
	void enterIndex(Mos_expParser.IndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#index}.
	 * @param ctx the parse tree
	 */
	void exitIndex(Mos_expParser.IndexContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equal}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterEqual(Mos_expParser.EqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equal}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitEqual(Mos_expParser.EqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bigger}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterBigger(Mos_expParser.BiggerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bigger}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitBigger(Mos_expParser.BiggerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code smaller}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterSmaller(Mos_expParser.SmallerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code smaller}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitSmaller(Mos_expParser.SmallerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code in}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterIn(Mos_expParser.InContext ctx);
	/**
	 * Exit a parse tree produced by the {@code in}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitIn(Mos_expParser.InContext ctx);
	/**
	 * Enter a parse tree produced by the {@code contains}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterContains(Mos_expParser.ContainsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code contains}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitContains(Mos_expParser.ContainsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notEqual}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterNotEqual(Mos_expParser.NotEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notEqual}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitNotEqual(Mos_expParser.NotEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bOrE}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterBOrE(Mos_expParser.BOrEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bOrE}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitBOrE(Mos_expParser.BOrEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sOrE}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterSOrE(Mos_expParser.SOrEContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sOrE}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitSOrE(Mos_expParser.SOrEContext ctx);
	/**
	 * Enter a parse tree produced by the {@code like}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void enterLike(Mos_expParser.LikeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code like}
	 * labeled alternative in {@link Mos_expParser#comparasion}.
	 * @param ctx the parse tree
	 */
	void exitLike(Mos_expParser.LikeContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(Mos_expParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(Mos_expParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#arrayexp}.
	 * @param ctx the parse tree
	 */
	void enterArrayexp(Mos_expParser.ArrayexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#arrayexp}.
	 * @param ctx the parse tree
	 */
	void exitArrayexp(Mos_expParser.ArrayexpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolTerm}
	 * labeled alternative in {@link Mos_expParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolTerm(Mos_expParser.BoolTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolTerm}
	 * labeled alternative in {@link Mos_expParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolTerm(Mos_expParser.BoolTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code or}
	 * labeled alternative in {@link Mos_expParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void enterOr(Mos_expParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code or}
	 * labeled alternative in {@link Mos_expParser#boolean_expr}.
	 * @param ctx the parse tree
	 */
	void exitOr(Mos_expParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolElement}
	 * labeled alternative in {@link Mos_expParser#boolean_term}.
	 * @param ctx the parse tree
	 */
	void enterBoolElement(Mos_expParser.BoolElementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolElement}
	 * labeled alternative in {@link Mos_expParser#boolean_term}.
	 * @param ctx the parse tree
	 */
	void exitBoolElement(Mos_expParser.BoolElementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code and}
	 * labeled alternative in {@link Mos_expParser#boolean_term}.
	 * @param ctx the parse tree
	 */
	void enterAnd(Mos_expParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code and}
	 * labeled alternative in {@link Mos_expParser#boolean_term}.
	 * @param ctx the parse tree
	 */
	void exitAnd(Mos_expParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueValue}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void enterTrueValue(Mos_expParser.TrueValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueValue}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void exitTrueValue(Mos_expParser.TrueValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseValue}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void enterFalseValue(Mos_expParser.FalseValueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseValue}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void exitFalseValue(Mos_expParser.FalseValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code readCompare}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void enterReadCompare(Mos_expParser.ReadCompareContext ctx);
	/**
	 * Exit a parse tree produced by the {@code readCompare}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void exitReadCompare(Mos_expParser.ReadCompareContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(Mos_expParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolExpr}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(Mos_expParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code not}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void enterNot(Mos_expParser.NotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code not}
	 * labeled alternative in {@link Mos_expParser#boolean_element}.
	 * @param ctx the parse tree
	 */
	void exitNot(Mos_expParser.NotContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#extendedExp}.
	 * @param ctx the parse tree
	 */
	void enterExtendedExp(Mos_expParser.ExtendedExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#extendedExp}.
	 * @param ctx the parse tree
	 */
	void exitExtendedExp(Mos_expParser.ExtendedExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code calcaddT}
	 * labeled alternative in {@link Mos_expParser#calculateExp}.
	 * @param ctx the parse tree
	 */
	void enterCalcaddT(Mos_expParser.CalcaddTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code calcaddT}
	 * labeled alternative in {@link Mos_expParser#calculateExp}.
	 * @param ctx the parse tree
	 */
	void exitCalcaddT(Mos_expParser.CalcaddTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code add}
	 * labeled alternative in {@link Mos_expParser#calculateExp}.
	 * @param ctx the parse tree
	 */
	void enterAdd(Mos_expParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by the {@code add}
	 * labeled alternative in {@link Mos_expParser#calculateExp}.
	 * @param ctx the parse tree
	 */
	void exitAdd(Mos_expParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minus}
	 * labeled alternative in {@link Mos_expParser#calculateExp}.
	 * @param ctx the parse tree
	 */
	void enterMinus(Mos_expParser.MinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minus}
	 * labeled alternative in {@link Mos_expParser#calculateExp}.
	 * @param ctx the parse tree
	 */
	void exitMinus(Mos_expParser.MinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code calcmulti}
	 * labeled alternative in {@link Mos_expParser#calcAddEle}.
	 * @param ctx the parse tree
	 */
	void enterCalcmulti(Mos_expParser.CalcmultiContext ctx);
	/**
	 * Exit a parse tree produced by the {@code calcmulti}
	 * labeled alternative in {@link Mos_expParser#calcAddEle}.
	 * @param ctx the parse tree
	 */
	void exitCalcmulti(Mos_expParser.CalcmultiContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multi}
	 * labeled alternative in {@link Mos_expParser#calcAddEle}.
	 * @param ctx the parse tree
	 */
	void enterMulti(Mos_expParser.MultiContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multi}
	 * labeled alternative in {@link Mos_expParser#calcAddEle}.
	 * @param ctx the parse tree
	 */
	void exitMulti(Mos_expParser.MultiContext ctx);
	/**
	 * Enter a parse tree produced by the {@code divid}
	 * labeled alternative in {@link Mos_expParser#calcAddEle}.
	 * @param ctx the parse tree
	 */
	void enterDivid(Mos_expParser.DividContext ctx);
	/**
	 * Exit a parse tree produced by the {@code divid}
	 * labeled alternative in {@link Mos_expParser#calcAddEle}.
	 * @param ctx the parse tree
	 */
	void exitDivid(Mos_expParser.DividContext ctx);
	/**
	 * Enter a parse tree produced by the {@code calcT}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void enterCalcT(Mos_expParser.CalcTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code calcT}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void exitCalcT(Mos_expParser.CalcTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code left}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void enterLeft(Mos_expParser.LeftContext ctx);
	/**
	 * Exit a parse tree produced by the {@code left}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void exitLeft(Mos_expParser.LeftContext ctx);
	/**
	 * Enter a parse tree produced by the {@code right}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void enterRight(Mos_expParser.RightContext ctx);
	/**
	 * Exit a parse tree produced by the {@code right}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void exitRight(Mos_expParser.RightContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ampersand}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void enterAmpersand(Mos_expParser.AmpersandContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ampersand}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void exitAmpersand(Mos_expParser.AmpersandContext ctx);
	/**
	 * Enter a parse tree produced by the {@code bar}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void enterBar(Mos_expParser.BarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code bar}
	 * labeled alternative in {@link Mos_expParser#calcMultiEle}.
	 * @param ctx the parse tree
	 */
	void exitBar(Mos_expParser.BarContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#calcTerm}.
	 * @param ctx the parse tree
	 */
	void enterCalcTerm(Mos_expParser.CalcTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#calcTerm}.
	 * @param ctx the parse tree
	 */
	void exitCalcTerm(Mos_expParser.CalcTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#localexp}.
	 * @param ctx the parse tree
	 */
	void enterLocalexp(Mos_expParser.LocalexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#localexp}.
	 * @param ctx the parse tree
	 */
	void exitLocalexp(Mos_expParser.LocalexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#moexp}.
	 * @param ctx the parse tree
	 */
	void enterMoexp(Mos_expParser.MoexpContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#moexp}.
	 * @param ctx the parse tree
	 */
	void exitMoexp(Mos_expParser.MoexpContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void enterPropertyName(Mos_expParser.PropertyNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void exitPropertyName(Mos_expParser.PropertyNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#className}.
	 * @param ctx the parse tree
	 */
	void enterClassName(Mos_expParser.ClassNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#className}.
	 * @param ctx the parse tree
	 */
	void exitClassName(Mos_expParser.ClassNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(Mos_expParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(Mos_expParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#dbString}.
	 * @param ctx the parse tree
	 */
	void enterDbString(Mos_expParser.DbStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#dbString}.
	 * @param ctx the parse tree
	 */
	void exitDbString(Mos_expParser.DbStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#pureString}.
	 * @param ctx the parse tree
	 */
	void enterPureString(Mos_expParser.PureStringContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#pureString}.
	 * @param ctx the parse tree
	 */
	void exitPureString(Mos_expParser.PureStringContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#intValue}.
	 * @param ctx the parse tree
	 */
	void enterIntValue(Mos_expParser.IntValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#intValue}.
	 * @param ctx the parse tree
	 */
	void exitIntValue(Mos_expParser.IntValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#hexValue}.
	 * @param ctx the parse tree
	 */
	void enterHexValue(Mos_expParser.HexValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#hexValue}.
	 * @param ctx the parse tree
	 */
	void exitHexValue(Mos_expParser.HexValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#octValue}.
	 * @param ctx the parse tree
	 */
	void enterOctValue(Mos_expParser.OctValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#octValue}.
	 * @param ctx the parse tree
	 */
	void exitOctValue(Mos_expParser.OctValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link Mos_expParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(Mos_expParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link Mos_expParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(Mos_expParser.NameContext ctx);
}