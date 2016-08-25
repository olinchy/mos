#coding=gbk
'''
Created on 2011-8-15

@author: ����
'''
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
from email.MIMEImage import MIMEImage
from email.MIMEBase import MIMEBase
from email import Encoders
import smtplib
import os
import logging
import logging.handlers
import time
import sys
import types
import codecs
from xml.dom import minidom 
import datetime

def sendEmail(server, port,mailSender, mailReceivers, subject, plainText, htmlText, attaches=[]):
        """
�����ʼ���������ͨ�ı���html�͸�������������Ϊ��
        """
        #server = authInfo.get('server')
        #port = authInfo.get('port')
        #user = authInfo.get('user')
        #passwd = authInfo.get('password')

        if not (server  ) :
                print 'incomplete login info, exit now'
                return

        # �趨root��Ϣ
        msgRoot = MIMEMultipart('related')
        msgRoot['Subject'] = subject
        msgRoot['From'] = mailSender
        msgRoot['To'] =  ','.join(mailReceivers)
        msgRoot.preamble = 'This is a multi-part message in MIME format.'

        # Encapsulate the plain and HTML versions of the message body in an
        # 'alternative' part, so message agents can decide which they want to display.
        msgAlternative = MIMEMultipart('alternative')
        msgRoot.attach(msgAlternative)

        #�趨���ı���Ϣ
        msgText = MIMEText(plainText, 'plain', 'utf-8')
        msgAlternative.attach(msgText)

        #�趨HTML��Ϣ
        msgText = MIMEText(htmlText, 'html', 'utf-8')
        msgAlternative.attach(msgText)
        
        for attach in attaches:
            part = MIMEBase('application', "octet-stream")
            part.set_payload( open(attach,"rb").read() )
            Encoders.encode_base64(part)
            part.add_header('Content-Disposition', 'attachment; filename="%s"' % os.path.basename(attach))
            msgRoot.attach(part)

        #�趨����ͼƬ��Ϣ
#        fp = open('test.jpg', 'rb')
#        msgImage = MIMEImage(fp.read())
#        fp.close()
#        msgImage.add_header('Content-ID', '<image1>')
#        msgRoot.attach(msgImage)

        #�����ʼ�
        smtp = smtplib.SMTP(server,port)
        #�趨���Լ������������
#        smtp.set_debuglevel(1)
        #smtp.ehlo() 
        #smtp.starttls() 
        #smtp.ehlo() 
        #smtp.login(user, passwd)
        for Receiver in mailReceivers: #�����˷����ʼ�ʱ������ֻ�е�һ�����ܹ��յ�
            #smtp.sendmail(mailSender,  msgRoot['To'], msgRoot.as_string())
            time.sleep(3)  #�����˷����ʼ���ʱ����
            smtp.sendmail(mailSender, GetMailReciver(mailReceivers,Receiver), msgRoot.as_string())
        smtp.quit()
        return

def GetMailReciver(ReceiverList,DestReceiver):
    RecvListTemp=list(ReceiverList)
    RecvListTemp.remove(DestReceiver)
    OtherReceivers=','.join(RecvListTemp)
    return DestReceiver+','+OtherReceivers


class TestReport(object):
    def __init__(self, name = "", startTime = "0",result = "", message = "", total = 0, succeed = 0, failure = 0, error = 0):
        self.name = name
        self.message = message
        self.totalCount = total
        self.succeedCount = succeed
        self.failureCount = failure
        self.result = result
        self.startTime = startTime
        self.errorCount = error


