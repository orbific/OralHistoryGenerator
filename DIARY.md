# Journal

# 2024-11-25
* 30 minutes + countdown
* Trying to move towards completion - moving the prompts for this oral band history into resource files.
* Also experimenting a little with temperature to see if this produces something better.
  * Setting the temperature to 1.5 produces incoherent text
  * 1.1 mostly works, but there was an accented character that produced weird foramtting in the PDF
* Merging in code from other laptop

# 2024-11-24
* 15 minutes
* Trying to break down the prompts a little so I can get review the actual text
* Not well, so early night.

# 2024-11-23
* 45 minutes
* Changing my few-shot examples to contain example HTML, then producing a short document to review
  * Few-shot learning appears to be pretty good at setting a format, but I need to see it over a whole document.
* Generating a complete document to see how it looks
* TODO - need to review the issues I've created.
  * [Need to figure how to review the documents produced](https://github.com/orbific/OralHistoryGenerator/issues/4)
  * [Separate the themes from novel generation](https://github.com/orbific/OralHistoryGenerator/issues/2)
    * I should put all the indie music specific bits of prompting into an external config file
* TODO - Copy across the code from work laptop
* TODO - Tidy up what I have now, and make two main classes - pulp novel generator and oral history generator
* TODO - Can I supply the whole book to the context? Would be very expensive in terms of tokens.
* Coding by persuasion, having to set out every single thing needed. How to avoid it forgetting things?

# 2024-11-22
# 45 minutes
* Working to hone the output to get a sample document out - 
  * Will be interesting to see how rigorous I need to be with the formatting - seems to be OK with HTML
* Thinking about the relative costs of models
  * Using 40-mini is costing 15c/1M input and 60c/1M output
  * Upgrading to 4o would be $2.50/1M input and $10/1M outout

# 2024-11-21
# 45 minutes
* Printing the complete text took 45 minutes and 18c
* It feels like an oral history, but the responses are repeating text.
* I produced a large version, but there were sections of repeated text. 
  * I made it plain that the text generated should not repeat earlier sections

# 2024-11-20
* 75 minutes
* Continuing with working on the prompts - a couple of examples of format seems to help here.
* I have a half-decent opening text here
* The band narrative seems to produce something stronger - the LLM can immediately 'see' a narrative
* Tried to craft a reusable summary prompt
* I seem to have the basics for producing the whole novel - going to be interestin to see if this worls

## 2024-11-19
* Time 1 hour
* Trying to move my experiments with the web tool via the API
* The work seems to be in the prompts more than the structure of the code - so, putting out the appropriate prompts and working with those.
* Trying to get text that isn't trite seems to be much easier with the web UI - this seems much richer

## 2024-11-18
* Time 0.75 hour, total 18 hours
* Working with the web tool seems to produce more interesting results - much more vivid and interesting
  * I was getting lots of network errors however, which were eased mobile data on my phone
  * Interesting to think about why the responses were better - possibly the context being much richer
    * Spring appears to have the MemoryChatAdvisor to help with this
    * But it might be better to implement something myself
    * Also, do my web-based chats with the LLM add to the overall context?
  * Another possibility is that the model is simply better at discussing some topics

## 2024-11-17
* Time 0.5 hour, total 17.25 hours
* Playing with generating a single account
* I can produce an entire novel-length text, but there is little coherence. It doesn't feel like a novel.
* So, I need to figure out how to make that happen.
* It's such a weirdly indirect way to write...

## 2024-11-16
* Time: 1.25 hour, total 16.75 hours
* Published [a blog post](https://jamesburt.me.uk/2024/11/nanogenmo-updates/) about my experience so far
* Amended the logging so it's easier to follow the prompts and responses. This should allow me to focus on the exchanges with the LLM for the oral history 
* Working out how best to log the generation is tricky - prompts return formatted data, but this is hard to read from logging
  * Maybe I need another log which can track the book generation?
* It feels very weird to be programming JSON responses in natural language - when it should be obvious how to respond
  * Problems occurred because one of my fields was called text, which seemed to confuse the response/parser
* Failed to set the JSON property names - had only changed the variable names

## 2024-11-15
* Time: 0.75 hour, total 15.5 hours
* Committed the latest changes, mainly separating out the prompts for each type of novel
* Added a system prompt containing the main rules on writing style to attempt to separate style from story
* Went back to the oral history to produce a sample. 
* TODO fix the layout issues for the oral history - might need to structure the single accounts.
* TODO still need to consider validation issues - how to check something like this?
* TODO need to write up an account for this.

## 2024-11-14
* Time: 1 hour, total 14.75 hours
* Creating a version of the novel to review
* Fixed a minor but annoying formatting bug where ":" was being added to the end of sections.
* Removed hardcoding for the title in the output text
* TODO Should there be a filter of some type to review the text produced and tidy it
* TODO have tried moving the resource files into a new folder so I can simply create a JAR of the plugins.
* TODO there is a real element of tell not show in the text - need to figure how to work around that

## 2024-11-13
* Time: 1 hour, total 13.75 hours
* Rewrote git history to remove incorrect username, fixing associated git consistency issues
* Completed refactoring
* Fixing various issues with git access and SSH keys 
* Also managed to lose the key I was using for openAI
* An entire day of not achieving much, due to inefficient sharing of code

## 2024-11-12
* Time: 0.25 hour, total 12.75 hours
* Continuing with the refactoring, trying to complete that. Probably doing too much in one go
* Fixing refactoring from dividing up the two types of book.

## 2024-11-11
* Time: 0.75 hour, total 12.5 hours
* Looking at basic tests - instantiation of new objects making it difficult to test these - I can now see where Factory classes come from.
  * Moving the LlmClient method out to be a new class
* Started to split out the oral-history generator from the simple novel experiment

## 2024-11-10
* Time: 1 hour, total 11.75 hours


## 2024-11-09
* Time: 1 hour, total 10.75 hours
* Added utility method to generate numbers as strings
* Fixed a weird bug with ResponseFormat imports
* Adding more description of speaker to the single-account generation script.
* A lot of these accounts don't really work as oral history somehow. I can now start trying to 'debug' them
* Adding new method to generate a chapter from individual accounts
  * TODO - need to work out how to switch between these.
* Ran the script so far to see how it did on three 1500-word chapters
* TODO - tomorrow - consider how to generate a narrative novel simply
  * This will allow me to explore how to get a simple case working
* TODO I need to spend a day grinding through test cases

## 2024-11-08
* Time: 1 hour, total 9.75 hours
* Completed refactor of Bookbuilder class, created utility method to call LLM
* Started working on a prompt for a single account
* Created a test harness method to generate a single oral history account
* [Commit for refactoring and changes to make chapter generation stateful](https://github.com/orbific/OralHistoryGenerator/commit/1d60b1487369d1f33d684646ab7d4e28dbe75e55)
* [Further refactoring, added test harness to create a single account](https://github.com/orbific/OralHistoryGenerator/commit/4ea459c29af8f03835872346a7d689be447fba05)

## 2024-11-07
* Time: 1 hour, total 8.75 hours
* Fixed a foolish bug where I was miscounting the length of a chapter, and making too many requests to generate it
* Altered the chapter length check to use word count
* Generated a full version of the book using the current prompts, which pass in chapter context
* Fixed some of the TODOs in the project - notably making the chapter's title part of the ChapterOutput object
* TODO: Produce a more structured chapter body, breaking it down into separate accounts that are JSON-structured
* TODO: Set up co-pilot in my IDE
* Generation of a current version of the novel took about 11 minutes for 80,000 words, costing around 10c, but contained repeated text

## 2024-11-06
* Time: 1 hour, total 7.75 hours
* Going to start with synopsis and work down through chapter (checking for arc) through to individual accounts
* Changing the prompt for the chapter headings seems to produce more interesting results.
* Created a new prompt to generate a basic list of recurring characters
* Refactored the chapter data objects so that there was a separation between metadata and output
* Started changing the chapter generation to include a cast of recurring characters
  * TODO - will later need to note what else characters have done earlier in the book
    * this will require having a reference for all pieces of text involving the character
    * which will therefore involve adding more structure to the chapter text 
* TODO - consider generating the cast with reference to the chapters
* TODO change the logs to make them easier to read
* Tried generating a complete novel, but there was a loop in the code resending the same request
* [Commit to share work between laptops](https://github.com/orbific/OralHistoryGenerator/commit/f430a4b2e8ec2ad8965fa73a29b5d92c7295187d)
* **Total cost for the day**: 11c

## 2024-11-05
* Time: 1 hour, total 6.75 hours
* Working with the chapter prompts and wondering how to make the accounts sound more authentic
* Started refactoring the code to break up the larger methods
* Making a change to have the repeated calls to the chapter generation include the previous prompts
* TODO - need to revisit the data structures being used as they are not helping.
* **Total cost for the day**: <1c

## 2024-11-04
* Time: 0.75 hour, total 5.75 hours
* Moving the commandline executor to its own class, so it does not automatically run when the context is created for testing.
* Added basic usage notes.
* Mostly a day of tidying up and consolidating what I have so far.
* **TODO** - this is getting complicated enough that I need to add testing
  * Interesting question - testing is not needed when the application is in context, but returning to it later makes it trickier to resume.
* Created new issues
    * [Output needs to be more novel-like](https://github.com/orbific/OralHistoryGenerator/issues/1)
    * [Look to separate the themes from the mechanics of producing the history](https://github.com/orbific/OralHistoryGenerator/issues/2)
    * [Output of chapters needs to be consistent](https://github.com/orbific/OralHistoryGenerator/issues/3)
    * [Need to investigate output validations](https://github.com/orbific/OralHistoryGenerator/issues/4)
* [Latest commit](https://github.com/orbific/OralHistoryGenerator/commit/6ffc9b9e6a32719abdd11d19ef7e3bf2167342a4)
* **TODO** fix the TODOs in my code
* **TODO** Catch up my GenAI blog posts. (red notebook p5)
* **Total cost for the day**: 0c

## 2024-11-03
* Time: 1.25 hour, total 5 hours
* Started today by parameterizing the number of chapters and the chapter length
* Checked the new HTML generator is working
* Set up a basic PDF generator which looks fairly poor so far
* Started work on a test harness, but need to figure out how to override the commandline runner, which launches when the spring context is set up.
<<<<<<< HEAD
* [Added new commit to share work with other laptop](https://github.com/orbific/OralHistoryGenerator/commit/8d97fe0287f29e6cce9de3d163335acf85004fab)
=======
>>>>>>> 8559fed0338ed6ea117849b579c4f325049b7064
* **Total cost for the day**: 3c

## 2024-11-02
* Time: 1.25 hours, total 3.75 hours
* Refactored JSON definitions to separate chapterMetadata definitions from text
* Created a generic OutputGenerator interface
* The current version can generate 1700 words of text in about 20 seconds. So I have an engine that can potentially generate 50000 words
* Ramping this up tp produce a full novel produced 83,000 words in 16 minutes at a cost of 7c
* I made my first two commits -
    * [An initial working version](https://github.com/orbific/OralHistoryGenerator/commit/af463b78144ffbfa4868b4b25fd77efbed217fc7)
    * [A version that produces a full-length novel](https://github.com/orbific/OralHistoryGenerator/commit/d7c9698ffdfe8dcf8dd85524d0afb96af2b5c3bf)
    * Could have a prompt to add background information about the topic
* **Total cost for the day**: 3c + 7c to write na 80,000 word novel

## 2024-11-01
* Time: 2.5 hours, total 2.5 hours
* Set up the basic project using Spring AI
* Some hassles with setting up Spring in the IDE. Decided to make this a [commandline application](https://www.baeldung.com/spring-boot-console-app)
* Chose to use OpenAI's 4o mini for the prototyping phase
* Established JSON schemas for the responses using [structured outputs](https://spring.io/blog/2024/08/09/spring-ai-embraces-openais-structured-outputs-enhancing-json-response), which will reduce the risk of extraneous information being added by ChatGPT
* **Total cost for the day**: 2c

### Sample output
*“We called it ‘the vibe.’ You could feel it in the air, a kind of electricity that drew people together. I was convinced that we were channeling some ancient energy, something primal. It felt like a revival, a return to a lost way of being. The music was our ritual, the dance our prayer.”*
— Liam, 22, Manchester, 1988
