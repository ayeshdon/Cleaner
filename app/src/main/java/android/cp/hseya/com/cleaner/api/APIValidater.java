package android.cp.hseya.com.cleaner.api;


public class APIValidater {


    private String errorCode;
    private String errorDescription;
    private boolean isSuccess;

    /**
     * get error code from API responce
     *
     * @return-String
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * set error code
     *
     * @param errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * get error code description
     *
     * @return
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * set error description
     *
     * @param errorDescription
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * get success value
     *
     * @return-boolean
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * set success value
     *
     * @param isSuccess
     */
    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }


}
