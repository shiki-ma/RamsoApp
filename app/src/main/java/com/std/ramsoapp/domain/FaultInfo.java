package com.std.ramsoapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maik on 2016/3/29.
 */
public class FaultInfo extends ROResult implements Parcelable {
    private String faultId;
    private String faultCode;
    private String faultSource;
    private String faultCustId;
    private String faultCustName;
    private String faultTermId;
    private String faultTermName;
    private String assetId;
    private String assetCode;
    private String assetName;
    private String faultDesc;
    private String faultOthCondition;
    private String faultType;
    private String faultLevel;
    private String faultLevelName;
    private String faultHandlers;
    private String faultHandlersId;
    private String faultRemark;
    private String faultDate;
    private String faultRequiredDate;
    private String handlerMethod;
    private String handlerMethodCN;
    private String handlerArrivalTime;
    private String handlerPerson;
    private String handlerBegTime;
    private String handlerAnalyse;
    private String handlerTreatment;
    private String handlerAssist;
    private String handlerAssistCN;
    private String handlerAstCondition;
    private String handlerResult;
    private String handlerResultCN;
    private String handlerFinTime;
    private String handlerFeedback;
    private String handlerFeedTime;
    private String customSatisfaction;
    private String customSatisfactionCN;
    private String customOpinions;
    private String customSuggest;
    private String customException;
    private String customEpResult;
    private String faultStatus;
    private String faultResult;
    private String faultResultName;
    private String isTurn;
    private String confirmRmk;
    private String confirmDate;
    private String recordTime;
    private String termAddress;
    private String custConnector;
    private String custConnectorTel;
    private long num;

    public String getFaultId() {
        return faultId;
    }

