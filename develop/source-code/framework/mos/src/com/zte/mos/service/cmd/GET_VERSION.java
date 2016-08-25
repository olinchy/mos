package com.zte.mos.service.cmd;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;


class GET_VERSION implements MoCmdHandler
{
    @Override
    public MoCmds getCmd()
    {
        return MoCmds.MoVersionRequest;
    }

    @Override
    public Result execute(MoMsg msg, MOS mos) throws MOSException
    {return null;
        /*
        if (msg.dn().equals(MOS.ROOT_INTERNAL_DN))
        {
            ObjectNode res = newObjNode();

            res.put("version", mos.getMetaVersion());
            try
            {
                res.put("configs", getConfigs(mos));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return new Successful();
        }
        else
        {
            ManagementObject destMO = mos.getMO(msg.dn(), new Maybe<Integer>(null));
            if (destMO != null)
            {
                try
                {
                    return destMO.onMsg(msg);
                }
                catch (RemoteException e)
                {
                    throw new MOSException(e);
                }
            }
            else
            {
                throw new MOSException("no such elements:" + msg.dn());
            }
        }*/

    }

    private String getConfigs(MOS mos) throws Exception
    {return null;
/*
        StringBuffer buffer = new StringBuffer();
        InputStream is = null;
        try
        {
            File file = mos.getMetaPackageFile();
            byte[] bytes = new byte[(int) file.length()];
            is = new FileInputStream(file);
            is.read(bytes);
            buffer.append(new sun.misc.BASE64Encoder().encodeBuffer(bytes));
        }
        finally
        {
            if (is != null)
            {
                is.close();
            }
        }
        return buffer.toString();*/
    }
}
