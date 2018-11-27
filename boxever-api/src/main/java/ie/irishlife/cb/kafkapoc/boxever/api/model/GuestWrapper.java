package ie.irishlife.cb.kafkapoc.boxever.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

public class GuestWrapper implements Serializable {

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("timeStamp")
    private Date timeStamp;

    @JsonProperty("guest")
    private Guest guest;

    @JsonProperty("href")
    private String href;

    public GuestWrapper() {

    }

    public GuestWrapper(String operation, Date timeStamp, Guest guest, String href) {
        this.operation = operation;
        this.timeStamp = timeStamp;
        this.guest = guest;
        this.href = href;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "GuestWrapper{" +
                "operation='" + operation + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", guest=" + guest +
                '}';
    }
}
