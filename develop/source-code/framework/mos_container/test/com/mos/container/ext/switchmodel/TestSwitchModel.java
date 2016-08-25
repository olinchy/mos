package com.mos.container.ext.switchmodel;

import com.mos.UT_MOS;
import com.zte.container.ext.switchmodel.ModelSwitchContext;
import com.zte.container.ext.switchmodel.ModelSwitchTask;
import com.zte.mos.container.BundleObject;
import com.zte.mos.container.NeDomain;
import com.zte.mos.container.RealBundleDomain;
import com.zte.mos.domain.*;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.ActionRsp;
import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.service.MOS;
import com.zte.mos.util.msg.MoActionMsg;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static com.zte.mos.container.NeDomain.newInstance;

/**
 * Created by ccy on 5/17/16.
 */
class ModelHeadStub extends ModelHead
{
    public ModelHeadStub(String modelName, String modelVersion)
    {
        super(null);
        this.name = modelName;
        this.version = modelVersion;
    }

    private final String name;
    private final String version;

    public String modelName()
    {
        return name;
    }

    public String msgVersion()
    {
        return version;
    }

    public String modelVersion()
    {
        return null;
    }
}

public class TestSwitchModel
{
    private String modelName;
    private String modelVersion;
    private BundleObject service;
    private NeDomain localNe;
    private ModelSwitchContext context;
    private ModelSwitchTask task;
    private UT_MOS imageMos;
    private ModelHeadStub modelHead;
    private ModelMETA modelMeta;

    @Before
    public void setUp() throws Exception
    {
        this.modelName = "nr8250";
        this.modelVersion = "v242";
        RealBundleDomain bundleDomain = RealBundleDomain.getInstance();
        this.service = new BundleObject(bundleDomain);
        this.localNe = newInstance(new NeIdentity("/Ems/1/Ne/1", "127.0.0.1", "nr8250"));
        this.imageMos = new UT_MOS();
        this.localNe.setMos(imageMos);
        this.context = new ModelSwitchContext(this.modelName, this.modelVersion, this.service, localNe);
        this.task = new ModelSwitchTask(this.context);
        this.modelHead = new ModelHeadStub(this.modelName, this.modelVersion);
        this.modelMeta = new ModelMETA(this.modelHead, null);
        this.imageMos.setModelMeta(this.modelMeta);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void should_deliver_switch_failed_event_if_lock_local_ne_failed() throws Exception
    {
        imageMos.lock();
        task.run();
    }

    @Test
    public void should_deliver_switch_failed_event_if_build_mo_tree_failed() throws Exception
    {
        task.run();
    }

    @Test
    public void should_deliver_switch_failed_event_if_start_remote_rmi_failed() throws Exception
    {
        imageMos.createMO(
                new ManagementObject()
                {
                    public String myDn = "/";

                    @Override
                    public DN dn()
                    {
                        return new DN(myDn);
                    }

                    @Override
                    public void setDn(String dn)
                    {
                        this.myDn = dn;
                    }

                    @Override
                    public void setParent(ManagementObject parent)
                    {

                    }

                    @Override
                    public void create() throws MOSException
                    {

                    }

                    @Override
                    public ActionRsp act(MOS mos, MoActionMsg msg) throws MOSException
                    {
                        return null;
                    }

                    @Override
                    public Mo afterGet() throws MOSException
                    {
                        return null;
                    }

                    @Override
                    public Mo toMoClass() throws MOSException
                    {
                        Mo mo = new Mo("");
                        mo.setDn(new DN(myDn));
                        return mo;
                    }

                    @Override
                    public Object referencedKilled(String fieldName, DN who) throws MOSException
                    {
                        return null;
                    }

                    @Override
                    public MoMetaClass meta() throws MOSException
                    {
                        return null;
                    }

                    @Override
                    public MoMetaClass childMeta(String childName) throws MOSException
                    {
                        return null;
                    }

                    @Override
                    public void setMos(MOS mos)
                    {

                    }

                    @Override
                    public List<String> lsDN()
                    {
                        return null;
                    }

                    @Override
                    public ManagementObject clone()
                    {
                        return null;
                    }

                    @Override
                    public ManagementObject[] ls() throws MOSException
                    {
                        return new ManagementObject[0];
                    }

                    @Override
                    public boolean add(ManagementObject managementObject)
                    {
                        return false;
                    }

                    @Override
                    public boolean remove(ManagementObject managementObject)
                    {
                        return false;
                    }

                    @Override
                    public boolean addAll(Collection<? extends ManagementObject> c)
                    {
                        return false;
                    }

                    @Override
                    public boolean contains(Object o)
                    {
                        return false;
                    }

                    @Override
                    public boolean isGroup()
                    {
                        return false;
                    }

                    @Override
                    public void destroy() throws MOSException
                    {

                    }
                }, new Maybe<Integer>());
        task.run();
    }
}
