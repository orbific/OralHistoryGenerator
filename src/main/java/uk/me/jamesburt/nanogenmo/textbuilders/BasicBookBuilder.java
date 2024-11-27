package uk.me.jamesburt.nanogenmo.textbuilders;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uk.me.jamesburt.nanogenmo.LlmClient;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.outputgeneration.PdfOutputGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class BasicBookBuilder {

    private static final int NUMBER_OF_CHAPTERS = 20;
    private static final int NUMBER_OF_WORDS = 2500;
    @Autowired
    LlmClient llmClient;

    @Autowired
    PdfOutputGenerator pdfOutputGenerator;

    @Value("classpath:/prompts/bandhistory/structure.st")
    private Resource structurePromptText;

    @Value("classpath:/prompts/bandhistory/chapter-breakdown.st")
    private Resource chapterStructurePromptText;

    @Value("classpath:/prompts/bandhistory/oral-history.st")
    private Resource oralHistoryPromptText;

    public void generateBook() throws IOException{

        StringBuilder bookText = new StringBuilder("<h1>The story of Marlarkey</h1>");
        bookText.append("<p>This text was generated for NaNoGenMo 2024</p>");

        // Generate the books structure
        String queryForStructure = structurePromptText.getContentAsString(Charset.defaultCharset());
        String structureResponse = llmClient.generateLlmStringResponse(queryForStructure);
        System.out.println("\n\n----- Structure response -------\n\n");
        System.out.println(structureResponse);
        System.out.println("\n\n------------\n\n");


        String summaryPrompt = "";

        for(int currentChapterNumber=1;currentChapterNumber<=NUMBER_OF_CHAPTERS;currentChapterNumber++) {
            String chapterNumberInWords = Utilities.convertIntToWords(currentChapterNumber);

            // Generate chapter structure
            String chapterStructure = chapterStructurePromptText.getContentAsString(Charset.defaultCharset());
            PromptTemplate promptTemplate = new PromptTemplate(chapterStructure);
            Map<String, Object> chapterStructurePromptParameters = new HashMap<>();
            chapterStructurePromptParameters.put("chapterNumber",currentChapterNumber);
            chapterStructurePromptParameters.put("bookStructure",structureResponse);
            Message m = promptTemplate.createMessage(chapterStructurePromptParameters);
            String chapterStructureResponse = llmClient.generateLlmStringResponse(m.getContent());
            System.out.println("\n\n---- Structure for chapter "+currentChapterNumber+"----\n\n");
            System.out.println(chapterStructureResponse);

            // TODO rename this variable - it tracks the growing structure query

            String queryForChapterContributors = """
                    Outline the contributions from interviewees that form the oral history,
                    with a brief description of the person's role and what they will say. These separate accounts should 
                    work to tell an overall story.
                    
                    The description of the full book is:
                    
                    """ + structureResponse +
                    (summaryPrompt.isEmpty()? "": "A summary of background information for the books is \n" + summaryPrompt)+
                    """
                    This will be from chapter %s. A summary of this chapter is here:
                   
                    """.formatted(chapterNumberInWords) + chapterStructureResponse +
                    "\nThe main contributors for a chapter will be the band. Other perspectives will come from both new interviewees and those who have appeared before. New interviewees should be added to each chapter, increasing the range of voices.";
            String chapterContributorsResponse = llmClient.generateLlmStringResponse(queryForChapterContributors);

            System.out.println("\n\n---- Chapter "+currentChapterNumber+ " contributors ----\n\n");
            System.out.println(chapterContributorsResponse);

            String chapterOutlines = "The description of the full book is:\n"+
                    structureResponse +
                    "This will be from chapter %s. A summary of this chapter is here:\n\n".formatted(chapterNumberInWords) + chapterStructureResponse
                    +"An outline of the chapter is \n\n" +
                    chapterStructureResponse + "\n\nA summary of the contributors for the chapter is \n\n"
                    +chapterContributorsResponse;

            StringBuilder chapterText = new StringBuilder();
            while(Utilities.getWordCount(chapterText.toString())<NUMBER_OF_WORDS) {

                // Generate oral history structure
                String oralHistorySection = oralHistoryPromptText.getContentAsString(Charset.defaultCharset());
                Message m2 = getOralHistoryMessage(chapterOutlines, chapterText, oralHistorySection);
                String chapterTextResponse  = llmClient.generateLlmStringResponse(m2.getContent());
                System.out.println("\n\n--------Chapter response  -----\n\n");
                System.out.println(chapterTextResponse);

                chapterText.append(chapterTextResponse.replace("```html","").replace("```",""));
            }

            System.out.println("\n\n--------Full chapter "+chapterNumberInWords+" text is "+Utilities.getWordCount(chapterText.toString())+" words -----\n\n");
            System.out.println(chapterText);

            bookText.append(chapterText);

            // This contains the summary of the book so far, so we add in the previous chapter summary
            if(summaryPrompt.isEmpty()) {
                summaryPrompt = """
                        Please summarise the information about a band called Malarkey from the following book chapter.
                        
                        Please list the members of the band Malarkey. Also list people who have featured significantly
                        in this chapter. Also list any songs Malarkey were famous for. You should also summarise incidents
                        that are likely to be referred to later in the book, as well as any relevant facts.
                        
                        """ + chapterText;
            } else {
                summaryPrompt = """
                        Please produce a list of facts about a band called Malarkey from a summary of the book up to 
                        chapter %s. The whole of that chapter is then included.

                        Please list the members of the band Malarkey. Also list people who have featured significantly
                        in this chapter. Also list any songs Malarkey were famous for. You should also summarise incidents
                        that are likely to be referred to later in the book, as well as any relevant facts.
                        
                        The complete summary should cover the whole of the book so far.
                        
                        """.formatted(chapterNumberInWords) + "A summary of the earlier chapters is \n"
                        +summaryPrompt
                        +"\nThe complete chapter that follows is \n\n"+chapterText;

            }
            summaryPrompt = """
                    Please summarise the following information for a book about a band called Malarkey. The outline for
                    the book is:
                    """ + summaryPrompt;

            summaryPrompt = llmClient.generateLlmStringResponse(summaryPrompt);

            System.out.println("\n\n--------Summary produced for chapter -----\n\n");
            System.out.println(summaryPrompt);

        } // End chapter for loop

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("full-output.html"))) {
            writer.write(bookText.toString());
            System.out.println("File saved successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred while saving the file: " + e.getMessage());
        }
        pdfOutputGenerator.generate(bookText.toString());

    }

    private static Message getOralHistoryMessage(String chapterOutlines, StringBuilder chapterText, String oralHistorySection) {
        Map<String, Object> oralHistoryPromptParameters = new HashMap<>();
        oralHistoryPromptParameters.put("numberOfWords", NUMBER_OF_WORDS);
        oralHistoryPromptParameters.put("chapterOutlines", chapterOutlines);
        String leadInText;
        if(chapterText.isEmpty()) {
            leadInText="Begin with writing the opening of this chapter, starting with the chapter title (contained within an H2 element:";
        } else {
            leadInText="\nThe chapter text is below. You should continue writing new text that can follow on from the text so far: \n\n"
                    + chapterText;
        }
        oralHistoryPromptParameters.put("textIntro", leadInText);
        PromptTemplate promptTemplate2 = new PromptTemplate(oralHistorySection);
        return promptTemplate2.createMessage(oralHistoryPromptParameters);
    }
}