    public void setFaultId(String faultId) {
        this.faultId = faultId;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultSource() {
        return faultSource;
    }

    public void setFaultSource(String faultSource) {
        this.faultSource = faultSource;
    }

    public String getFaultCustId() {
        return faultCustId;
    }

    public void setFaultCustId(String faultCustId) {
        this.faultCustId = faultCustId;
    }

    public String getFaultCustName() {
        return faultCustName;
    }

    public void setFaultCustName(String faultCustName) {
        this.faultCustName = faultCustName;
    }

    public String getFaultTermId() {
        return faultTermId;
    }

    public void setFaultTermId(String faultTermId) {
        this.faultTermId = faultTermId;
    }

    public String getFaultTermName() {
        return faultTermName;
    }

    public void setFaultTermName(String faultTermName) {
        this.faultTermName = faultTermName;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getFaultOthCondition() {
        return faultOthCondition;
    }

    public void setFaultOthCondition(String faultOthCondition) {
        this.faultOthCondition = faultOthCondition;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(String faultLevel) {
        this.faultLevel = faultLevel;
    }

    public String getFaultLevelName() {
        return faultLevelName;
    }

    public void setFaultLevelName(String faultLevelName) {
        this.faultLevelName = faultLevelName;
    }

    public String getFaultHandlers() {
        return faultHandlers;
    }

    public void setFaultHandlers(String faultHandlers) {
        this.faultHandlers = faultHandlers;
    }

    public String getFaultHandlersId() {
        return faultHandlersId;
    }

    public void setFaultHandlersId(String faultHandlersId) {
        this.faultHandlersId = faultHandlersId;
    }

    public String getFaultRemark() {
        return faultRemark;
    }

    public void setFaultRemark(String faultRemark) {
        this.faultRemark = faultRemark;
    }

    public String getFaultDate() {
        return faultDate;
    }

    public void setFaultDate(String faultDate) {
        this.faultDate = faultDate;
    }

    public String getFaultRequiredDate() {
        return faultRequiredDate;
    }

    public void setFaultRequiredDate(String faultRequiredDate) {
        this.faultRequiredDate = faultRequiredDate;
    }

    public String getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(String handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public String getHandlerMethodCN() {
        return handlerMethodCN;
    }

    public void setHandlerMethodCN(String handlerMethodCN) {
        this.handlerMethodCN = handlerMethodCN;
    }

    public String getHandlerArrivalTime() {
        return handlerArrivalTime;
    }

    public void setHandlerArrivalTime(String handlerArrivalTime) {
        this.handlerArrivalTime = handlerArrivalTime;
    }

    public String getHandlerPerson() {
        return handlerPerson;
    }

    public void setHandlerPerson(String handlerPerson) {
        this.handlerPerson = handlerPerson;
    }

    public String getHandlerBegTime() {
        return handlerBegTime;
    }

    public void setHandlerBegTime(String handlerBegTime) {
        this.handlerBegTime = handlerBegTime;
    }

    public String getHandlerAnalyse() {
        return handlerAnalyse;
    }

    public void setHandlerAnalyse(String handlerAnalyse) {
        this.handlerAnalyse = handlerAnalyse;
    }

    public String getHandlerTreatment() {
        return handlerTreatment;
    }

    public void setHandlerTreatment(String handlerTreatment) {
        this.handlerTreatment = handlerTreatment;
    }

    public String getHandlerAssist() {
        return handlerAssist;
    }

    public void setHandlerAssist(String handlerAssist) {
        this.handlerAssist = handlerAssist;
    }

    public String getHandlerAssistCN() {
        return handlerAssistCN;
    }

    public void setHandlerAssistCN(String handlerAssistCN) {
        this.handlerAssistCN = handlerAssistCN;
    }

    public String getHandlerAstCondition() {
        return handlerAstCondition;
    }

    public void setHandlerAstCondition(String handlerAstCondition) {
        this.handlerAstCondition = handlerAstCondition;
    }

    public String getHandlerResult() {
        return handlerResult;
    }

    public void setHandlerResult(String handlerResult) {
        this.handlerResult = handlerResult;
    }

    public String getHandlerResultCN() {
        return handlerResultCN;
    }

    public void setHandlerResultCN(String handlerResultCN) {
        this.handlerResultCN = handlerResultCN;
    }

    public String getHandlerFinTime() {
        return handlerFinTime;
    }

    public void setHandlerFinTime(String handlerFinTime) {
        this.handlerFinTime = handlerFinTime;
    }

    public String getHandlerFeedback() {
        return handlerFeedback;
    }

    public void setHandlerFeedback(String handlerFeedback) {
        this.handlerFeedback = handlerFeedback;
    }

    public String getHandlerFeedTime() {
        return handlerFeedTime;
    }

    public void setHandlerFeedTime(String handlerFeedTime) {
        this.handlerFeedTime = handlerFeedTime;
    }

    public String getCustomSatisfaction() {
        return customSatisfaction;
    }

    public void setCustomSatisfaction(String customSatisfaction) {
        this.customSatisfaction = customSatisfaction;
    }

    public String getCustomSatisfactionCN() {
        return customSatisfactionCN;
    }

    public void setCustomSatisfactionCN(String customSatisfactionCN) {
        this.customSatisfactionCN = customSatisfactionCN;
    }

    public String getCustomOpinions() {
        return customOpinions;
    }

    public void setCustomOpinions(String customOpinions) {
        this.customOpinions = customOpinions;
    }

    public String getCustomSuggest() {
        return customSuggest;
    }

    public void setCustomSuggest(String customSuggest) {
        this.customSuggest = customSuggest;
    }

    public String getCustomException() {
        return customException;
    }

    public void setCustomException(String customException) {
        this.customException = customException;
    }

    public String getCustomEpResult() {
        return customEpResult;
    }

    public void setCustomEpResult(String customEpResult) {
        this.customEpResult = customEpResult;
    }

    public String getFaultStatus() {
        return faultStatus;
    }

    public void setFaultStatus(String faultStatus) {
        this.faultStatus = faultStatus;
    }

    public String getFaultResult() {
        return faultResult;
    }

    public void setFaultResult(String faultResult) {
        this.faultResult = faultResult;
    }

    public String getIsTurn() {
        return isTurn;
    }

    public void setIsTurn(String isTurn) {
        this.isTurn = isTurn;
    }

    public String getConfirmRmk() {
        return confirmRmk;
    }

    public void setConfirmRmk(String confirmRmk) {
        this.confirmRmk = confirmRmk;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getFaultResultName() {
        return faultResultName;
    }

    public void setFaultResultName(String faultResultName) {
        this.faultResultName = faultResultName;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public FaultInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.faultId);
        dest.writeString(this.faultCode);
        dest.writeString(this.faultSource);
        dest.writeString(this.faultCustId);
        dest.writeString(this.faultCustName);
        dest.writeString(this.faultTermId);
        dest.writeString(this.faultTermName);
        dest.writeString(this.assetId);
        dest.writeString(this.assetCode);
        dest.writeString(this.assetName);
        dest.writeString(this.faultDesc);
        dest.writeString(this.faultOthCondition);
        dest.writeString(this.faultType);
        dest.writeString(this.faultLevel);
        dest.writeString(this.faultLevelName);
        dest.writeString(this.faultHandlers);
        dest.writeString(this.faultHandlersId);
        dest.writeString(this.faultRemark);
        dest.writeString(this.faultDate);
        dest.writeString(this.faultRequiredDate);
        dest.writeString(this.handlerMethod);
        dest.writeString(this.handlerMethodCN);
        dest.writeString(this.handlerArrivalTime);
        dest.writeString(this.handlerPerson);
        dest.writeString(this.handlerBegTime);
        dest.writeString(this.handlerAnalyse);
        dest.writeString(this.handlerTreatment);
        dest.writeString(this.handlerAssist);
        dest.writeString(this.handlerAssistCN);
        dest.writeString(this.handlerAstCondition);
        dest.writeString(this.handlerResult);
        dest.writeString(this.handlerResultCN);
        dest.writeString(this.handlerFinTime);
        dest.writeString(this.handlerFeedback);
        dest.writeString(this.handlerFeedTime);
        dest.writeString(this.customSatisfaction);
        dest.writeString(this.customSatisfactionCN);
        dest.writeString(this.customOpinions);
        dest.writeString(this.customSuggest);
        dest.writeString(this.customException);
        dest.writeString(this.customEpResult);
        dest.writeString(this.faultStatus);
        dest.writeString(this.faultResult);
        dest.writeString(this.faultResultName);
        dest.writeString(this.isTurn);
        dest.writeString(this.confirmRmk);
        dest.writeString(this.confirmDate);
        dest.writeString(this.recordTime);
        dest.writeString(this.termAddress);
        dest.writeString(this.custConnector);
        dest.writeString(this.custConnectorTel);
        dest.writeLong(this.num);
    }

    public String getTermAddress() {
        return termAddress;
    }

    public void setTermAddress(String termAddress) {
        this.termAddress = termAddress;
    }

    public String getCustConnector() {
        return custConnector;
    }

    public void setCustConnector(String custConnector) {
        this.custConnector = custConnector;
    }

    public String getCustConnectorTel() {
        return custConnectorTel;
    }

    public void setCustConnectorTel(String custConnectorTel) {
        this.custConnectorTel = custConnectorTel;
    }

    protected FaultInfo(Parcel in) {
        this.faultId = in.readString();
        this.faultCode = in.readString();
        this.faultSource = in.readString();
        this.faultCustId = in.readString();
        this.faultCustName = in.readString();
        this.faultTermId = in.readString();
        this.faultTermName = in.readString();
        this.assetId = in.readString();
        this.assetCode = in.readString();
        this.assetName = in.readString();
        this.faultDesc = in.readString();
        this.faultOthCondition = in.readString();
        this.faultType = in.readString();
        this.faultLevel = in.readString();
        this.faultLevelName = in.readString();
        this.faultHandlers = in.readString();
        this.faultHandlersId = in.readString();
        this.faultRemark = in.readString();
        this.faultDate = in.readString();
        this.faultRequiredDate = in.readString();
        this.handlerMethod = in.readString();
        this.handlerMethodCN = in.readString();
        this.handlerArrivalTime = in.readString();
        this.handlerPerson = in.readString();
        this.handlerBegTime = in.readString();
        this.handlerAnalyse = in.readString();
        this.handlerTreatment = in.readString();
        this.handlerAssist = in.readString();
        this.handlerAssistCN = in.readString();
        this.handlerAstCondition = in.readString();
        this.handlerResult = in.readString();
        this.handlerResultCN = in.readString();
        this.handlerFinTime = in.readString();
        this.handlerFeedback = in.readString();
        this.handlerFeedTime = in.readString();
        this.customSatisfaction = in.readString();
        this.customSatisfactionCN = in.readString();
        this.customOpinions = in.readString();
        this.customSuggest = in.readString();
        this.customException = in.readString();
        this.customEpResult = in.readString();
        this.faultStatus = in.readString();
        this.faultResult = in.readString();
        this.faultResultName = in.readString();
        this.isTurn = in.readString();
        this.confirmRmk = in.readString();
        this.confirmDate = in.readString();
        this.recordTime = in.readString();
        this.termAddress = in.readString();
        this.custConnector = in.readString();
        this.custConnectorTel = in.readString();
        this.num = in.readLong();
    }

    public static final Creator<FaultInfo> CREATOR = new Creator<FaultInfo>() {
        @Override
        public FaultInfo createFromParcel(Parcel source) {
            return new FaultInfo(source);
        }

        @Override
        public FaultInfo[] newArray(int size) {
            return new FaultInfo[size];
        }
    };
}
