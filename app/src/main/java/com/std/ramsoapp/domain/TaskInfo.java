package com.std.ramsoapp.domain;

/**
 * Created by Maik on 2016/3/9.
 */
public class TaskInfo extends ROResult {
    private String taskId;
    private String assetId;
    private String asmhId;
    /** 资产编号 */
    private String assetCode;
    private String assetName;
    /** 资产外部编号 */
    private String assetOutCode;
    /** 资产类型 */
    private String assetTypeName;
    /*** 品牌 */
    private String assetBrand;
    /** 型号 */
    private String assetModel;
    /** 容量 */
    private String assetCapacity;
    /** 在用数字中继 */
    private String assetDigitalRelay;
    /** 在用模拟中继 */
    private String assetSimulationRelay;
    /** 引示号 */
    private String assetCited;
    /** 中继方式 */
    private String assetWay;
    /** 中继方向*/
    private String assetDirection;
    /** 上级资产 */
    private String assetForeignName;
    /** 开机密码 */
    private String assetStartPasswd;
    /** 操作密码 */
    private String assetOperatePasswd;
    /**  验收日期 */
    private String assetAcceptDate;
    /** 启用日期 */
    private String assetUsedDate;
    /** 维保到期日期 */
    private String assetMaintenDate;
    /** 供应商 */
    private String assetSupplyName;
    /** 工程编号 */
    private String assetProjCode;
    /** 所属单位 */
    private String assetBelongName;
    /** 资产所属单位类型；电信：T，联通：U，移动：M，其他：O */
    private String assetBelongType;
    /** 建设单位 */
    private String assetBuildName;
    /** 用户端点 */
    private String termName;
    /** 客户 */
    private String custName;
    /** 审核结果： 1：通过；2：不通过 */
    private String assetResult;
    /** 审核时间 */
    private String assetConfirmDate;
    /** 1:已登记,2:待入库,3:已入库,4:变更中,5:转移中,6:报废中,7:已报废 */
    private String assetStatus;
    /** 1:录入;2:待审核;3:已审核 */
    private String confirmStatus;
    private String taskDate;

    private String termAddress;
    private String custConnector;
    private String custConnectorTel;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAsmhId() {
        return asmhId;
    }

    public void setAsmhId(String asmhId) {
        this.asmhId = asmhId;
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

    public String getAssetOutCode() {
        return assetOutCode;
    }

    public void setAssetOutCode(String assetOutCode) {
        this.assetOutCode = assetOutCode;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getAssetBrand() {
        return assetBrand;
    }

    public void setAssetBrand(String assetBrand) {
        this.assetBrand = assetBrand;
    }

    public String getAssetModel() {
        return assetModel;
    }

    public void setAssetModel(String assetModel) {
        this.assetModel = assetModel;
    }

    public String getAssetCapacity() {
        return assetCapacity;
    }

    public void setAssetCapacity(String assetCapacity) {
        this.assetCapacity = assetCapacity;
    }

    public String getAssetDigitalRelay() {
        return assetDigitalRelay;
    }

    public void setAssetDigitalRelay(String assetDigitalRelay) {
        this.assetDigitalRelay = assetDigitalRelay;
    }

    public String getAssetSimulationRelay() {
        return assetSimulationRelay;
    }

    public void setAssetSimulationRelay(String assetSimulationRelay) {
        this.assetSimulationRelay = assetSimulationRelay;
    }

    public String getAssetCited() {
        return assetCited;
    }

    public void setAssetCited(String assetCited) {
        this.assetCited = assetCited;
    }

    public String getAssetWay() {
        return assetWay;
    }

    public void setAssetWay(String assetWay) {
        this.assetWay = assetWay;
    }

    public String getAssetDirection() {
        return assetDirection;
    }

    public void setAssetDirection(String assetDirection) {
        this.assetDirection = assetDirection;
    }

    public String getAssetForeignName() {
        return assetForeignName;
    }

    public void setAssetForeignName(String assetForeignName) {
        this.assetForeignName = assetForeignName;
    }

    public String getAssetStartPasswd() {
        return assetStartPasswd;
    }

    public void setAssetStartPasswd(String assetStartPasswd) {
        this.assetStartPasswd = assetStartPasswd;
    }

    public String getAssetOperatePasswd() {
        return assetOperatePasswd;
    }

    public void setAssetOperatePasswd(String assetOperatePasswd) {
        this.assetOperatePasswd = assetOperatePasswd;
    }

    public String getAssetAcceptDate() {
        return assetAcceptDate;
    }

    public void setAssetAcceptDate(String assetAcceptDate) {
        this.assetAcceptDate = assetAcceptDate;
    }

    public String getAssetUsedDate() {
        return assetUsedDate;
    }

    public void setAssetUsedDate(String assetUsedDate) {
        this.assetUsedDate = assetUsedDate;
    }

    public String getAssetMaintenDate() {
        return assetMaintenDate;
    }

    public void setAssetMaintenDate(String assetMaintenDate) {
        this.assetMaintenDate = assetMaintenDate;
    }

    public String getAssetSupplyName() {
        return assetSupplyName;
    }

    public void setAssetSupplyName(String assetSupplyName) {
        this.assetSupplyName = assetSupplyName;
    }

    public String getAssetProjCode() {
        return assetProjCode;
    }

    public void setAssetProjCode(String assetProjCode) {
        this.assetProjCode = assetProjCode;
    }

    public String getAssetBelongName() {
        return assetBelongName;
    }

    public void setAssetBelongName(String assetBelongName) {
        this.assetBelongName = assetBelongName;
    }

    public String getAssetBelongType() {
        return assetBelongType;
    }

    public void setAssetBelongType(String assetBelongType) {
        this.assetBelongType = assetBelongType;
    }

    public String getAssetBuildName() {
        return assetBuildName;
    }

    public void setAssetBuildName(String assetBuildName) {
        this.assetBuildName = assetBuildName;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAssetResult() {
        return assetResult;
    }

    public void setAssetResult(String assetResult) {
        this.assetResult = assetResult;
    }

    public String getAssetConfirmDate() {
        return assetConfirmDate;
    }

    public void setAssetConfirmDate(String assetConfirmDate) {
        this.assetConfirmDate = assetConfirmDate;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(String confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
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
}
