package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.FileTransferContextService;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pavel on 15-9-17.
 */
public class FileTransferContextServiceMock implements FileTransferContextService{
    private Map<String,FileTransferContext> fileTransferContextMap = null;
    public FileTransferContextServiceMock(){
        fileTransferContextMap = new HashMap<String, FileTransferContext>();
    }
    @Override
    public FileTransferContext getFileTransferContext(String ftpUserName) {
        FileTransferContext fileTransferContext = fileTransferContextMap.get(ftpUserName);
        if (fileTransferContext==null){
            throw new RuntimeException("Can not get FileTransferContext by "+ftpUserName);
//            return new FileTransferContext("127.0.0.1",21,22,ftpUserName,"defaultPassword");
        }
        return fileTransferContext;
    }

    public void addFileTransferContext(String ftpUserName,FileTransferContext fileTransferContext){
        fileTransferContextMap.put(ftpUserName,fileTransferContext);
    }
}
