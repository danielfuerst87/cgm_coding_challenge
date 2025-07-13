# cgm_coding_challenge

This repo contains a Java command line application for asking questions and storing answers, implented with openjdk version "21.0.7".

A user has 2 options: 
- Ask a specific question 
- Add questions and their answers<br>

Therefore the following restrictions apply:

- A question is a String with max 255 chars
- An answer is a String with max 255 chars
- A question can have multiple answers (like bullet points)
- If the user asks a question it has to be exactly the same as entered – no “best match”.
- If the user asks a question which is not stored yet, the program should print “the answer to life, universe and everything is 42” according to “The hitchhikers guide to the Galaxy”
- If the user asks a question which is stored, the program should print all answers to that question. Every Answer in a separate line
- Adding a question looks like:
question? “answer1” “answer2” ... “answerX”
- Char “?” is the separator between question and answers
- Every question needs to have at least one answer but can have unlimited answers all inside of char "

Examples:

- Adding a question:<br>
What is Peters favorite food? “Pizza” “Spaghetti” “Ice cream”<br>
- Asking a question which is in the system:<br>
What is Peters favorite food?<br>
Answers will be:<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ■ Pizza<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ■ Spaghetti<br>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ■ Ice cream
- Asking a question which is not in the system:<br>
When is Peters birthday?<br>
Answer will be:<br>
the answer to life, universe and everything is 42