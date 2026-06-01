# refactoring code

- What improvements can you make to the design/architecture of your code?
 find ways to shorten code, rename variables, clear debug prints, remove unused code that is commented out

- How can you split up large methods or classes into smaller components?
split every functional code into methods

- Are there unused files/methods that can be removed?
printVideoMetaData is a method only used for debugging, blank files in folder for git tracking

- Where would additional Java interfaces be appropriate?

- How can you make things simpler, more-usable, and easier to maintain?
maybe combine methods if it doesn't make the method too big 

- Other refactoring improvements?
remove system.out.println maybe


# adding tests
- What portions of your code are untested / only lightly tested?
video length tests- if the video is too long or maybe there is too many frames to process

- Where would be the highest priority places to add new tests?
videoprocessor - try to find where it could fail and write tests for that

- Other testing improvements?
always could have more tests


# improving error handling
- What parts of your code are brittle?

- Where could you better be using exceptions?
anywhere is arguments being used or server connections

- Where can you better add input validation to check invalid input?
command line args or jar args

- How can you better be resolving/logging/surfacing errors? Hint: almost any place you're using "throws Exception" or "catch(Exception e)" should likely be improved to specify the specific types of exceptions that might be thrown or caught.
better details of where and why the exception was thrown

- Other error handling improvements?
clear error messages


# writing documentation
- What portions of your code are missing Javadoc/JSdoc for the methods/classes?
code has comments to explain code but no docs

- What documentation could be made clearer or improved?
all of them, explained in tech language and also plain language

- Are there sections of dead code that are commented out?
yes

- Where would be the most important places to add documentation to make your code easier to read?
the files that handle the most args or inputs
- Other documentation improvements?


# improving performance (optional)
- What parts of your code / tests run particularly slowly?

- What speed improvements would most make running / maintaining your code better?

- Other performance improvements?


# hardening security (optional)
- What packages / images are out of date / have security issues?

- Where could you have better input validation in your code to prevent malicious use?

- Other security improvements?


# bug fixes (optional)
- What bugs do you know exist?

- What parts of the code do you think might be causing them?

- Other bug fix improvements?


# other
- Any other improvements in general you could make?
clean up and find where to shorten, makes clear comments on top of page to explain what the file does.