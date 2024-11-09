# Journal

## 2024-11-09
* Time: 1 hour, total 9.75 hours
* Added utility method to generate numbers as strings
* Fixed a weird bug with ResponseFormat imports

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
* [Added new commit to share work with other laptop](https://github.com/orbific/OralHistoryGenerator/commit/8d97fe0287f29e6cce9de3d163335acf85004fab)
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
