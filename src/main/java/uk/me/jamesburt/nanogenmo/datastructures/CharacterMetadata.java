package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterMetadata(
        @JsonProperty(required = true, value = "name") String name ,
        @JsonProperty(required = true, value = "profession") String profession ,
        @JsonProperty(required = true, value = "description") String description,
        @JsonProperty(required = true, value = "history") String history
) {}
