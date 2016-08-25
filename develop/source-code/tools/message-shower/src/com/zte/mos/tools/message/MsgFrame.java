package com.zte.mos.tools.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.type.Pair;
import com.zte.mos.util.tools.JsonUtil;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by olinchy on 2/10/15 for mosjava.
 */
public class MsgFrame extends BasicFrame
{

    public MsgFrame(Pair<MsgType, String> msg, Integer pos)
    {
        Dimension di = new Dimension(270, 55);
        this.setSize(di);
        this.setLocation(UITool.getScreenRightTop(di, pos));

        String picPath = "/img/" + msg.first().name() + ".png";
        URL url = this.getClass().getResource(picPath);
        this.getContentPane().setBackground(new Color(Integer.parseInt("646464", 16)));
        this.getContentPane().add(new JLabel(new ImageIcon(url)),
                BorderLayout.WEST);
        this.getContentPane().add(getTextPane(msg.second()), BorderLayout.CENTER);
        this.setFocusableWindowState(false);
    }

    private Component getTextPane(String msg)
    {
        String m = msg;
        String t = "";
        if (msg.contains("title"))
        {
            JsonNode node = JsonUtil.toNode(msg);
            m = "\n" + node.get("msg").textValue();
            t = node.get("title").textValue();
        }
        JTextPane msgPane = new JTextPane();
        msgPane.setEditable(false);

        StyledDocument doc = msgPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setForeground(center, Color.WHITE);
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_LEFT);

        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        msgPane.setBackground(new Color(Integer.parseInt("646464", 16)));

        try
        {
            doc.insertString(doc.getLength(), t, getTitleStyle(center));
            doc.insertString(doc.getLength(), m, getMsgStyle(center));
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }

        return msgPane;
    }

    private AttributeSet getMsgStyle(SimpleAttributeSet attr)
    {
        StyleConstants.setFontSize(attr, 13);
        StyleConstants.setBold(attr, false);
        return attr;
    }

    private AttributeSet getTitleStyle(SimpleAttributeSet attr)
    {
        StyleConstants.setFontSize(attr, 15);
        StyleConstants.setBold(attr, true);

        return attr;
    }

    @Override public void startWorking()
    {

    }

    @Override protected void init()
    {
    }

    @Override public float getTarOpacity()
    {
        return 0.8f;
    }
}
