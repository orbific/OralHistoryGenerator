package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChapterData(
        @JsonProperty(required = true, value = "editorialOverview") String editorialOverview,
        @JsonProperty(required = true, value = "text") String text
) {}
