package com.zte.exp.action;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;

public class MOSActionVisitor extends ActionBaseVisitor<ActionDesc>
{
    // action = tree paras = [[root, /ems/1], [depth, 2] ] 
    // action = select paras = [[exp=port.portType = 2]]
    private final ActionDesc local = new ActionDesc();

    /**
     * {@inheritDoc} <p/>
     * The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.
     */
    @Override
    public ActionDesc visitTreeAct(@NotNull ActionParser.TreeActContext ctx)
    {
        // action = tree paras = [[root, /ems/1], [depth, 2] ]
        local.actionName = "tree";
        return visitChildren(ctx);
    }

    @Override
    public ActionDesc visitExp(@NotNull ActionParser.ExpContext ctx)
    {
        StringBuilder builder = new StringBuilder();
        getText(ctx.expvalue(), builder);

        local.paras.add(new String[] { ctx.name().getText(), builder.toString().trim() });
        return local;
    }

    @Override
    public ActionDesc visitInit(@NotNull ActionParser.InitContext ctx)
    {
        StringBuilder builder = new StringBuilder();
        getText(ctx, builder);
        local.paras.add(new String[] { "exp", builder.toString().trim() });
        return local;
    }

    @Override
    public ActionDesc visitSelect(@NotNull ActionParser.SelectContext ctx)
    {
        local.actionName = "select";

        return visitChildren(ctx);
    }

    @Override
    public ActionDesc visitCommit(@NotNull ActionParser.CommitContext ctx)
    {
        local.actionName = "commit";
        return visitChildren(ctx);
    }

    @Override public ActionDesc visitVer(@NotNull ActionParser.VerContext ctx)
    {
        local.actionName = "version";
        return visitChildren(ctx);
    }

    @Override
    public ActionDesc visitJsonObject(@NotNull ActionParser.JsonObjectContext ctx)
    {
        local.paras.add(new String[] { "json", ctx.getText() });
        return local;
    }

    @Override public ActionDesc visitOnSelect(@NotNull ActionParser.OnSelectContext ctx)
    {
        local.actionName = "onSelect";
        return visitChildren(ctx);
    }

    @Override
    protected ActionDesc defaultResult()
    {
        return local;
    }

    private void getText(ParseTree ctx, StringBuilder builder)
    {
        if (ctx.getChildCount() == 0)
        {
            builder.append(ctx.getText());
        }
        else
        {
            for (int i = 0; i < ctx.getChildCount(); i++)
            {
                getText(ctx.getChild(i), builder);
                builder.append(" ");
            }
        }
    }

}
