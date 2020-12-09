package com.study.design_pattern.state.order;

/**
 * 需要放在二方包发布
 */
public class ProcessResult {

    private boolean success;

    private String failedReason;

    private ProcessResult(boolean success, String failedReason) {
        this.success = success;
        this.failedReason = failedReason;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public static ProcessResult ofBizCheckFailedResult(String failedReason) {
        return new ProcessResult(false, failedReason);
    }

    public static ProcessResult ofBizCheckSuccessResult() {
        return new ProcessResult(true, null);
    }
}
