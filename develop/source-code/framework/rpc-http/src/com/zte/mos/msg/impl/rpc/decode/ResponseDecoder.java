package com.zte.mos.msg.impl.rpc.decode;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.msg.framework.Decoder;
import com.zte.mos.msg.framework.operation.IOperation;
import com.zte.mos.msg.framework.operation.IResponse;
import com.zte.mos.util.Scan;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by luoqingkai on 15-5-19.
 */
public class ResponseDecoder {

    private static HashMap<Class<? extends IOperation>, IDecoder> decoderMap =
            new HashMap<Class<? extends IOperation>, IDecoder>();

    static{
        //decoderMap.put(Connect.class, new ConnectDecoder());
        Set<Class<Object>> set = Scan.getClasses("com.zte.mos.msg.impl.rpc.decode");
        if (!set.isEmpty()){
            for (Class<Object> clazz : set){
                if (!clazz.isInterface() && clazz.isAnnotationPresent(Decoder.class)){
                    Class<? extends IDecoder> implClazz = clazz.asSubclass(IDecoder.class);
                    Constructor<? extends IDecoder> constructor = null;
                    try
                    {
                        constructor = implClazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        IDecoder decoder = constructor.newInstance();
                        decoderMap.put(decoder.getOperationClazz(), decoder);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static IResponse decode(Class<? extends IOperation> clazz, JsonNode node)
            throws NoDecoderException{
        IDecoder decoder = decoderMap.get(clazz);
        if (decoder != null){
            return decoder.decode(node);
        }else{
            throw new NoDecoderException();
        }
    }
}
