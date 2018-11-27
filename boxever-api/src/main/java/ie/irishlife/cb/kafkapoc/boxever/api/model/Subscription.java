package ie.irishlife.cb.kafkapoc.boxever.api.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "channel",
        "pointOfSale",
        "status",
        "effectiveDate"
})
public class Subscription implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("pointOfSale")
    private String pointOfSale;
    @JsonProperty("status")
    private String status;
    @JsonProperty("effectiveDate")
    private String effectiveDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -8872665186619160200L;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Subscription withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("channel")
    public String getChannel() {
        return channel;
    }

    @JsonProperty("channel")
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Subscription withChannel(String channel) {
        this.channel = channel;
        return this;
    }

    @JsonProperty("pointOfSale")
    public String getPointOfSale() {
        return pointOfSale;
    }

    @JsonProperty("pointOfSale")
    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public Subscription withPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public Subscription withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("effectiveDate")
    public String getEffectiveDate() {
        return effectiveDate;
    }

    @JsonProperty("effectiveDate")
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Subscription withEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Subscription withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("channel", channel).append("pointOfSale", pointOfSale).append("status", status).append("effectiveDate", effectiveDate).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(status).append(additionalProperties).append(pointOfSale).append(name).append(effectiveDate).append(channel).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Subscription) == false) {
            return false;
        }
        Subscription rhs = ((Subscription) other);
        return new EqualsBuilder().append(status, rhs.status).append(additionalProperties, rhs.additionalProperties).append(pointOfSale, rhs.pointOfSale).append(name, rhs.name).append(effectiveDate, rhs.effectiveDate).append(channel, rhs.channel).isEquals();
    }

}
