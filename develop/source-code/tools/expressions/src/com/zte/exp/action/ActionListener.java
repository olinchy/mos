// Generated from Action.g4 by ANTLR 4.1
package com.zte.exp.action;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ActionParser}.
 */
public interface ActionListener extends ParseTreeListener
{
    /**
     * Enter a parse tree produced by {@link ActionParser#oneDNTerm}.
     *
     * @param ctx the parse tree
     */
    void enterOneDNTerm(@NotNull ActionParser.OneDNTermContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#oneDNTerm}.
     *
     * @param ctx the parse tree
     */
    void exitOneDNTerm(@NotNull ActionParser.OneDNTermContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#sOrE}.
     *
     * @param ctx the parse tree
     */
    void enterSOrE(@NotNull ActionParser.SOrEContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#sOrE}.
     *
     * @param ctx the parse tree
     */
    void exitSOrE(@NotNull ActionParser.SOrEContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#contains}.
     *
     * @param ctx the parse tree
     */
    void enterContains(@NotNull ActionParser.ContainsContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#contains}.
     *
     * @param ctx the parse tree
     */
    void exitContains(@NotNull ActionParser.ContainsContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#boolTerm}.
     *
     * @param ctx the parse tree
     */
    void enterBoolTerm(@NotNull ActionParser.BoolTermContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#boolTerm}.
     *
     * @param ctx the parse tree
     */
    void exitBoolTerm(@NotNull ActionParser.BoolTermContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#commit}.
     *
     * @param ctx the parse tree
     */
    void enterCommit(@NotNull ActionParser.CommitContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#commit}.
     *
     * @param ctx the parse tree
     */
    void exitCommit(@NotNull ActionParser.CommitContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#value}.
     *
     * @param ctx the parse tree
     */
    void enterValue(@NotNull ActionParser.ValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#value}.
     *
     * @param ctx the parse tree
     */
    void exitValue(@NotNull ActionParser.ValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#dbString}.
     *
     * @param ctx the parse tree
     */
    void enterDbString(@NotNull ActionParser.DbStringContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#dbString}.
     *
     * @param ctx the parse tree
     */
    void exitDbString(@NotNull ActionParser.DbStringContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#notEqual}.
     *
     * @param ctx the parse tree
     */
    void enterNotEqual(@NotNull ActionParser.NotEqualContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#notEqual}.
     *
     * @param ctx the parse tree
     */
    void exitNotEqual(@NotNull ActionParser.NotEqualContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#right}.
     *
     * @param ctx the parse tree
     */
    void enterRight(@NotNull ActionParser.RightContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#right}.
     *
     * @param ctx the parse tree
     */
    void exitRight(@NotNull ActionParser.RightContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#intValue}.
     *
     * @param ctx the parse tree
     */
    void enterIntValue(@NotNull ActionParser.IntValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#intValue}.
     *
     * @param ctx the parse tree
     */
    void exitIntValue(@NotNull ActionParser.IntValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#neid}.
     *
     * @param ctx the parse tree
     */
    void enterNeid(@NotNull ActionParser.NeidContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#neid}.
     *
     * @param ctx the parse tree
     */
    void exitNeid(@NotNull ActionParser.NeidContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#propertyName}.
     *
     * @param ctx the parse tree
     */
    void enterPropertyName(@NotNull ActionParser.PropertyNameContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#propertyName}.
     *
     * @param ctx the parse tree
     */
    void exitPropertyName(@NotNull ActionParser.PropertyNameContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#index}.
     *
     * @param ctx the parse tree
     */
    void enterIndex(@NotNull ActionParser.IndexContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#index}.
     *
     * @param ctx the parse tree
     */
    void exitIndex(@NotNull ActionParser.IndexContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#dnexp}.
     *
     * @param ctx the parse tree
     */
    void enterDnexp(@NotNull ActionParser.DnexpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#dnexp}.
     *
     * @param ctx the parse tree
     */
    void exitDnexp(@NotNull ActionParser.DnexpContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#minus}.
     *
     * @param ctx the parse tree
     */
    void enterMinus(@NotNull ActionParser.MinusContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#minus}.
     *
     * @param ctx the parse tree
     */
    void exitMinus(@NotNull ActionParser.MinusContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#select}.
     *
     * @param ctx the parse tree
     */
    void enterSelect(@NotNull ActionParser.SelectContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#select}.
     *
     * @param ctx the parse tree
     */
    void exitSelect(@NotNull ActionParser.SelectContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#or}.
     *
     * @param ctx the parse tree
     */
    void enterOr(@NotNull ActionParser.OrContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#or}.
     *
     * @param ctx the parse tree
     */
    void exitOr(@NotNull ActionParser.OrContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#init}.
     *
     * @param ctx the parse tree
     */
    void enterInit(@NotNull ActionParser.InitContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#init}.
     *
     * @param ctx the parse tree
     */
    void exitInit(@NotNull ActionParser.InitContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#boolElement}.
     *
     * @param ctx the parse tree
     */
    void enterBoolElement(@NotNull ActionParser.BoolElementContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#boolElement}.
     *
     * @param ctx the parse tree
     */
    void exitBoolElement(@NotNull ActionParser.BoolElementContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#dbexp}.
     *
     * @param ctx the parse tree
     */
    void enterDbexp(@NotNull ActionParser.DbexpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#dbexp}.
     *
     * @param ctx the parse tree
     */
    void exitDbexp(@NotNull ActionParser.DbexpContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#octValue}.
     *
     * @param ctx the parse tree
     */
    void enterOctValue(@NotNull ActionParser.OctValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#octValue}.
     *
     * @param ctx the parse tree
     */
    void exitOctValue(@NotNull ActionParser.OctValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#like}.
     *
     * @param ctx the parse tree
     */
    void enterLike(@NotNull ActionParser.LikeContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#like}.
     *
     * @param ctx the parse tree
     */
    void exitLike(@NotNull ActionParser.LikeContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#and}.
     *
     * @param ctx the parse tree
     */
    void enterAnd(@NotNull ActionParser.AndContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#and}.
     *
     * @param ctx the parse tree
     */
    void exitAnd(@NotNull ActionParser.AndContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#not}.
     *
     * @param ctx the parse tree
     */
    void enterNot(@NotNull ActionParser.NotContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#not}.
     *
     * @param ctx the parse tree
     */
    void exitNot(@NotNull ActionParser.NotContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#exp}.
     *
     * @param ctx the parse tree
     */
    void enterExp(@NotNull ActionParser.ExpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#exp}.
     *
     * @param ctx the parse tree
     */
    void exitExp(@NotNull ActionParser.ExpContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#extendedExp}.
     *
     * @param ctx the parse tree
     */
    void enterExtendedExp(@NotNull ActionParser.ExtendedExpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#extendedExp}.
     *
     * @param ctx the parse tree
     */
    void exitExtendedExp(@NotNull ActionParser.ExtendedExpContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#calcaddT}.
     *
     * @param ctx the parse tree
     */
    void enterCalcaddT(@NotNull ActionParser.CalcaddTContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#calcaddT}.
     *
     * @param ctx the parse tree
     */
    void exitCalcaddT(@NotNull ActionParser.CalcaddTContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#multi}.
     *
     * @param ctx the parse tree
     */
    void enterMulti(@NotNull ActionParser.MultiContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#multi}.
     *
     * @param ctx the parse tree
     */
    void exitMulti(@NotNull ActionParser.MultiContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#bar}.
     *
     * @param ctx the parse tree
     */
    void enterBar(@NotNull ActionParser.BarContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#bar}.
     *
     * @param ctx the parse tree
     */
    void exitBar(@NotNull ActionParser.BarContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#calcTerm}.
     *
     * @param ctx the parse tree
     */
    void enterCalcTerm(@NotNull ActionParser.CalcTermContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#calcTerm}.
     *
     * @param ctx the parse tree
     */
    void exitCalcTerm(@NotNull ActionParser.CalcTermContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#array}.
     *
     * @param ctx the parse tree
     */
    void enterArray(@NotNull ActionParser.ArrayContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#array}.
     *
     * @param ctx the parse tree
     */
    void exitArray(@NotNull ActionParser.ArrayContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#bOrE}.
     *
     * @param ctx the parse tree
     */
    void enterBOrE(@NotNull ActionParser.BOrEContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#bOrE}.
     *
     * @param ctx the parse tree
     */
    void exitBOrE(@NotNull ActionParser.BOrEContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#hexValue}.
     *
     * @param ctx the parse tree
     */
    void enterHexValue(@NotNull ActionParser.HexValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#hexValue}.
     *
     * @param ctx the parse tree
     */
    void exitHexValue(@NotNull ActionParser.HexValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#bigger}.
     *
     * @param ctx the parse tree
     */
    void enterBigger(@NotNull ActionParser.BiggerContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#bigger}.
     *
     * @param ctx the parse tree
     */
    void exitBigger(@NotNull ActionParser.BiggerContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#twoDNTerm}.
     *
     * @param ctx the parse tree
     */
    void enterTwoDNTerm(@NotNull ActionParser.TwoDNTermContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#twoDNTerm}.
     *
     * @param ctx the parse tree
     */
    void exitTwoDNTerm(@NotNull ActionParser.TwoDNTermContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#ver}.
     *
     * @param ctx the parse tree
     */
    void enterVer(@NotNull ActionParser.VerContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#ver}.
     *
     * @param ctx the parse tree
     */
    void exitVer(@NotNull ActionParser.VerContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#boolExpr}.
     *
     * @param ctx the parse tree
     */
    void enterBoolExpr(@NotNull ActionParser.BoolExprContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#boolExpr}.
     *
     * @param ctx the parse tree
     */
    void exitBoolExpr(@NotNull ActionParser.BoolExprContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#add}.
     *
     * @param ctx the parse tree
     */
    void enterAdd(@NotNull ActionParser.AddContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#add}.
     *
     * @param ctx the parse tree
     */
    void exitAdd(@NotNull ActionParser.AddContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#arrayexp}.
     *
     * @param ctx the parse tree
     */
    void enterArrayexp(@NotNull ActionParser.ArrayexpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#arrayexp}.
     *
     * @param ctx the parse tree
     */
    void exitArrayexp(@NotNull ActionParser.ArrayexpContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#readCompare}.
     *
     * @param ctx the parse tree
     */
    void enterReadCompare(@NotNull ActionParser.ReadCompareContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#readCompare}.
     *
     * @param ctx the parse tree
     */
    void exitReadCompare(@NotNull ActionParser.ReadCompareContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#slashValue}.
     *
     * @param ctx the parse tree
     */
    void enterSlashValue(@NotNull ActionParser.SlashValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#slashValue}.
     *
     * @param ctx the parse tree
     */
    void exitSlashValue(@NotNull ActionParser.SlashValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#smaller}.
     *
     * @param ctx the parse tree
     */
    void enterSmaller(@NotNull ActionParser.SmallerContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#smaller}.
     *
     * @param ctx the parse tree
     */
    void exitSmaller(@NotNull ActionParser.SmallerContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#dnElements}.
     *
     * @param ctx the parse tree
     */
    void enterDnElements(@NotNull ActionParser.DnElementsContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#dnElements}.
     *
     * @param ctx the parse tree
     */
    void exitDnElements(@NotNull ActionParser.DnElementsContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#pureString}.
     *
     * @param ctx the parse tree
     */
    void enterPureString(@NotNull ActionParser.PureStringContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#pureString}.
     *
     * @param ctx the parse tree
     */
    void exitPureString(@NotNull ActionParser.PureStringContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#localexp}.
     *
     * @param ctx the parse tree
     */
    void enterLocalexp(@NotNull ActionParser.LocalexpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#localexp}.
     *
     * @param ctx the parse tree
     */
    void exitLocalexp(@NotNull ActionParser.LocalexpContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#json}.
     *
     * @param ctx the parse tree
     */
    void enterJson(@NotNull ActionParser.JsonContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#json}.
     *
     * @param ctx the parse tree
     */
    void exitJson(@NotNull ActionParser.JsonContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#treeAct}.
     *
     * @param ctx the parse tree
     */
    void enterTreeAct(@NotNull ActionParser.TreeActContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#treeAct}.
     *
     * @param ctx the parse tree
     */
    void exitTreeAct(@NotNull ActionParser.TreeActContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#name}.
     *
     * @param ctx the parse tree
     */
    void enterName(@NotNull ActionParser.NameContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#name}.
     *
     * @param ctx the parse tree
     */
    void exitName(@NotNull ActionParser.NameContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#className}.
     *
     * @param ctx the parse tree
     */
    void enterClassName(@NotNull ActionParser.ClassNameContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#className}.
     *
     * @param ctx the parse tree
     */
    void exitClassName(@NotNull ActionParser.ClassNameContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#left}.
     *
     * @param ctx the parse tree
     */
    void enterLeft(@NotNull ActionParser.LeftContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#left}.
     *
     * @param ctx the parse tree
     */
    void exitLeft(@NotNull ActionParser.LeftContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#slashExp}.
     *
     * @param ctx the parse tree
     */
    void enterSlashExp(@NotNull ActionParser.SlashExpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#slashExp}.
     *
     * @param ctx the parse tree
     */
    void exitSlashExp(@NotNull ActionParser.SlashExpContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#onSelect}.
     *
     * @param ctx the parse tree
     */
    void enterOnSelect(@NotNull ActionParser.OnSelectContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#onSelect}.
     *
     * @param ctx the parse tree
     */
    void exitOnSelect(@NotNull ActionParser.OnSelectContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#jsonObject}.
     *
     * @param ctx the parse tree
     */
    void enterJsonObject(@NotNull ActionParser.JsonObjectContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#jsonObject}.
     *
     * @param ctx the parse tree
     */
    void exitJsonObject(@NotNull ActionParser.JsonObjectContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#trueValue}.
     *
     * @param ctx the parse tree
     */
    void enterTrueValue(@NotNull ActionParser.TrueValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#trueValue}.
     *
     * @param ctx the parse tree
     */
    void exitTrueValue(@NotNull ActionParser.TrueValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#expvalue}.
     *
     * @param ctx the parse tree
     */
    void enterExpvalue(@NotNull ActionParser.ExpvalueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#expvalue}.
     *
     * @param ctx the parse tree
     */
    void exitExpvalue(@NotNull ActionParser.ExpvalueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#falseValue}.
     *
     * @param ctx the parse tree
     */
    void enterFalseValue(@NotNull ActionParser.FalseValueContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#falseValue}.
     *
     * @param ctx the parse tree
     */
    void exitFalseValue(@NotNull ActionParser.FalseValueContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#divid}.
     *
     * @param ctx the parse tree
     */
    void enterDivid(@NotNull ActionParser.DividContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#divid}.
     *
     * @param ctx the parse tree
     */
    void exitDivid(@NotNull ActionParser.DividContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#equal}.
     *
     * @param ctx the parse tree
     */
    void enterEqual(@NotNull ActionParser.EqualContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#equal}.
     *
     * @param ctx the parse tree
     */
    void exitEqual(@NotNull ActionParser.EqualContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#dnterm}.
     *
     * @param ctx the parse tree
     */
    void enterDnterm(@NotNull ActionParser.DntermContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#dnterm}.
     *
     * @param ctx the parse tree
     */
    void exitDnterm(@NotNull ActionParser.DntermContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#in}.
     *
     * @param ctx the parse tree
     */
    void enterIn(@NotNull ActionParser.InContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#in}.
     *
     * @param ctx the parse tree
     */
    void exitIn(@NotNull ActionParser.InContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#para}.
     *
     * @param ctx the parse tree
     */
    void enterPara(@NotNull ActionParser.ParaContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#para}.
     *
     * @param ctx the parse tree
     */
    void exitPara(@NotNull ActionParser.ParaContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#ampersand}.
     *
     * @param ctx the parse tree
     */
    void enterAmpersand(@NotNull ActionParser.AmpersandContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#ampersand}.
     *
     * @param ctx the parse tree
     */
    void exitAmpersand(@NotNull ActionParser.AmpersandContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#calcT}.
     *
     * @param ctx the parse tree
     */
    void enterCalcT(@NotNull ActionParser.CalcTContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#calcT}.
     *
     * @param ctx the parse tree
     */
    void exitCalcT(@NotNull ActionParser.CalcTContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#ne}.
     *
     * @param ctx the parse tree
     */
    void enterNe(@NotNull ActionParser.NeContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#ne}.
     *
     * @param ctx the parse tree
     */
    void exitNe(@NotNull ActionParser.NeContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#calcmulti}.
     *
     * @param ctx the parse tree
     */
    void enterCalcmulti(@NotNull ActionParser.CalcmultiContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#calcmulti}.
     *
     * @param ctx the parse tree
     */
    void exitCalcmulti(@NotNull ActionParser.CalcmultiContext ctx);

    /**
     * Enter a parse tree produced by {@link ActionParser#moexp}.
     *
     * @param ctx the parse tree
     */
    void enterMoexp(@NotNull ActionParser.MoexpContext ctx);

    /**
     * Exit a parse tree produced by {@link ActionParser#moexp}.
     *
     * @param ctx the parse tree
     */
    void exitMoexp(@NotNull ActionParser.MoexpContext ctx);
}