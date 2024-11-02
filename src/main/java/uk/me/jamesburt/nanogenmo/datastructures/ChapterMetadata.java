package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChapterMetadata(
        @JsonProperty(required = true, value = "chapterTitle") String chapterTitle ,
        @JsonProperty(required = true, value = "description") String description
) {}
