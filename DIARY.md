# Journal

## 2024-11-04
* Time: 0.5 hour, total 5.5 hours
* Moving the commandline executor to its own class, so it does not automatically run when the context is created for testing.
* Added basic usage notes.
* Mostly a day of tidying up and consolidating what I have so far.
* **TODO** - this is getting complicated enough that I need to add testing
  * Interesting question - testing is not needed when the application is in context, but returning to it later makes it trickier to resume.
* Commit: [Latest version]()
* Created new issues
    * [Output needs to be more novel-like](https://github.com/orbific/OralHistoryGenerator/issues/1)
    * [Look to separate the themes from the mechanics of producing the history](https://github.com/orbific/OralHistoryGenerator/issues/2)
    * [Output of chapters needs to be consistent](https://github.com/orbific/OralHistoryGenerator/issues/3)
    * [Need to investigate output validations](https://github.com/orbific/OralHistoryGenerator/issues/4)
* **TODO** fix the TODOs in my code

## 2024-11-03
* Time: 1.25 hour, total 5 hours
* Started today by parameterizing the number of chapters and the chapter length
* Checked the new HTML generator is working
* Set up a basic PDF generator which looks fairly poor so far
* Started work on a test harness, but need to figure out how to override the commandline runner, which launches when the spring context is set up.
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