class ReportManager(object):
    def __init__(self, reportDir):
        self.reportDir = reportDir
        
        self.tests = '0'
        self.failures = '0'
        self.errors = '0'
        self.startTime = []
        self.name = []
        
    def CreateReport(self):
        '''
        ����һ��������������������Խ���ı�����־
        '''
        name="TestResult"
         
        console = logging.StreamHandler()
        console.setLevel( logging.NOTSET )
            # set a format which is simpler for console use
        formatter = logging.Formatter('%(asctime)s    %(message)s', '%Y-%m-%d %H:%M:%S' )
            # tell the handler to use this format
        console.setFormatter( formatter )
            # add the handler to the root logger
        logging.getLogger( "testReport" ).addHandler( console)
        Log=logging.getLogger( name )
        Log.setLevel( logging.NOTSET )
        return Log
            
    def addTestReport(self, testReport):
        '''
        ÿ��ִ����һ���������ݾ�Ҫ���øú��������ڼ�¼����ʱ���Լ����
        testcase ��һ������ʵ������Ӧ��һ�����Ժ�����һ���������ݼ�
        '''
        self._setCounts(testReport)
        self._addTestReport(testReport)
        self._writeXmlReportToFile()
        self.createHTMLReport()
        
    def startTestRun(self, projName):
        '''
        ��ִ�в�������ǰҪ���øú�������������ϴεĲ�����Ϣ��
        testcase ��һ������ʵ������Ӧ��һ�����Ժ�����һ���������ݼ�
        '''
        self._createNewReport(projName)     
        self._writeXmlReportToFile()
        self.createHTMLReport()
        
    def sendReportMail(self, subject,mailServer,sender,mailReceiversList):
        '''
        ��ִ����ȫ���Ĳ������ݺ���øú����������ʼ����Ͳ��Խ��
        '''
        fp = open(self.htmlFileName, 'rb')
        htmlText=fp.read()
        plainText="������ʾ"
        sendEmail(mailServer, 25,sender, mailReceiversList, subject, plainText, htmlText, attaches=[self.htmlFileName])
        
        #self._createMailmsg(subject)     
        #self._sendMail(mailServer,sender,mailReceiversList)
        
    def _createMailmsg(self, subject):
        '''
        
        ����ʹ�÷�ʽ���£�mpack -s "Today's build" -d tt.h -o body.msg  tt.h
ֻ�����ʹ�ã�
�����·�����������������mail�ļ�
mailSender\mpack.exe -s "Today's build" -d mailSender\report.xml -o body.msg  mailSender\report.xml
���ܺͳ�·�� �������й�ϵ
����ֻ�ܸ���python�ĵ�ǰ����Ŀ¼��mpack��Ŀ¼�С�
        '''
        title = subject
        reportxml = self.reportFileName
        reportDestDir = self._getMailSenderDir() + os.sep + "report.xml"
        self.MailMsg = self._getMailSenderDir() + os.sep + "body.msg"
        open(reportDestDir, "wb").write(open(reportxml, "rb").read()) 
        cwd=os.getcwd()
        os.chdir(self._getMailSenderDir())
        cmd="mpack -s \"%s\" -d report.xml -o body.msg  report.xml" % (subject)
        #cmd = "%s -s \"%s\" -d %s -o %s %s" % (mpackDir,title,reportxml,self.MailMsg,reportxml)
        f=os.popen(cmd)
        data=f.readlines()
        os.chdir(cwd)
        return data
    
    def _getToolsDir(self):

        kernelDir = os.path.dirname(sys.modules[ReportManager.__module__].__file__)
        relateDir = os.sep + ".." + os.sep + ".." + os.sep + "tools" 
        return os.path.abspath(kernelDir + relateDir)
    def _getMailSenderDir(self):
        
        toolsDir = self._getToolsDir()
        relateDir= os.sep + "mailSender"
        return os.path.abspath(toolsDir + relateDir)
    def _sendMail(self,mailServer,sender,receivers):
        
        if  self.MailMsg:
            dmailDir = self._getMailSenderDir() + os.sep + "bmail.exe"
            cwd=os.getcwd()
            os.chdir(self._getMailSenderDir())
            receiver = ""
            for item in receivers:
                receiver = receiver + item+" "
            cmd="%s -s %s -t %s -f %s -m %s" %(dmailDir,mailServer,receiver,sender,self.MailMsg)   
            f = os.popen(cmd)
            data = f.readlines()
            os.chdir(cwd)
            return data

    def _setCounts(self, testReport):
        
        self.tests = str(testReport.totalCount)
        self.failures = str(testReport.failureCount)
        self.errors = str(testReport.errorCount)
        self.startTime.append(str(testReport.startTime))  
        self.name.append(str(testReport.name))
            
    def _addTestReport(self, testReport):
        
        testsuites = self.reportdoc.getElementsByTagName("testsuites")[0]
        testcase=self.reportdoc.createElement("testcase")
        
        #��ȡ����������
        nameList = testReport.name.split('.')
        testcase.setAttribute("classname",nameList[0])
        
        #��ȡ������������
        name = ''
        i = 1
        while i < len(nameList):
            name += nameList[i]
            if i != len(nameList) - 1:
                name += '.'
            i += 1
        testcase.setAttribute("name",name)
        testcase.setAttribute("status",'run')
        
        import time
        starttime = time.strptime( testReport.startTime, "%Y-%m-%d %H:%M:%S" )
        starttime = datetime.datetime(*starttime[:6])
        endtime = time.localtime()
        endtime = datetime.datetime(*endtime[:6])
        runtime = (endtime - starttime).seconds

        testcase.setAttribute("time",str(runtime))  #��ȷ���޸ġ�����������������������������������������������
                       
        if str(testReport.result) != "Pass":
            failure=self.reportdoc.createElement("failure")
            failure.setAttribute("type",str(testReport.result))
            text = self.reportdoc.createTextNode( str(testReport.message) )
            failure.appendChild(text)
            testcase.appendChild(failure)
        else:
            PassBy=self.reportdoc.createElement("Pass")
            PassBy.setAttribute("type",str(testReport.result))
            text = self.reportdoc.createTextNode( str(testReport.message) )
            PassBy.appendChild(text)
            testcase.appendChild(PassBy)
               
        testsuites.appendChild(testcase)
        
        
    def _writeXmlReportToFile(self):
        
        f = codecs.open(self.reportFileName,'w','utf-8')
        self.reportdoc.writexml(f, addindent='  ', newl='\n',encoding='utf-8')
        f.close()
        
    def _createNewReport(self,projName):
        #ztq,2012-4-5,����ǰ�����ı����Ŀ¼��д���ļ�LastReportDirectory.txt�У��汾�����Զ�������Ҫ
        #TxtFilePathForSaveReportName=self.reportDir[:self.reportDir.rfind(os.sep)]+os.sep+'LastReportDirectory.txt'
        #FileObj=open(TxtFilePathForSaveReportName,'w+')
        #FileObj.write(self.reportDir)
        #FileObj.write("\n")
        #FileObj.close()
        
        self.reportFileName = self.reportDir + os.sep + "TestReport" + os.sep + "report.xml"
        dirname = os.path.split( self.reportFileName )[0]
        if not os.path.isdir( dirname ):
            os.makedirs( dirname )
        # ����Document
        self.reportdoc = minidom.Document()
        # ����report�ڵ�
        testsuites = self.reportdoc.createElement("testsuites")
        testsuites.setAttribute("name",projName)
        self.reportdoc.appendChild(testsuites)

        
    def createHTMLReport(self):
        
        self.htmlFileName = self.reportDir + os.sep + "TestReport" + os.sep + "report.html"
        f = codecs.open(self.htmlFileName,'w','utf-8')
        f.write(self.createHTMLDeclaration())
        f.write(self.createHtmlContent())
        f.close()
        
    def createHTMLDeclaration(self):
        declaration = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional \
            //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"> "
        return declaration
    
    def createHtmlContent(self):
        startTag = "<html>"
        endTag = "</html>"
        header = self.createHTMLHeader()
        body = self.createHTMLBody()
        return startTag + header + body +endTag
    
    def createHTMLHeader(self):
        startTag = "<head>"
        endTag = "</head>"
        middleContent = ""
        return startTag + middleContent+ endTag
     
    def createHTMLBody(self):
        startTag = "<body bgcolor=\"#F5FFFA\">"
        endTag = "</body>"
        middleContent = ""
        summery =self.createSummaryHTMReport()
        detail = self.creteDetailHTMLReprot()
        middleContent = summery + detail
        return startTag + middleContent+ endTag  
     
    def createSummaryHTMReport(self):
        startTag = "<div id=\"count\">"
        endTag = "</div>"
        
        testsuites = self.reportdoc.getElementsByTagName("testsuites")[0]
        projName = testsuites.getAttribute("name")
        tests = self.tests
        errors = self.errors
        failures = self.failures

        success = 0
        try:
            success = float(str(tests)) - float(str(failures)) - float(str(errors))
            PassRatioFloat=100*success/float(str(tests))
        except Exception,Msg:
            PassRatioFloat=0
        
        PassRatioStr='%.1f' %PassRatioFloat + '%'
        middleContent = "<b>Total count is: %s, succeed count is: %s, \
        and failure count is: %s,pass ratio is:%s.</b> " %(tests,str(success),failures,PassRatioStr)
        
        return startTag + middleContent+ endTag  
    
    def creteDetailHTMLReprot(self):
        startTag = "<div id=\"report\" style=\"width:500px;\">"
        endTag = "</div>"
        
        TableStartTag = "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"1000\">" 
        TableEndTag = "</table>"
        TableHeader="<tr bgcolor=\"#D3D3D3\"> \
            <th width=\"50\">#</th> \
            <th width=\"200\">Name</th> \
            <th width=\"100\">Result</th> \
            <th width=\"400\">Message</th> \
            <th width=\"200\">Start time</th></tr>"
        testcases = self.reportdoc.getElementsByTagName("testcase")
        testReport = TestReport()
        index=0
        itemsContent = ""
        for item in testcases:            
            TestResult = 'Pass' 
            TestMessage = None
            failure = item._get_lastChild()
            if failure:                
                text = failure._get_lastChild()
                TestMessage = text._get_data()   
                TestResult = failure.getAttribute('type')
                    
            testReport = TestReport(name = self.name[index],
                                    message = TestMessage or "None.",
                                    startTime = self.startTime[index],
                                    result = TestResult)
            index = index + 1
            itemsContent = itemsContent + self.creteHTMLReprotItem(testReport, index)
        middleContent = TableStartTag + TableHeader + itemsContent + TableEndTag 
        return startTag + middleContent + endTag
    
    def creteHTMLReprotItem(self,testReport,index):  
        startTag = "<tr>"
        endTag = "</tr>"   
        indexContent="<td> %d </td>" %(index)
        nameContent="<td> %s </td>" %(testReport.name)
 
        if (testReport.result == "Pass"):
            resultContent ='<td bgcolor="#90EE90">%s</td>' % (testReport.result)
        elif (testReport.result == "Fail"):
            resultContent ='<td bgcolor="#DC143C">%s</td>' % (testReport.result)       
        else:
            resultContent ='<td bgcolor="#FF00FF">%s</td>' % (testReport.result)

        messageContent = '<td>%s </td>' % (testReport.message)
        timeContent = '<td>%s </td>' % (testReport.startTime)
        
        middleContent=indexContent + nameContent + resultContent + messageContent +timeContent
        return startTag + middleContent +endTag
