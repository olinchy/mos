package com.zte.uep.stub;

import com.zte.ums.uep.api.pfl.printservice.ExportService;
import com.zte.ums.uep.api.pfl.printservice.IExportDataProvider;
import com.zte.ums.uep.api.pfl.printservice.IListener;
import com.zte.ums.uep.util.print.exporter.ExportException;
import com.zte.ums.uep.util.print.exporter.ExportFormat;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by pavel on 15-8-15.
 */
public class ExportServiceMock implements ExportService{
    private ExportFormat format = null;
    private ArrayList<String[]> exportData = null;
    private String fileName = null;
    private JTable table = null;

    public ExportFormat getFormat() {
        return format;
    }

    public ArrayList<String[]> getExportData() {
        return exportData;
    }

    public String getFileName() {
        return fileName;
    }

    public JTable getTable() {
        return table;
    }

    @Override
    public void exportWithMultiThread(Component component, IExportDataProvider iExportDataProvider, IListener iListener, ExportFormat exportFormat, Object o, int i, boolean b) throws ExportException {

    }

    @Override
    public void export(Component component, IExportDataProvider iExportDataProvider, IListener iListener, ExportFormat exportFormat, Object o, int i, boolean b) throws ExportException {
        this.format = exportFormat;
        this.exportData = (ArrayList<String[]>)o;
    }

    @Override
    public void export(Component component, JTable jTable, IListener iListener, String s) throws ExportException {
        this.fileName = s;
        this.table = jTable;
    }

    @Override
    public void export(Component component, JTable jTable, IListener iListener, String s, ExportFormat exportFormat) throws ExportException {

    }

    @Override
    public void exportMultiSheetWithFormat(
            Component component, JTable[] jTables, IListener iListener, String[] strings, String s,
            ExportFormat[] exportFormats) throws ExportException
    {

    }

    @Override
    public void exportMultiSheet(Component component, JTable[] jTables, IListener iListener, String[] strings, String s) throws ExportException {

    }

    @Override
    public void removeFileFilter(int i) throws ExportException {

    }

    @Override
    public void addFileFilter(int i) throws ExportException {

    }

    @Override
    public void resetFileFilter() throws ExportException {

    }

    @Override
    public void setExcelFileFilterOnly() throws ExportException {

    }

    @Override
    public void showExcelManager() throws ExportException {

    }

    @Override
    public String[] getSheetInfo(String s) throws ExportException {
        return new String[0];
    }

    @Override
    public String getTemplateFile(Component component) throws ExportException {
        return null;
    }
}
