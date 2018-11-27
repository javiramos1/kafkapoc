package ie.irishlife.cb.kafkapoc.boxever.api.cdc;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "CLIENTID",
        "FORENAME",
        "SURNAME",
        "ANNUALSALARY",
        "RETIREMENTDATE",
        "DATEOFBIRTH",
        "ADDRESS",
        "PHONE_NUMBER",
        "MARITAL_STATUS",
        "EMAIL"
})
public class Data implements Serializable {

    @JsonProperty("CLIENTID")
    private String cLIENTID;
    @JsonProperty("FORENAME")
    private String fORENAME;
    @JsonProperty("SURNAME")
    private String sURNAME;
    @JsonProperty("ANNUALSALARY")
    private Integer aNNUALSALARY;
    @JsonProperty("RETIREMENTDATE")
    private Date rETIREMENTDATE;
    @JsonProperty("DATEOFBIRTH")
    private Date dATEOFBIRTH;
    @JsonProperty("ADDRESS")
    private String aDDRESS;
    @JsonProperty("PHONE_NUMBER")
    private String pHONENUMBER;
    @JsonProperty("MARITAL_STATUS")
    private String mARITALSTATUS;
    @JsonProperty("EMAIL")
    private String eMAIL;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -3044724348476994323L;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
    }

    /**
     * @param aDDRESS
     * @param sURNAME
     * @param pHONENUMBER
     * @param aNNUALSALARY
     * @param fORENAME
     * @param dATEOFBIRTH
     * @param mARITALSTATUS
     * @param cLIENTID
     * @param eMAIL
     * @param rETIREMENTDATE
     */
    public Data(String cLIENTID, String fORENAME, String sURNAME, Integer aNNUALSALARY, Date rETIREMENTDATE, Date dATEOFBIRTH, String aDDRESS, String pHONENUMBER, String mARITALSTATUS, String eMAIL) {
        super();
        this.cLIENTID = cLIENTID;
        this.fORENAME = fORENAME;
        this.sURNAME = sURNAME;
        this.aNNUALSALARY = aNNUALSALARY;
        this.rETIREMENTDATE = rETIREMENTDATE;
        this.dATEOFBIRTH = dATEOFBIRTH;
        this.aDDRESS = aDDRESS;
        this.pHONENUMBER = pHONENUMBER;
        this.mARITALSTATUS = mARITALSTATUS;
        this.eMAIL = eMAIL;
    }

    @JsonProperty("CLIENTID")
    public String getCLIENTID() {
        return cLIENTID;
    }

    @JsonProperty("CLIENTID")
    public void setCLIENTID(String cLIENTID) {
        this.cLIENTID = cLIENTID;
    }

    @JsonProperty("FORENAME")
    public String getFORENAME() {
        return fORENAME;
    }

    @JsonProperty("FORENAME")
    public void setFORENAME(String fORENAME) {
        this.fORENAME = fORENAME;
    }

    @JsonProperty("SURNAME")
    public String getSURNAME() {
        return sURNAME;
    }

    @JsonProperty("SURNAME")
    public void setSURNAME(String sURNAME) {
        this.sURNAME = sURNAME;
    }

    @JsonProperty("ANNUALSALARY")
    public Integer getANNUALSALARY() {
        return aNNUALSALARY;
    }

    @JsonProperty("ANNUALSALARY")
    public void setANNUALSALARY(Integer aNNUALSALARY) {
        this.aNNUALSALARY = aNNUALSALARY;
    }

    @JsonProperty("RETIREMENTDATE")
    public Date getRETIREMENTDATE() {
        return rETIREMENTDATE;
    }

    @JsonProperty("RETIREMENTDATE")
    public void setRETIREMENTDATE(Date rETIREMENTDATE) {
        this.rETIREMENTDATE = rETIREMENTDATE;
    }

    @JsonProperty("DATEOFBIRTH")
    public Date getDATEOFBIRTH() {
        return dATEOFBIRTH;
    }

    @JsonProperty("DATEOFBIRTH")
    public void setDATEOFBIRTH(Date dATEOFBIRTH) {
        this.dATEOFBIRTH = dATEOFBIRTH;
    }

    @JsonProperty("ADDRESS")
    public String getADDRESS() {
        return aDDRESS;
    }

    @JsonProperty("ADDRESS")
    public void setADDRESS(String aDDRESS) {
        this.aDDRESS = aDDRESS;
    }

    @JsonProperty("PHONE_NUMBER")
    public String getPHONENUMBER() {
        return pHONENUMBER;
    }

    @JsonProperty("PHONE_NUMBER")
    public void setPHONENUMBER(String pHONENUMBER) {
        this.pHONENUMBER = pHONENUMBER;
    }

    @JsonProperty("MARITAL_STATUS")
    public String getMARITALSTATUS() {
        return mARITALSTATUS;
    }

    @JsonProperty("MARITAL_STATUS")
    public void setMARITALSTATUS(String mARITALSTATUS) {
        this.mARITALSTATUS = mARITALSTATUS;
    }

    @JsonProperty("EMAIL")
    public String getEMAIL() {
        return eMAIL;
    }

    @JsonProperty("EMAIL")
    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
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
        return new ToStringBuilder(this).append("cLIENTID", cLIENTID).append("fORENAME", fORENAME).append("sURNAME", sURNAME).append("aNNUALSALARY", aNNUALSALARY).append("rETIREMENTDATE", rETIREMENTDATE).append("dATEOFBIRTH", dATEOFBIRTH).append("aDDRESS", aDDRESS).append("pHONENUMBER", pHONENUMBER).append("mARITALSTATUS", mARITALSTATUS).append("eMAIL", eMAIL).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(aDDRESS).append(sURNAME).append(pHONENUMBER).append(aNNUALSALARY).append(additionalProperties).append(fORENAME).append(dATEOFBIRTH).append(mARITALSTATUS).append(cLIENTID).append(eMAIL).append(rETIREMENTDATE).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Data) == false) {
            return false;
        }
        Data rhs = ((Data) other);
        return new EqualsBuilder().append(aDDRESS, rhs.aDDRESS).append(sURNAME, rhs.sURNAME).append(pHONENUMBER, rhs.pHONENUMBER).append(aNNUALSALARY, rhs.aNNUALSALARY).append(additionalProperties, rhs.additionalProperties).append(fORENAME, rhs.fORENAME).append(dATEOFBIRTH, rhs.dATEOFBIRTH).append(mARITALSTATUS, rhs.mARITALSTATUS).append(cLIENTID, rhs.cLIENTID).append(eMAIL, rhs.eMAIL).append(rETIREMENTDATE, rhs.rETIREMENTDATE).isEquals();
    }

}
