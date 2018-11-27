package ie.irishlife.cb.kafkapoc.boxever.api.cdc;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "SCN",
        "SEG_OWNER",
        "TABLE_NAME",
        "TIMESTAMP",
        "SQL_REDO",
        "OPERATION",
        "data",
        "before"
})
public class Payload implements Serializable {

    @JsonProperty("SCN")
    private Integer sCN;
    @JsonProperty("SEG_OWNER")
    private String sEGOWNER;
    @JsonProperty("TABLE_NAME")
    private String tABLENAME;
    @JsonProperty("TIMESTAMP")
    private Long tIMESTAMP;
    @JsonProperty("SQL_REDO")
    private String sQLREDO;
    @JsonProperty("OPERATION")
    private String oPERATION;
    @JsonProperty("data")
    private Data data;
    @JsonProperty("before")
    private Before before;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8764057435608659915L;

    /**
     * No args constructor for use in serialization
     */
    public Payload() {
    }

    /**
     * @param oPERATION
     * @param tABLENAME
     * @param sCN
     * @param before
     * @param data
     * @param sEGOWNER
     * @param sQLREDO
     * @param tIMESTAMP
     */
    public Payload(Integer sCN, String sEGOWNER, String tABLENAME, Long tIMESTAMP, String sQLREDO, String oPERATION, Data data, Before before) {
        super();
        this.sCN = sCN;
        this.sEGOWNER = sEGOWNER;
        this.tABLENAME = tABLENAME;
        this.tIMESTAMP = tIMESTAMP;
        this.sQLREDO = sQLREDO;
        this.oPERATION = oPERATION;
        this.data = data;
        this.before = before;
    }

    @JsonProperty("SCN")
    public Integer getSCN() {
        return sCN;
    }

    @JsonProperty("SCN")
    public void setSCN(Integer sCN) {
        this.sCN = sCN;
    }

    @JsonProperty("SEG_OWNER")
    public String getSEGOWNER() {
        return sEGOWNER;
    }

    @JsonProperty("SEG_OWNER")
    public void setSEGOWNER(String sEGOWNER) {
        this.sEGOWNER = sEGOWNER;
    }

    @JsonProperty("TABLE_NAME")
    public String getTABLENAME() {
        return tABLENAME;
    }

    @JsonProperty("TABLE_NAME")
    public void setTABLENAME(String tABLENAME) {
        this.tABLENAME = tABLENAME;
    }

    @JsonProperty("TIMESTAMP")
    public Long getTIMESTAMP() {
        return tIMESTAMP;
    }

    @JsonProperty("TIMESTAMP")
    public void setTIMESTAMP(Long tIMESTAMP) {
        this.tIMESTAMP = tIMESTAMP;
    }

    @JsonProperty("SQL_REDO")
    public String getSQLREDO() {
        return sQLREDO;
    }

    @JsonProperty("SQL_REDO")
    public void setSQLREDO(String sQLREDO) {
        this.sQLREDO = sQLREDO;
    }

    @JsonProperty("OPERATION")
    public String getOPERATION() {
        return oPERATION;
    }

    @JsonProperty("OPERATION")
    public void setOPERATION(String oPERATION) {
        this.oPERATION = oPERATION;
    }

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }

    @JsonProperty("before")
    public Before getBefore() {
        return before;
    }

    @JsonProperty("before")
    public void setBefore(Before before) {
        this.before = before;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sCN", sCN).append("sEGOWNER", sEGOWNER).append("tABLENAME", tABLENAME).append("tIMESTAMP", tIMESTAMP).append("sQLREDO", sQLREDO).append("oPERATION", oPERATION).append("data", data).append("before", before).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(oPERATION).append(tABLENAME).append(sCN).append(additionalProperties).append(before).append(data).append(sEGOWNER).append(sQLREDO).append(tIMESTAMP).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Payload) == false) {
            return false;
        }
        Payload rhs = ((Payload) other);
        return new EqualsBuilder().append(oPERATION, rhs.oPERATION).append(tABLENAME, rhs.tABLENAME).append(sCN, rhs.sCN).append(additionalProperties, rhs.additionalProperties).append(before, rhs.before).append(data, rhs.data).append(sEGOWNER, rhs.sEGOWNER).append(sQLREDO, rhs.sQLREDO).append(tIMESTAMP, rhs.tIMESTAMP).isEquals();
    }

}
