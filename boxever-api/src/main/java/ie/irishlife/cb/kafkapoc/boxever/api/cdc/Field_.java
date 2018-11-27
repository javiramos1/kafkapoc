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
        "type",
        "optional",
        "field",
        "name",
        "version"
})
public class Field_ implements Serializable {

    @JsonProperty("type")
    private String type;
    @JsonProperty("optional")
    private Boolean optional;
    @JsonProperty("field")
    private String field;
    @JsonProperty("name")
    private String name;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -543661368956881288L;

    /**
     * No args constructor for use in serialization
     */
    public Field_() {
    }

    /**
     * @param field
     * @param name
     * @param optional
     * @param type
     * @param version
     */
    public Field_(String type, Boolean optional, String field, String name, Integer version) {
        super();
        this.type = type;
        this.optional = optional;
        this.field = field;
        this.name = name;
        this.version = version;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("optional")
    public Boolean getOptional() {
        return optional;
    }

    @JsonProperty("optional")
    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    @JsonProperty("field")
    public String getField() {
        return field;
    }

    @JsonProperty("field")
    public void setField(String field) {
        this.field = field;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
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
        return new ToStringBuilder(this).append("type", type).append("optional", optional).append("field", field).append("name", name).append("version", version).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(field).append(additionalProperties).append(name).append(optional).append(type).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Field_) == false) {
            return false;
        }
        Field_ rhs = ((Field_) other);
        return new EqualsBuilder().append(field, rhs.field).append(additionalProperties, rhs.additionalProperties).append(name, rhs.name).append(optional, rhs.optional).append(type, rhs.type).append(version, rhs.version).isEquals();
    }

}
