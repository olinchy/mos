package com.zte.rpc;

import com.zte.mos.domain.IMosHead;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.MsgMode;
import com.zte.mos.msg.SupportedProtocol;
import com.zte.mos.msg.impl.rpc.RpcConnectResponse;
import com.zte.mos.msg.impl.rpc.RpcSession;
import com.zte.mos.msg.impl.rpc.RpcUserConfiguration;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by olinchy on 16-2-23.
 */
public class Test
{
    public static void main(final String[] args)
    {
        RpcSession session = null;
        try
        {
            session = new RpcSession(new TargetAddress()
            {
                @Override
                public String getTargetID()
                {
                    return null;
                }

                @Override
                public String getIpAddress()
                {
                    return args[0];
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
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
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
