# OralHistoryGenerator
A project for NaNoGenMo to create a fictional book-length oral history using an LLM

## Journal
### 2024-11-01
* Time: 1 hours, total 2.5 hours
* Refactored JSON definitions to separate chapterMetadata definitions from text
* Created a generic OutputGenerator interface
* The current version can generate 1700 words of text in about 20 seconds. So I have an engine that can potentially generate 50000 words

### 2024-11-01
* Time: 2.5 hours, total 2.5 hours
* Set up the basic project using Spring AI
* Some fiddliness setting up Spring. Decided to make this a [commandline application](https://www.baeldung.com/spring-boot-console-app)
* Chose to use OpenAI's 4o mini for the prototyping phase
* Established JSON schemas for the responses using [structured outputs](https://spring.io/blog/2024/08/09/spring-ai-embraces-openais-structured-outputs-enhancing-json-response), which will reduce the risk of extraneous information being added by ChatGPT
* **TODO**: need to consider how to test this when it can produce text so fast
* **TODO**: should be able to output text/HTML/PDF/ebook
* **TODO**: Look at parameterising application properties such as the model to use
* **TODO**: Add notes on how to run the application
* **TODO**: Split out the chapterMetadata overview from an overall synopsis
** **TODO**: Add some recurring characters, and possibly more of an over-arcing story
* **Total cost for the day**: 2c 

#### Sample output
*“We called it ‘the vibe.’ You could feel it in the air, a kind of electricity that drew people together. I was convinced that we were channeling some ancient energy, something primal. It felt like a revival, a return to a lost way of being. The music was our ritual, the dance our prayer.”*
— Liam, 22, Manchester, 1988
