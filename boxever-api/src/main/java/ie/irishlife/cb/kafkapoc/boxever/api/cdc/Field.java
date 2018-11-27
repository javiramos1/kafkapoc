package ie.irishlife.cb.kafkapoc.boxever.api.cdc;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type",
        "optional",
        "field",
        "name",
        "version",
        "fields"
})
public class Field implements Serializable {

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
    @JsonProperty("fields")
    private List<ie.irishlife.cb.kafkapoc.boxever.api.cdc.Field_> fields = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -5899631613809634878L;

    /**
     * No args constructor for use in serialization
     */
    public Field() {
    }

    /**
     * @param field
     * @param name
     * @param optional
     * @param type
     * @param fields
     * @param version
     */
    public Field(String type, Boolean optional, String field, String name, Integer version, List<ie.irishlife.cb.kafkapoc.boxever.api.cdc.Field_> fields) {
        super();
        this.type = type;
        this.optional = optional;
        this.field = field;
        this.name = name;
        this.version = version;
        this.fields = fields;
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

    @JsonProperty("fields")
    public List<ie.irishlife.cb.kafkapoc.boxever.api.cdc.Field_> getFields() {
        return fields;
    }

    @JsonProperty("fields")
    public void setFields(List<ie.irishlife.cb.kafkapoc.boxever.api.cdc.Field_> fields) {
        this.fields = fields;
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
        return new ToStringBuilder(this).append("type", type).append("optional", optional).append("field", field).append("name", name).append("version", version).append("fields", fields).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(field).append(additionalProperties).append(name).append(optional).append(type).append(fields).append(version).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Field) == false) {
            return false;
        }
        Field rhs = ((Field) other);
        return new EqualsBuilder().append(field, rhs.field).append(additionalProperties, rhs.additionalProperties).append(name, rhs.name).append(optional, rhs.optional).append(type, rhs.type).append(fields, rhs.fields).append(version, rhs.version).isEquals();
    }

}
