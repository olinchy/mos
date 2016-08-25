package com.zte.mos.msg.impl.rpc;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Created by yangfeng on 16-2-14.
 */
public class TestRpcSession
{
    @Test
    public void test() throws Exception
    {
        RpcSession session = new RpcSession(new TargetAddress()
        {
            @Override
            public String getTargetID()
            {
                return null;
            }

            @Override
            public String getIpAddress()
            {
                return "10.86.56.149";
            }

            @Override
            public IMosHead getMosHead()
            {
                return new IMosHead()
                {
                    @Override
                    public float version()
                    {
                        return 1.1f;
                    }

                    @Override
                    public SupportedProtocol defaultProtocol()
                    {
                        return SupportedProtocol.RPC;
                    }

                    @Override
                    public MsgMode msgMode()
                    {
                        return MsgMode.Single;
                    }
                };
            }

            @Override
            public ModelHead getModelHead()
            {
                return null;
            }

            @Override
            public ImagedSystem getSystem()
            {
                return null;
            }
        }, new RpcUserConfiguration("", "", 5000));
        RpcConnectResponse response = session.connect();
        while (true)
        {
            try
            {
                if (response.getFuture().isDone())
                {
                    System.out.println(response.getFuture().get());
                    break;
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
