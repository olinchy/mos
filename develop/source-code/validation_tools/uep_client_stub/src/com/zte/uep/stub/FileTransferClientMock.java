package com.zte.uep.stub;

import com.zte.ums.uep.api.psl.filetransfer.FileTransferClient;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferClientEventListener;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferClientException;

/**
 * Created by pavel on 15-9-17.
 */
public class FileTransferClientMock implements FileTransferClient{
    @Override
    public void getByAsyn(String s, String s1, FileTransferClientEventListener fileTransferClientEventListener) throws FileTransferClientException {

    }

    @Override
    public void putByAsyn(String s, String s1, int i, int i1, FileTransferClientEventListener fileTransferClientEventListener) throws FileTransferClientException {

    }

    @Override
    public void putFile(String s, String s1, int i, int i1) throws FileTransferClientException {

    }

    @Override
    public void putDir(String s, String s1, int i) throws FileTransferClientException {

    }

    @Override
    public void get(String s, String s1) throws FileTransferClientException {

    }

    @Override
    public void put(String s, String s1, int i, int i1) throws FileTransferClientException {

    }

    @Override
    public void quit() throws FileTransferClientException {

    }

    @Override
    public void delete(String s) throws FileTransferClientException {

    }

    @Override
    public void rmdir(String s) throws FileTransferClientException {

    }

    @Override
    public void rename(String s, String s1) throws FileTransferClientException {

    }

    @Override
    public void mkdir(String s) throws FileTransferClientException {

    }

    @Override
    public void chdir(String s) throws FileTransferClientException {

    }

    @Override
    public String pwd() throws FileTransferClientException {
        return null;
    }

    @Override
    public String[] dir(String s, boolean b) throws FileTransferClientException {
        return new String[0];
    }

    @Override
    public String list(String s, boolean b) throws FileTransferClientException {
        return null;
    }

    @Override
    public long getFileSize(String s) throws FileTransferClientException {
        return 0;
    }

    @Override
    public void abort() throws FileTransferClientException {

    }

    @Override
    public void getFile(String s, String s1) throws FileTransferClientException {

    }

    @Override
    public String[] getFiles(String s, String s1) throws FileTransferClientException {
        return new String[0];
    }
}
