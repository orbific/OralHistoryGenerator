package uk.me.jamesburt.nanogenmo.datastructures;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CastMetadata(
        @JsonProperty(required = true, value = "cast") CharacterMetadata[] characterMetadata
) {
    public static String convertCastToString(CastMetadata cast) {
        StringBuilder sb = new StringBuilder();
        for(CharacterMetadata character: cast.characterMetadata()) {
            sb.append(character.name());
            sb.append(" (").append(character.profession()).append(")\n");
            sb.append("Description: ").append(character.description());
            sb.append("History: ").append(character.history());
            sb.append("\n");
        }
        return sb.toString();
    }

}




