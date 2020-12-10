package com.amplifyframework.datastore.generated.model;


import java.util.UUID;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Event type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Events")
public final class Event implements Model {



  public static final QueryField ID = field("id");
  public static final QueryField EVENT_TITLE = field("EventTitle");
  public static final QueryField DESCRIPTION = field("Description");
  public static final QueryField EVENT_DATE = field("EventDate");
  public static final QueryField EVENT_TIME = field("EventTime");
  public static final QueryField CATEGORY = field("Category");
  public static final QueryField POST_URL = field("PostURL");
  public static final QueryField EXTRA_INFO = field("ExtraInfo");
  public static final QueryField VENUE = field("Venue");
  public static final QueryField TEMPLATE = field("Template");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String EventTitle;
  private final @ModelField(targetType="String") String Description;
  private final @ModelField(targetType="String") String EventDate;
  private final @ModelField(targetType="String") String EventTime;
  private final @ModelField(targetType="String") String Category;
  private final @ModelField(targetType="String") String PostURL;
  private final @ModelField(targetType="String") String ExtraInfo;
  private final @ModelField(targetType="String") String Venue;
  private final @ModelField(targetType="String") String Template;



  public String getId() {
      return id;
  }
  
  public String getEventTitle() {
      return EventTitle;
  }
  
  public String getDescription() {
      return Description;
  }
  
  public String getEventDate() {
      return EventDate;
  }
  
  public String getEventTime() {
      return EventTime;
  }
  
  public String getCategory() {
      return Category;
  }
  
  public String getPostUrl() {
      return PostURL;
  }
  
  public String getExtraInfo() {
      return ExtraInfo;
  }
  
  public String getVenue() {
      return Venue;
  }
  
  public String getTemplate() {
      return Template;
  }
  
  private Event(String id, String EventTitle, String Description, String EventDate, String EventTime, String Category, String PostURL, String ExtraInfo, String Venue, String Template) {
    this.id = id;
    this.EventTitle = EventTitle;
    this.Description = Description;
    this.EventDate = EventDate;
    this.EventTime = EventTime;
    this.Category = Category;
    this.PostURL = PostURL;
    this.ExtraInfo = ExtraInfo;
    this.Venue = Venue;
    this.Template = Template;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Event event = (Event) obj;
      return ObjectsCompat.equals(getId(), event.getId()) &&
              ObjectsCompat.equals(getEventTitle(), event.getEventTitle()) &&
              ObjectsCompat.equals(getDescription(), event.getDescription()) &&
              ObjectsCompat.equals(getEventDate(), event.getEventDate()) &&
              ObjectsCompat.equals(getEventTime(), event.getEventTime()) &&
              ObjectsCompat.equals(getCategory(), event.getCategory()) &&
              ObjectsCompat.equals(getPostUrl(), event.getPostUrl()) &&
              ObjectsCompat.equals(getExtraInfo(), event.getExtraInfo()) &&
              ObjectsCompat.equals(getVenue(), event.getVenue()) &&
              ObjectsCompat.equals(getTemplate(), event.getTemplate());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getEventTitle())
      .append(getDescription())
      .append(getEventDate())
      .append(getEventTime())
      .append(getCategory())
      .append(getPostUrl())
      .append(getExtraInfo())
      .append(getVenue())
      .append(getTemplate())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Event {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("EventTitle=" + String.valueOf(getEventTitle()) + ", ")
      .append("Description=" + String.valueOf(getDescription()) + ", ")
      .append("EventDate=" + String.valueOf(getEventDate()) + ", ")
      .append("EventTime=" + String.valueOf(getEventTime()) + ", ")
      .append("Category=" + String.valueOf(getCategory()) + ", ")
      .append("PostURL=" + String.valueOf(getPostUrl()) + ", ")
      .append("ExtraInfo=" + String.valueOf(getExtraInfo()) + ", ")
      .append("Venue=" + String.valueOf(getVenue()) + ", ")
      .append("Template=" + String.valueOf(getTemplate()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Event justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Event(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      EventTitle,
      Description,
      EventDate,
      EventTime,
      Category,
      PostURL,
      ExtraInfo,
      Venue,
      Template);
  }
  public interface BuildStep {
    Event build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep eventTitle(String eventTitle);
    BuildStep description(String description);
    BuildStep eventDate(String eventDate);
    BuildStep eventTime(String eventTime);
    BuildStep category(String category);
    BuildStep postUrl(String postUrl);
    BuildStep extraInfo(String extraInfo);
    BuildStep venue(String venue);
    BuildStep template(String template);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String EventTitle;
    private String Description;
    private String EventDate;
    private String EventTime;
    private String Category;
    private String PostURL;
    private String ExtraInfo;
    private String Venue;
    private String Template;
    @Override
     public Event build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Event(
          id,
          EventTitle,
          Description,
          EventDate,
          EventTime,
          Category,
          PostURL,
          ExtraInfo,
          Venue,
          Template);
    }
    
    @Override
     public BuildStep eventTitle(String eventTitle) {
        this.EventTitle = eventTitle;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.Description = description;
        return this;
    }
    
    @Override
     public BuildStep eventDate(String eventDate) {
        this.EventDate = eventDate;
        return this;
    }
    
    @Override
     public BuildStep eventTime(String eventTime) {
        this.EventTime = eventTime;
        return this;
    }
    
    @Override
     public BuildStep category(String category) {
        this.Category = category;
        return this;
    }
    
    @Override
     public BuildStep postUrl(String postUrl) {
        this.PostURL = postUrl;
        return this;
    }
    
    @Override
     public BuildStep extraInfo(String extraInfo) {
        this.ExtraInfo = extraInfo;
        return this;
    }
    
    @Override
     public BuildStep venue(String venue) {
        this.Venue = venue;
        return this;
    }
    
    @Override
     public BuildStep template(String template) {
        this.Template = template;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String eventTitle, String description, String eventDate, String eventTime, String category, String postUrl, String extraInfo, String venue, String template) {
      super.id(id);
      super.eventTitle(eventTitle)
        .description(description)
        .eventDate(eventDate)
        .eventTime(eventTime)
        .category(category)
        .postUrl(postUrl)
        .extraInfo(extraInfo)
        .venue(venue)
        .template(template);
    }
    
    @Override
     public CopyOfBuilder eventTitle(String eventTitle) {
      return (CopyOfBuilder) super.eventTitle(eventTitle);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder eventDate(String eventDate) {
      return (CopyOfBuilder) super.eventDate(eventDate);
    }
    
    @Override
     public CopyOfBuilder eventTime(String eventTime) {
      return (CopyOfBuilder) super.eventTime(eventTime);
    }
    
    @Override
     public CopyOfBuilder category(String category) {
      return (CopyOfBuilder) super.category(category);
    }
    
    @Override
     public CopyOfBuilder postUrl(String postUrl) {
      return (CopyOfBuilder) super.postUrl(postUrl);
    }
    
    @Override
     public CopyOfBuilder extraInfo(String extraInfo) {
      return (CopyOfBuilder) super.extraInfo(extraInfo);
    }
    
    @Override
     public CopyOfBuilder venue(String venue) {
      return (CopyOfBuilder) super.venue(venue);
    }
    
    @Override
     public CopyOfBuilder template(String template) {
      return (CopyOfBuilder) super.template(template);
    }
  }
    public String getDayOfMonth() {
        return EventDate.substring(5, 7);
    }

    public String getMonthOfYear() {
        return EventDate.substring(0, 3);}
  
}
