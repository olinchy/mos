package com.zte.uep.stub;

import com.zte.ums.uep.api.psl.filetransfer.FileTransferClient;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferClientException;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferClientService;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferContext;

/**
 * Created by pavel on 15-9-17.
 */
public class FileTransferClientServiceMock implements FileTransferClientService{
    @Override
    public FileTransferClient getFileTransferClient(FileTransferContext fileTransferContext) throws FileTransferClientException {
        return new FileTransferClientMock();
    }
}
