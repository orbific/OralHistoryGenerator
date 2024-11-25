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

    private static final int NUMBER_OF_CHAPTERS = 1;
    private static final int NUMBER_OF_WORDS = 2500;
    @Autowired
    LlmClient llmClient;

    @Autowired
    PdfOutputGenerator pdfOutputGenerator;

    @Value("classpath:/prompts/bandhistory/structure.st")
    private Resource structurePromptText;

    @Value("classpath:/prompts/bandhistory/chapter-breakdown.st")
    private Resource chapterStructurePromptText;

    public void generateBook() throws IOException{

        StringBuilder bookText = new StringBuilder("<h1>The story of Marlarkey</h1>");
        bookText.append("<p>This text was generated for NaNoGenMo 2024</p>");

        // Generate the books structure
        String queryForStructure = structurePromptText.getContentAsString(Charset.defaultCharset());
        String structureResponse = llmClient.generateLlmStringResponse(queryForStructure);
        System.out.println("\n\n----- Structure response -------\n\n");
        System.out.println(structureResponse);
        System.out.println("\n\n------------\n\n");


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
            String summaryPrompt = "";



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
                    "\nThe main contributors for a chapter will be the band. Other perspectives will come from both new interviewees and those who have appeared before.";
//            System.out.println("\n\n---- Chapter "+currentChapterNumber+ " contributors ----\n\n");
//            String chapterContributorsResponse = llmClient.generateLlmStringResponse(queryForChapterContributors);
//
//            System.out.println("\n\n--------Chapter contributor response-----\n\n");
//            System.out.println(chapterContributorsResponse);

String chapterContributorsResponse = """
        ### Oral History Contributions for Chapter 1: **A New Sound in a Grunge World (1994)**
           
        1. **Sam (Frontman)**
           - **Role**: Charismatic frontman and lyricist.
           - **Contribution**: Sam shares stories about his early songwriting process, emphasizing how he wanted to write lyrics that uplifted listeners in a time filled with darkness. He recalls specific moments that sparked his creativity, particularly influenced by the witty lyricism of artists like Elvis Costello. Sam reflects on the band's mission to counter the grunge movement with humor and hope, providing insight into the ethos that drives Malarkey.
           
        2. **Rachael (Guitarist)**
           - **Role**: Skilled guitarist and musical architect of the band's jangly sound.
           - **Contribution**: Rachael discusses the influence of jangly pop bands like The Smiths on her guitar style, explaining how she sought to create a sound that was both catchy and uplifting. She recounts her early experiences in Manchester's music scene and how the supportive atmosphere among local musicians inspired her to experiment and innovate with their music.
           
        3. **Tom (Drummer)**
           - **Role**: Drummer with a punk background, adding energy and flair to performances.
           - **Contribution**: Tom provides anecdotes about the band’s first rehearsals, describing the raw energy and excitement of blending different musical styles. He discusses the dynamics of the band as friends and collaborators, including moments of conflict and laughter that shaped their identity. Tom also reflects on how his punk influences brought a unique rhythm to their sound, setting them apart from their grunge counterparts.
           
        4. **James (Local Venue Owner)**
           - **Role**: Owner of The Roadhouse, a key venue for emerging bands in Manchester.
           - **Contribution**: James offers his perspective on the Manchester music scene, describing the vibrant atmosphere that fostered creativity among indie bands. He shares memories of Malarkey's early gigs at The Roadhouse, highlighting the energy and camaraderie that characterized their performances. James discusses the importance of these local venues in providing a platform for new talent and nurturing the indie music community.
           
        5. **Claire (Fellow Musician)**
           - **Role**: Member of another emerging indie band in Manchester.
           - **Contribution**: Claire reflects on her experiences performing alongside Malarkey and the collaborative spirit of the local scene. She discusses how the band’s positive energy and uplifting sound resonated with audiences and created a sense of hope amidst the prevailing grunge aesthetic. Claire shares her admiration for their approach to songwriting and performance, emphasizing the importance of friendship and support in their musical journeys.
           
        6. **Mike (Music Journalist)**
           - **Role**: Writer for a local music magazine covering the indie scene.
           - **Contribution**: Mike provides context on the broader music landscape of the early '90s, discussing the dominance of grunge and its emotional impact on youth culture. He highlights Malarkey's emergence as a refreshing alternative, noting their unique blend of influences and how they stood out among their peers. Mike reflects on the potential he saw in the band from their earliest performances and his excitement about their future trajectory.
           
        7. **Lucy (Dedicated Fan)**
           - **Role**: Early supporter of Malarkey and frequent concert-goer.
           - **Contribution**: Lucy shares her personal journey of discovering Malarkey and how their music resonated with her during a turbulent time in her life. She recounts her experiences at their early gigs and the sense of community and belonging she felt among fellow fans. Lucy emphasizes the importance of Malarkey's message of joy and hope, explaining how it provided a much-needed escape from the heaviness of the grunge scene.
           
        ### Conclusion of Contributions
        These diverse perspectives weave together to form a rich tapestry that outlines the formation of Malarkey against the backdrop of a grunge-dominated music scene. Each interviewee contributes unique insights that highlight the band's aspirations, the supportive environment of Manchester's indie scene, and the emotional connections forged through their music. This collective narrative sets the stage for the band's adventurous journey as they embrace their DIY ethos and prepare for the next steps in their evolution.     
        """;

            String chapterOutlines = "The description of the full book is:\n"+
                    structureResponse +
                    "This will be from chapter %s. A summary of this chapter is here:\n\n".formatted(chapterNumberInWords) + chapterStructureResponse
                    +"An outline of the chapter is \n\n" +
                    chapterStructureResponse + "\n\nA summary of the contributors for the chapter is \n\n"
                    +chapterContributorsResponse;

            StringBuilder chapterText = new StringBuilder();
            while(Utilities.getWordCount(chapterText.toString())<NUMBER_OF_WORDS) {
                // TODO - this may need to include the previous sections of the chapter
                String requestOralHistory = """
                    You are to produce the contents of a chapter from an oral history, featuring contributions from
                    interviewees, with a brief description of the person's role and their edited statement. These
                    contributions should help to tell the story and develop the themes of the chapter.
                        
                    There should be no editorialising in the text, only edited transcripts from the interviewees.
                    
                    Examples of a section from the text would be:
                    
                        <p><b>Lucy Caldwell (Music Journalist)</b>:<br/>
                        I was at that Railway Arms gig. It wasn’t by design—I’d come to see the headliner—but Malarkey caught my attention. They weren’t good, not in a traditional sense, but they were fascinating. Jack Forrester was magnetic, all limbs and sharp edges, like he didn’t belong on that tiny stage. His voice was rough, like he’d been shouting at the bus driver all day, but it worked</p>
                        
                        <p><b>Dave Partridge (Audience Member at Birmingham Gig)</b>:<br/>
                        The first time I saw Malarkey, I didn’t think much of them. They were loud, chaotic, and a bit cocky for a band I’d never heard of. Jack, the singer, had this swagger about him, like he thought he was already a star. I wasn’t convinced.</p>
                        <p>But I’ll say this: they didn’t back down. The crowd wasn’t having it—we wanted something heavy, something grungy. Instead, they gave us these jangly little pop songs with sarcastic lyrics. One guy yelled, “Play ‘Teen Spirit!’” and Jack just grinned and said, “We don’t do requests.”</p>
                        
                        <p><b>Henry (Promoter)</b>:<br/>
                        I still chuckle at some of the early mishaps, like when they forgot the lyrics during a pivotal show. But it only added to their charm. They were real, and that’s what people loved.</p>
                    
                    All accounts should be in the past tense.
                    Each account should begin with a name and role, and be only for a single person.
                    It is important that each account consists of nothing more than the name and role of the person speaking and their direct, quoted account.
                    
                    There should be no anonymous contributors.
                    
                    Some of the quotes may come from real people who were well-known in the music business of the time.
                    
                    Where possible the accounts should contain specific details about the era. The tone of the book
                    overall is to be fun and provocative, telling an interesting story. 
                    
                    There should be no headings to group the interviews. We should solely have the accounts from 
                    the participants.    
                    
                    Contributions should be of varying length, from one paragraph to three or four.

                    Your response should be formatted as HTML suitable for adding to the body of another document.
                    Each of the interviews should have their paragraphs wrapped in paragraph <p> tags
                    Use <b> to format the title for each interview (name and role), and have a line break after 

                    The full chapter till be 
                    """ + NUMBER_OF_WORDS+ "words in total\n" + chapterOutlines;

                if(chapterText.length()==0) {
                    requestOralHistory+="\nBegin with writing the opening of this chapter, starting with the chapter title (contained within an H2 element:";
                } else {
                    requestOralHistory+="\nThe chapter text is below. You should continue writing new text that can follow on from the text so far: \n\n"
                            +chapterText;
                }

                String chapterTextResponse  = llmClient.generateLlmStringResponse(requestOralHistory);
                System.out.println("\n\n--------Chapter response  -----\n\n");
                System.out.println(chapterTextResponse);


                // TODO - I should be checking this HTML for validity/appropriateness
                // And throw it back if i hate it?

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

//A PROMPT I USED        Summarise this text with the facts we know about Malarkey from it.
//        // This prompt produces the sort of text I want.
//        String bookSummaryPrompt = """
//                You are producing sections from an oral history. These are in the voice of different people who were
//                witnesses to the scenes involved. The different accounts might be in dialogue with each other. The book
//                these are from tells the story of a Britpop band called Malarkey. We will begin with descriptions of
//                their early gigs in 1994. Many of their audiences were more into grunge and were unimpressed by someone
//                doing the Libertines almost a decade early. The first account should be one that sets context for the
//                other accounts. This section of the oral history should introduce Malarkey and the context of the time
//                when they played their first gigs. Feel free to make up details.
//
//                Each account should begin only with the name of the person and their role in relation to the band.
//                """;
//
//
//        String chapterSoFar = chapterOpeningResponse;
//        while(Utilities.getWordCount(chapterSoFar)<1500) {
//            String chapterContinuesPrompt = """
//                    You are to continue writing a chapter from an oral history, featuring contributions from
//                    interviewees, with a brief description of the person's role and their edited statement. These
//                    contributions should help to tell the story and develop the themes of the chapter.
//
//                    When describing the role this should be consistent and clear - ie bassist, fan etc.
//
//                    There should be no editorialising in the text, only edited transcripts from the interviewees.
//
//                    The full chapter till be 2500 words.
//                    The details of the chapter these accounts are from is:
//
//                    """ + chapterStructureResponse + """
//
//                    The chapter begins:
//
//                    """ + chapterOpeningResponse;
//
//            String chapterContinues = llmClient.generateLlmStringResponse(chapterContinuesPrompt);
//            chapterSoFar = chapterSoFar + chapterContinues;
//        }
////        System.out.println("WORD COUNT: "+Utilities.getWordCount(chapterSoFar));
////        System.out.println(chapterSoFar);
//        String requestChapterSummary = queryForPeople + chapterSoFar;
//        String summary = llmClient.generateLlmStringResponse(requestChapterSummary);
////        System.out.println("WORD COUNT: "+Utilities.getWordCount(chapterSoFar));
//        System.out.println(summary);
//
//        String chapterOneSummary = """
//                1. **Jamie** - Lead singer of Malarkey. He played a pivotal role in the band's formation and emphasized the chemistry and creative process within the group. He was influenced by the Manchester music scene and sought to connect with audiences through their music.
//
//                2. **Rosie** - Guitarist of Malarkey. She shared the vision of creating music that resonated with others and highlighted the importance of community and the band's influences, including iconic bands like The Stone Roses and Oasis.
//
//                3. **Sam** - Bassist of Malarkey. He entered the group with a punk background, which contrasted with Jamie and Rosie’s melodic influences. His contributions to songwriting were significant, particularly in blending different musical styles.
//
//                4. **Liam** - Drummer of Malarkey. He contributed to the band's early sound and dynamics, bringing energy to their creative process and performances. He reflected on the challenges and growth the band experienced during their early gigs.
//
//                ### Named Members of Malarkey:
//                - **Jamie (Lead Singer)**
//                - **Rosie (Guitarist)**
//                - **Sam (Bassist)**
//                - **Liam (Drummer)**
//
//                ### Songs by Malarkey:
//                1. **Going Nowhere** - A song that emerged from collaborative songwriting between Jamie, Rosie, and Sam, highlighting the blend of raw lyrics with pop elements.
//
//                ### Additional Information:
//                - The members of Malarkey drew inspiration from influential bands in the Manchester music scene, like The Stone Roses, Oasis, The Charlatans, and The Verve.
//                - Their early days were marked by a struggle to find their unique sound through jam sessions, gigs, and a mix of influences.
//                - They had ambitions of touring and connecting with audiences, highlighting their desire for authenticity in their music.
//                """;

    }
}
