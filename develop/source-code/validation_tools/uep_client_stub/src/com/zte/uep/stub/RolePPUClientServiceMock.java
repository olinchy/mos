package com.zte.uep.stub;

import com.zte.ums.api.common.role.ppu.entity.OperationInfo;
import com.zte.ums.api.common.role.ppu.service.RolePPUClientService;
import com.zte.ums.uep.api.pfl.finterface.FIException;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by pavel on 16-2-21.
 */
public class RolePPUClientServiceMock implements RolePPUClientService
{
    private Map<String,Boolean> rightMap = new HashMap<String,Boolean>();

    @Override
    public boolean checkRight(String s, String[] strings)
    {
        return getCheckRightResult(s);
    }

    @Override
    public boolean checkRightForRanSharing(String s, String[] strings)
    {
        return false;
    }

    @Override
    public String[] getResource(String s)
    {
        return new String[0];
    }

    @Override
    public String[] getResourceForRanSharing(String s)
    {
        return new String[0];
    }

    @Override
    public String[] getResource(String s, String s1)
    {
        return new String[0];
    }

    @Override
    public String[] getOperation(String s)
    {
        return new String[0];
    }

    @Override
    public OperationInfo getOperationInfo(String s)
    {
        return null;
    }

    @Override
    public void setCurrentUserRoleAndSetNames(String[] strings, String[] strings1)
    {

    }

    @Override
    public JTree getOperationTree()
    {
        return null;
    }

    @Override
    public HashMap<String, Set<String>> getOperationsByUserName(String[] strings) throws FIException
    {
        return null;
    }

    @Override
    public void setResTreeNodeSelected(
            DefaultMutableTreeNode defaultMutableTreeNode, String[] strings)
    {

    }

    public boolean getCheckRightResult(String operation)
    {
        Boolean result = rightMap.get(operation);
        return result==null?true:result;
    }

    public void setCheckRightResult(String operation,Boolean result){
        rightMap.put(operation,result);
    }
}
