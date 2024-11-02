package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChapterText(
        // TODO break down the text into overviews and a collection of oral history sections
        @JsonProperty(required = true, value = "editorialOverview") String editorialOverview,
        @JsonProperty(required = true, value = "text") String text
) {}
