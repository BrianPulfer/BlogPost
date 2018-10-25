package ch.supsi.webapp.web;

public class SuccessJSON {
    public SuccessJSON(boolean success) {
        this.success = success;
    }

    private boolean success;

    public boolean getSucces(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }
}
