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
        "schema",
        "payload"
})
public class CDCData implements Serializable {

    @JsonProperty("schema")
    private Schema schema;
    @JsonProperty("payload")
    private ie.irishlife.cb.kafkapoc.boxever.api.cdc.Payload payload;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -4173311707519843545L;

    /**
     * No args constructor for use in serialization
     */
    public CDCData() {
    }

    /**
     * @param schema
     * @param payload
     */
    public CDCData(Schema schema, ie.irishlife.cb.kafkapoc.boxever.api.cdc.Payload payload) {
        super();
        this.schema = schema;
        this.payload = payload;
    }

    @JsonProperty("schema")
    public Schema getSchema() {
        return schema;
    }

    @JsonProperty("schema")
    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @JsonProperty("payload")
    public ie.irishlife.cb.kafkapoc.boxever.api.cdc.Payload getPayload() {
        return payload;
    }

    @JsonProperty("payload")
    public void setPayload(ie.irishlife.cb.kafkapoc.boxever.api.cdc.Payload payload) {
        this.payload = payload;
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
        return new ToStringBuilder(this).append("schema", schema).append("payload", payload).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(schema).append(additionalProperties).append(payload).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CDCData) == false) {
            return false;
        }
        CDCData rhs = ((CDCData) other);
        return new EqualsBuilder().append(schema, rhs.schema).append(additionalProperties, rhs.additionalProperties).append(payload, rhs.payload).isEquals();
    }

}
