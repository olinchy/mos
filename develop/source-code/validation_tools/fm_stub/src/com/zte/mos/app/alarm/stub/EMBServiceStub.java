package com.zte.mos.app.alarm.stub;

import com.zte.ums.api.usf.bsf.system.Path;
import com.zte.ums.uep.api.pfl.emb.*;

import java.net.URL;

/**
 * Created by ccy on 8/13/15.
 */
public class EMBServiceStub implements EMBService {
    @Override
    public EMessage syncRequest(EMessage eMessage) throws EMBException {
        return null;
    }

    @Override
    public void asyncRequest(EMessage eMessage, AsyncMessageListener asyncMessageListener) throws EMBException {

    }

    @Override
    public void asyncRequest(EMessage eMessage) throws EMBException {

    }

    @Override
    public void asyncResponse(EMessage eMessage, EMessage eMessage1) throws EMBException {

    }

    @Override
    public void asyncException(EMessage eMessage, EMBException e) throws EMBException {

    }

    @Override
    public void sendAsyncAndSyncResponse(EMessage eMessage, EMessage eMessage1, boolean b) throws EMBException {

    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, Path path) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, String s1) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, Path path, String s1) throws EMBException {
        return null;
    }

    @Override
    public void unrigisterNotification(NotificationRegistryInfo notificationRegistryInfo) {

    }

    @Override
    public void sendNotification(String s, EMessage eMessage) throws EMBException {

    }

    @Override
    public EMessage newEMessage(int i) {
        return null;
    }

    @Override
    public EMessage newEMessage(int i, Path path, Path path1) {
        return null;
    }

    @Override
    public EMessage newEMessage(String s, Path path, Path path1) {
        return null;
    }

    @Override
    public EMessage new3XEMessage(int i, EMBUrl embUrl) {
        return null;
    }

    @Override
    public EMessage newBinEMessage(int i, Path path, Path path1, byte[] bytes) {
        return null;
    }

    @Override
    public int getMsgSize(EMessage eMessage) {
        return 0;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, EMBUrl embUrl) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, EMBUrl embUrl, String s1) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, EMBUrl embUrl, String s1, boolean b) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, Path path, int i) throws EMBException {
        return null;
    }

    @Override
    public void putEmbXmlFile(URL url, URL url1, int i) throws EMBException {

    }

    @Override
    public void sendBatchNotifications(EMessage[] eMessages, String s, Path path, Path path1) throws EMBException {

    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, NotificationDiscardListener notificationDiscardListener, String s1) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, NotificationDiscardListener notificationDiscardListener, Path path, String s1) throws EMBException {
        return null;
    }

    @Override
    public NotificationRegistryInfo registerNotification(String s, NotificationListener notificationListener, NotificationDiscardListener notificationDiscardListener, EMBUrl embUrl, String s1) throws EMBException {
        return null;
    }
}
