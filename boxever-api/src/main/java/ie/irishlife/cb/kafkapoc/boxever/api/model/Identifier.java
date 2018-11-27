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
        "provider",
        "id",
        "expiryDate"
})
public class Identifier implements Serializable {

    @JsonProperty("provider")
    private String provider;
    @JsonProperty("id")
    private String id;
    @JsonProperty("expiryDate")
    private String expiryDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7920527865028021756L;

    @JsonProperty("provider")
    public String getProvider() {
        return provider;
    }

    @JsonProperty("provider")
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Identifier withProvider(String provider) {
        this.provider = provider;
        return this;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Identifier withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("expiryDate")
    public String getExpiryDate() {
        return expiryDate;
    }

    @JsonProperty("expiryDate")
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Identifier withExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public Identifier withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("provider", provider).append("id", id).append("expiryDate", expiryDate).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(additionalProperties).append(expiryDate).append(provider).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Identifier) == false) {
            return false;
        }
        Identifier rhs = ((Identifier) other);
        return new EqualsBuilder().append(id, rhs.id).append(additionalProperties, rhs.additionalProperties).append(expiryDate, rhs.expiryDate).append(provider, rhs.provider).isEquals();
    }

}
