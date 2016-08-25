package com.zte.mos.app.alarm.stub;

import com.zte.ums.api.common.fm.ppu.entity.AlarmData;
import com.zte.ums.api.common.fm.ppu.entity.FmPlmnEntity;
import com.zte.ums.api.common.fm.ppu.entity.ProbableCause;
import com.zte.ums.uep.api.pfl.embmml.ParseMMLUtil;

import java.util.Date;

/**
 * Created by ccy on 9/15/15.
 */



public class AlarmDataStub implements AlarmData
{
    private final ParseMMLUtil mml;

    public AlarmDataStub(ParseMMLUtil mml)
    {
        this.mml = mml;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getAid() {
        return String.valueOf(mml.getParaValue(0, "Aid"));
    }

    @Override
    public String getAlarmKey() {
        return String.valueOf(mml.getParaValue(0, "AlarmKey"));
    }

    @Override
    public String getMoc() {
        return String.valueOf(mml.getParaValue(0, "Moc"));
    }

    @Override
    public String getPosition1() {
        return String.valueOf(mml.getParaValue(0, "Position1"));
    }

    @Override
    public String getSubPosition1() {
        return String.valueOf(mml.getParaValue(0, "SubPosition1"));
    }

    @Override
    public String getPosition2() {
        return null;
    }

    @Override
    public String getSubPosition2() {
        return null;
    }

    @Override
    public long getAlarmRaisedTime()
    {
        return new Date().getTime();
    }

    @Override
    public String getTimeZoneID() {
        return String.valueOf(mml.getParaValue(0, "TimeZoneID"));
    }

    @Override
    public int getTimeZoneOffset() {
        return Integer.valueOf(mml.getParaValue(0, "TimeZoneOffset").toString());
    }

    @Override
    public int getDSTSaving() {
        return Integer.valueOf(mml.getParaValue(0, "DSTSaving").toString());
    }

    @Override
    public byte getClearType() {
        return Byte.valueOf(mml.getParaValue(0, "ClearType").toString());
    }

    @Override
    public long getAlarmChangedTime() {
        return 0;
    }

    @Override
    public long getAlarmClearedTime()
    {
        return new Date().getTime();
    }

    @Override
    public byte getAlarmType() {
        return 0;
    }


@Override
    public ProbableCause getProbableCause()
    {
        return new ProbableCause() {
            @Override
            public short getSystemType() {
                return 0;
            }

            @Override
            public long getCode() {
                return Long.valueOf(mml.getParaValue(0, "ProbableCause").toString());
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public byte getCodeType() {
                return 0;
            }

            @Override
            public int getNMCReasonCode() {
                return 0;
            }

            @Override
            public String[] getAttrValuesByNames(String[] strings) {
                return new String[0];
            }
        };
    }

    @Override
    public String getSpecificProblem() {
        return null;
    }

    @Override
    public long getReasonCode() {
        return Long.valueOf(mml.getParaValue(0, "ReasonCode").toString());
    }

    @Override
    public byte getPerceivedSeverity() {
        return Byte.valueOf(mml.getParaValue(0, "PerceivedSeverity").toString());
    }

    @Override
    public String getAdditionalText() {
        return String.valueOf(mml.getParaValue(0, "AdditionalText"));
    }

    @Override
    public String[] getCustomAttrs()
    {
        return mml.getParaArray(0, "CustomAttrs");
    }

    @Override
    public byte getAckState() {
        return 0;
    }

    @Override
    public long getAckTime() {
        return 0;
    }

    @Override
    public String getAckUserId() {
        return null;
    }

    @Override
    public String getAckSystemId() {
        return null;
    }

    @Override
    public String getClearUserId() {
        return null;
    }

    @Override
    public String getClearSystemId() {
        return null;
    }

    @Override
    public String getCommentText() {
        return null;
    }

    @Override
    public long getCommentTime() {
        return 0;
    }

    @Override
    public String getCommentUserId() {
        return null;
    }

    @Override
    public String getCommentSystemId() {
        return null;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isNafFiltered() {
        return false;
    }

    @Override
    public byte getDataType() {
        return 0;
    }

    @Override
    public String getNeIp() {
        return null;
    }

    @Override
    public String getPathId() {
        return null;
    }

    @Override
    public String getPathName() {
        return null;
    }

    @Override
    public String[] getPathIdAll()
    {
        return new String[0];
    }

    @Override
    public String getLink() {
        return mml.getParaValue(0, "link") != null ?String.valueOf(mml.getParaValue(0, "link")) : null;
    }

    @Override
    public byte getOriginPerceivedSeverity() {
        return 0;
    }

    @Override
    public FmPlmnEntity[] getAlarmPlmns() {
        return new FmPlmnEntity[0];
    }

    @Override
    public String[] getProduct() {
        return new String[0];
    }

    @Override
    public boolean isADMC() {
        return false;
    }

    @Override
    public String getThresholdInfo() {
        return null;
    }

    @Override
    public String getAckInfo() {
        return null;
    }

    @Override
    public String getEmsId() {
        return null;
    }

    @Override
    public byte getDebugState() {
        return 0;
    }

    @Override
    public String getSubName1() {
        return null;
    }

    @Override
    public String getOmmid() {
        return null;
    }

    @Override
    public String getClearInfo() {
        return null;
    }

    @Override
    public byte getVisibleReasonType() {
        return 0;
    }

    @Override
    public byte getClearedState() {
        return 0;
    }

    @Override
    public String getRootAlarmId() {
        return null;
    }
}
