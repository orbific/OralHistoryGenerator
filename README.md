# OralHistoryGenerator
A project for NaNoGenMo to create a fictional book-length oral history using an LLM

## Journal

### 2024-11-04
* **TODO** Draft a blog post on what I've learned so far
* **TODO** set up issues for the TODO features listed in this document
* **TODO** fix the TODOs in my code
* **TODO** move to focussing more on prompt analysis / making this more novel like
* **TODO** write up usage instructions for the application
* **TODO** stop the test harness from running the full application
* **TODO** can I randomise elements to add to the prompts to make them more varied.
* **TODO** Need to enforce consistency in the text
* **TODO** Split the readme and this diary into separate files.
* **TODO** Start thinking about validation
* **TODO** How to make this adjustable for more types of story - splitting topic from format
* **TODO** need to consider how to test this when it can produce text so fast
* **TODO** Add an ebook generator
* **TODO** Look at parameterising application properties such as the model to use
* **TODO** Add notes on how to run the application
* **TODO** Split out the chapterMetadata overview from an overall synopsis
* **TODO** Add some recurring characters, and possibly more of an over-arcing story
* **TODO** Catch up my GenAI blog posts. (red notebook p5)

### 2024-11-03
* Time: 1.25 hour, total 5 hours
* Started today by parameterising the number of chapters and the chapter length
* Checked the new HTML generator is working
* Set up a basic PDF generator which looks fairly poor so far
* Started work on a test harness, but need to figure out how to override the commandline runner, which launches when the spring context is set up.
* [Added new commit to share work with other laptop](https://github.com/orbific/OralHistoryGenerator/commit/8d97fe0287f29e6cce9de3d163335acf85004fab)
* **Total cost for the day**: 3c

### 2024-11-02
* Time: 1.25 hours, total 3.75 hours
* Refactored JSON definitions to separate chapterMetadata definitions from text
* Created a generic OutputGenerator interface
* The current version can generate 1700 words of text in about 20 seconds. So I have an engine that can potentially generate 50000 words
* Ramping this up tp produce a full novel produced 83,000 words in 16 minutes at a cost of 7c
* I made my first two commits - 
  * [An initial working version](https://github.com/orbific/OralHistoryGenerator/commit/af463b78144ffbfa4868b4b25fd77efbed217fc7)
  * [A version that produces a full-length novel](https://github.com/orbific/OralHistoryGenerator/commit/d7c9698ffdfe8dcf8dd85524d0afb96af2b5c3bf)
  * Could have a prompt to add background information about the topic
* **Total cost for the day**: 3c + 7c to write a 80,000 word novel
* 
### 2024-11-01
* Time: 2.5 hours, total 2.5 hours
* Set up the basic project using Spring AI
* Some fiddliness setting up Spring. Decided to make this a [commandline application](https://www.baeldung.com/spring-boot-console-app)
* Chose to use OpenAI's 4o mini for the prototyping phase
* Established JSON schemas for the responses using [structured outputs](https://spring.io/blog/2024/08/09/spring-ai-embraces-openais-structured-outputs-enhancing-json-response), which will reduce the risk of extraneous information being added by ChatGPT
* **Total cost for the day**: 2c 

#### Sample output
*“We called it ‘the vibe.’ You could feel it in the air, a kind of electricity that drew people together. I was convinced that we were channeling some ancient energy, something primal. It felt like a revival, a return to a lost way of being. The music was our ritual, the dance our prayer.”*
— Liam, 22, Manchester, 1988
