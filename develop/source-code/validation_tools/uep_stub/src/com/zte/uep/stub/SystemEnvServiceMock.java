package com.zte.uep.stub;

import com.zte.ums.api.usf.bsf.system.*;
import com.zte.ums.api.usf.env.*;
import com.zte.ums.uep.api.pfl.emb.EMBUrl;

import java.util.List;

/**
 * Created by pavel on 16-1-14.
 */
public class SystemEnvServiceMock implements SystemEnvService
{
    @Override
    public Path getUsfEmbPath(String s, String s1)
    {
        return null;
    }

    @Override
    public boolean isCluster()
    {
        return false;
    }

    @Override
    public boolean isMain()
    {
        return false;
    }

    @Override
    public boolean isHmf()
    {
        return false;
    }

    @Override
    public boolean isSupportHmf()
    {
        return false;
    }

    @Override
    public boolean isSupportPmf()
    {
        return false;
    }

    @Override
    public boolean isHnaf()
    {
        return false;
    }

    @Override
    public boolean isHemf()
    {
        return false;
    }

    @Override
    public Node getCurrentNode()
    {
        return null;
    }

    @Override
    public Node[] getRunningNodes()
    {
        return new Node[0];
    }

    @Override
    public Ppu[] getRunningPpus()
    {
        return new Ppu[0];
    }

    @Override
    public Pmu[] getRunningPmus()
    {
        return new Pmu[0];
    }

    @Override
    public PmuFunction[] getRunningPmuFunctions()
    {
        return new PmuFunction[0];
    }

    @Override
    public void addNodeListener(NodeListener nodeListener)
    {

    }

    @Override
    public void removeNodeListener(NodeListener nodeListener)
    {

    }

    @Override
    public void addPmfListener(PmfListener pmfListener) {

    }

    @Override
    public void removePmfListener(PmfListener pmfListener) {

    }

    @Override
    public void addPmuFunctionListener(PmuFunctionListener pmuFunctionListener)
    {

    }

    @Override
    public void removePmuFunctionListener(PmuFunctionListener pmuFunctionListener)
    {

    }

    @Override
    public JNDIUrl getJNDIUrl(Path path)
    {
        return null;
    }

    @Override
    public EMBUrl getEMBUrl(Path path)
    {
        return null;
    }

    @Override
    public Node getNode(String s, String s1)
    {
        return null;
    }

    @Override
    public Node getPeerEmsNode(String s, String s1)
    {
        return null;
    }

    @Override
    public Node getNode(Path path)
    {
        return null;
    }

    @Override
    public Node getPeerEmsNode(Path path)
    {
        return null;
    }

    @Override
    public Node getClusterNode()
    {
        return null;
    }

    @Override
    public Node getMainNode()
    {
        return null;
    }

    @Override
    public Node getHmfNode()
    {
        return null;
    }

    @Override
    public Path getLocalPath(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public Path getPath(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public Path getPeerEmsPath(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public Path[] getPaths(String s, String s1, String s2)
    {
        return new Path[0];
    }

    @Override
    public Path[] getPeerEmsPaths(String s, String s1, String s2)
    {
        return new Path[0];
    }

    @Override
    public Path getPath(String s, String s1, String s2, String s3)
    {
        return null;
    }

    @Override
    public Path[] getPaths(String s, String s1, String s2, String s3)
    {
        return new Path[0];
    }

    @Override
    public JNDIUrl getJNDIUrl(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public JNDIUrl[] getJNDIUrls(String s, String s1, String s2)
    {
        return new JNDIUrl[0];
    }

    @Override
    public JNDIUrl getJNDIUrl(String s, String s1, String s2, String s3)
    {
        return null;
    }

    @Override
    public JNDIUrl[] getJNDIUrls(String s, String s1, String s2, String s3)
    {
        return new JNDIUrl[0];
    }

    @Override
    public EMBUrl getEMBUrl(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public EMBUrl[] getEMBUrls(String s, String s1, String s2)
    {
        return new EMBUrl[0];
    }

    @Override
    public EMBUrl getEMBUrl(String s, String s1, String s2, String s3)
    {
        return null;
    }

    @Override
    public EMBUrl[] getEMBUrls(String s, String s1, String s2, String s3)
    {
        return new EMBUrl[0];
    }

    @Override
    public Node getNode(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public Node[] getNodes(String s, String s1, String s2)
    {
        return new Node[0];
    }

    @Override
    public Node getPeerEmsNode(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public Node[] getPeerEmsNodes(String s, String s1, String s2)
    {
        return new Node[0];
    }

    @Override
    public Node getNode(String s, String s1, String s2, String s3)
    {
        return null;
    }

    @Override
    public Node[] getNodes(String s, String s1, String s2, String s3)
    {
        return new Node[0];
    }

    @Override
    public boolean atSameHost(Node node, Node node1)
    {
        return false;
    }

    @Override
    public boolean atSameNode(String s, int i)
    {
        return false;
    }

    @Override
    public String addLowerLayerUMS(String s, int i) throws EnvException
    {
        return null;
    }

    @Override
    public void removeUMSNode(String s) throws EnvException
    {

    }

    @Override
    public EMBUrl getNextTargetEMBUrl(Path path)
    {
        return null;
    }

    @Override
    public Node getHmfNodeInEms(String s)
    {
        return null;
    }

    @Override
    public Node getMainNodeInPeerEms()
    {
        return null;
    }

    @Override
    public Path getHmfPathInEms(String s)
    {
        return null;
    }

    @Override
    public Node getMainNodeInEms(String s)
    {
        return null;
    }

    @Override
    public Node getClusterNodeInEms(String s)
    {
        return null;
    }

    @Override
    public Node getClusterNodeInPeerEms()
    {
        return null;
    }

    @Override
    public List<InstallPmuFunction> getInstallPmuFunctions(String s, String s1, String s2)
    {
        return null;
    }

    @Override
    public List<InstallPmuFunction> getInstallPmuFunctionsInEms(String s, String s1, String s2, String s3)
    {
        return null;
    }

    @Override
    public String getNodeId(String s, String s1, String s2, String s3)
    {
        return null;
    }

    @Override
    public Object[] getEMBUrlAndPath(Path path)
    {
        return new Object[0];
    }

    @Override
    public String getPeerUmsId()
    {
        return null;
    }
}
