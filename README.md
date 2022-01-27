# Spring in Action, 6th Edition Sample Code

This repo will hold the sample code from [Spring in Action, 6th edition](https://www.manning.com/books/spring-in-action-sixth-edition).

## What are all of the weird comments with "tag::" and "end::" about???

(Note: In the most recent commit, I've removed these comments. But they may come back, so I'll leave this mention of them here.)

The source code will have several weird comments, many starting with "tag::" and some with "end::". There may also be large chunks of code commented out.

The "tag::" and "end::" comments are there for the sake of building the book itself. The code in between those comments are pulled into the manuscript so that (as much as possible) code you see in the book comes straight from code that was built and tested.

Sometimes, though, code evolves throughout the course of a chapter. It's impractical to have multiple copies of the same project just for a few lines that are different. In those cases, the variant code is commented out, but still has the "tag::" and "end::" markers around it. The danger of doing that is that it's entirely possible that the commented out variant isn't correct--after all, it hasn't been compiled or run through a test suite. If you find something out of sorts, please let me know and I'll try to fix it.

## Shouldn't there be a test for ... ?

I'll readily admit that there could be more tests around certain parts of the code. When I find time, I will add these tests. In the meantime, please feel free to open a pull request with any suggested tests you think should be merged in.
