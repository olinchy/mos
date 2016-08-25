from kernel.framework.case import TestCase
from kernel.drivers.cli import *
import time
from kernel.drivers.NE.NE import NE
from kernel.drivers.cli.Cli import Cli
            
class Persistence(TestCase):

    def setUp(self):
        self.ne = NE({'port': '8090'})
        self.ne.open()
        self.cli = Cli("cli", 8090)
        self.success = {'result': 0}
        self.failure = {'result': 1}
        self.cesAlarmPara = {"mo" : {
		    "bufferOverRunAlarm" : 5,
		    "lateFrameAlarm" : 5,
		    "lateFrameClear" : 3,
		    "lofAlarm" : 100,
		    "lofClear" : 3,
		    "malFormAlarm" : 5,
		    "misConnAlarm" : 100
	        },"result" : 0 }

    def tearDown(self):
        self.cli.close()
        self.ne.close()

    def test_set_multiple_attributes_shourld_persistence_correctly(self):
        self.cli.proc('starttrans')\
                .proc('add /Shelf/1')\
                .proc('commit')\
                .assert_result(self.success)\
                .proc('endtrans commit')\
                .proc('set /Shelf/1/CesAlarmThreshold/1')\
                .proc('misConnAlarm 100')\
                .proc('commit')\
                .assert_result(self.success)\
                .proc('set /Shelf/1/CesAlarmThreshold/1')\
                .proc('lofAlarm 100')\
                .proc('commit')\
                .assert_result(self.success)
        self.cli.close()
        self.ne.close()
        self.ne.open()
        self.cli = Cli("cli", 8090)

        self.cli.proc('get /Shelf/1/CesAlarmThreshold/1')\
                .assert_result(self.cesAlarmPara)\
                .proc('del /Shelf/1')


    def test_set_multiple_attributes_in_one_trans_shourld_persistence_correctly(self):
        self.cli.proc('starttrans')\
                .proc('add /Shelf/1')\
                .proc('commit')\
                .assert_result(self.success)\
                .proc('endtrans commit')\
                .proc('starttrans')\
                .proc('set /Shelf/1/CesAlarmThreshold/1')\
                .proc('misConnAlarm 100')\
                .proc('commit')\
                .assert_result(self.success)\
                .proc('set /Shelf/1/CesAlarmThreshold/1')\
                .proc('lofAlarm 100')\
                .proc('commit')\
                .assert_result(self.success)\
                .proc('endtrans commit')\
                .proc('get /Shelf/1/CesAlarmThreshold/1')\
                .assert_result(self.cesAlarmPara)
        self.cli.close()
        self.ne.close()
        self.ne.open()
        self.cli = Cli("cli", 8090)

        self.cli.proc('get /Shelf/1/CesAlarmThreshold/1')\
                .assert_result(self.cesAlarmPara)\
                .proc('del /Shelf/1')\
                .assert_result(self.success)

    
if __name__ == "__main__":
    unittest.main()
    
