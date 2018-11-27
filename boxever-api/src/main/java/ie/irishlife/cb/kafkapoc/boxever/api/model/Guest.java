package ie.irishlife.cb.kafkapoc.boxever.api.model;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({
        "ref",
        "createdAt",
        "modifiedAt",
        "firstSeen",
        "lastSeen",
        "guestType",
        "title",
        "firstName",
        "lastName",
        "gender",
        "dateOfBirth",
        "emails",
        "phoneNumbers",
        "nationality",
        "passportNumber",
        "passportExpiry",
        "street",
        "city",
        "country",
        "postCode",
        "state",
        "subscriptions",
        "identifiers"
})
public class Guest implements Serializable {

    @JsonProperty("ref")
    private String ref;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("modifiedAt")
    private String modifiedAt;
    @JsonProperty("firstSeen")
    private String firstSeen;
    @JsonProperty("lastSeen")
    private String lastSeen;
    @JsonProperty("guestType")
    private String guestType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("dateOfBirth")
    private Date dateOfBirth;
    @JsonProperty("emails")
    private List<String> emails = null;
    @JsonProperty("phoneNumbers")
    private List<String> phoneNumbers = null;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("passportNumber")
    private String passportNumber;
    @JsonProperty("passportExpiry")
    private String passportExpiry;
    @JsonProperty("street")
    private List<String> street = null;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("postCode")
    private String postCode;
    @JsonProperty("state")
    private String state;
    @JsonProperty("subscriptions")
    private List<ie.irishlife.cb.kafkapoc.boxever.api.model.Subscription> subscriptions = null;
    @JsonProperty("identifiers")
    private List<ie.irishlife.cb.kafkapoc.boxever.api.model.Identifier> identifiers = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 3954895252378680820L;

    @JsonProperty("ref")
    public String getRef() {
        return ref;
    }

    @JsonProperty("ref")
    public void setRef(String ref) {
        this.ref = ref;
    }

    public Guest withRef(String ref) {
        this.ref = ref;
        return this;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Guest withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @JsonProperty("modifiedAt")
    public String getModifiedAt() {
        return modifiedAt;
    }

    @JsonProperty("modifiedAt")
    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Guest withModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    @JsonProperty("firstSeen")
    public String getFirstSeen() {
        return firstSeen;
    }

    @JsonProperty("firstSeen")
    public void setFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
    }

    public Guest withFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
        return this;
    }

    @JsonProperty("lastSeen")
    public String getLastSeen() {
        return lastSeen;
    }

    @JsonProperty("lastSeen")
    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Guest withLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
        return this;
    }

    @JsonProperty("guestType")
    public String getGuestType() {
        return guestType;
    }

    @JsonProperty("guestType")
    public void setGuestType(String guestType) {
        this.guestType = guestType;
    }

    public Guest withGuestType(String guestType) {
        this.guestType = guestType;
        return this;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Guest withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Guest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Guest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Guest withGender(String gender) {
        this.gender = gender;
        return this;
    }

    @JsonProperty("dateOfBirth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Guest withDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    @JsonProperty("emails")
    public List<String> getEmails() {
        return emails;
    }

    @JsonProperty("emails")
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public Guest withEmails(List<String> emails) {
        this.emails = emails;
        return this;
    }

    @JsonProperty("phoneNumbers")
    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    @JsonProperty("phoneNumbers")
    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Guest withPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

    @JsonProperty("nationality")
    public String getNationality() {
        return nationality;
    }

    @JsonProperty("nationality")
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Guest withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    @JsonProperty("passportNumber")
    public String getPassportNumber() {
        return passportNumber;
    }

    @JsonProperty("passportNumber")
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Guest withPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        return this;
    }

    @JsonProperty("passportExpiry")
    public String getPassportExpiry() {
        return passportExpiry;
    }

    @JsonProperty("passportExpiry")
    public void setPassportExpiry(String passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    public Guest withPassportExpiry(String passportExpiry) {
        this.passportExpiry = passportExpiry;
        return this;
    }

    @JsonProperty("street")
    public List<String> getStreet() {
        return street;
    }

    @JsonProperty("street")
    public void setStreet(List<String> street) {
        this.street = street;
    }

    public Guest withStreet(List<String> street) {
        this.street = street;
        return this;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public Guest withCity(String city) {
        this.city = city;
        return this;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public Guest withCountry(String country) {
        this.country = country;
        return this;
    }

    @JsonProperty("postCode")
    public String getPostCode() {
        return postCode;
    }

    @JsonProperty("postCode")
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Guest withPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public Guest withState(String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("subscriptions")
    public List<ie.irishlife.cb.kafkapoc.boxever.api.model.Subscription> getSubscriptions() {
        return subscriptions;
    }

    @JsonProperty("subscriptions")
    public void setSubscriptions(List<ie.irishlife.cb.kafkapoc.boxever.api.model.Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Guest withSubscriptions(List<ie.irishlife.cb.kafkapoc.boxever.api.model.Subscription> subscriptions) {
        this.subscriptions = subscriptions;
        return this;
    }

    @JsonProperty("identifiers")
    public List<ie.irishlife.cb.kafkapoc.boxever.api.model.Identifier> getIdentifiers() {
        return identifiers;
    }

    @JsonProperty("identifiers")
    public void setIdentifiers(List<ie.irishlife.cb.kafkapoc.boxever.api.model.Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public Guest withIdentifiers(List<ie.irishlife.cb.kafkapoc.boxever.api.model.Identifier> identifiers) {
        this.identifiers = identifiers;
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

    public Guest withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("ref", ref).append("createdAt", createdAt).append("modifiedAt", modifiedAt).append("firstSeen", firstSeen).append("lastSeen", lastSeen).append("guestType", guestType).append("title", title).append("firstName", firstName).append("lastName", lastName).append("gender", gender).append("dateOfBirth", dateOfBirth).append("emails", emails).append("phoneNumbers", phoneNumbers).append("nationality", nationality).append("passportNumber", passportNumber).append("passportExpiry", passportExpiry).append("street", street).append("city", city).append("country", country).append("postCode", postCode).append("state", state).append("subscriptions", subscriptions).append("identifiers", identifiers).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dateOfBirth).append(lastSeen).append(modifiedAt).append(identifiers).append(street).append(state).append(passportNumber).append(city).append(title).append(createdAt).append(gender).append(firstName).append(guestType).append(firstSeen).append(lastName).append(subscriptions).append(passportExpiry).append(postCode).append(phoneNumbers).append(emails).append(country).append(ref).append(nationality).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Guest) == false) {
            return false;
        }
        Guest rhs = ((Guest) other);
        return new EqualsBuilder().append(dateOfBirth, rhs.dateOfBirth).append(lastSeen, rhs.lastSeen).append(modifiedAt, rhs.modifiedAt).append(identifiers, rhs.identifiers).append(street, rhs.street).append(state, rhs.state).append(passportNumber, rhs.passportNumber).append(city, rhs.city).append(title, rhs.title).append(createdAt, rhs.createdAt).append(gender, rhs.gender).append(firstName, rhs.firstName).append(guestType, rhs.guestType).append(firstSeen, rhs.firstSeen).append(lastName, rhs.lastName).append(subscriptions, rhs.subscriptions).append(passportExpiry, rhs.passportExpiry).append(postCode, rhs.postCode).append(phoneNumbers, rhs.phoneNumbers).append(emails, rhs.emails).append(country, rhs.country).append(ref, rhs.ref).append(nationality, rhs.nationality).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
