# web-crawler

This program crawles a single domain and displays a sitemap. 
It's by no means a production application but rather an example project of 
how such a program could be structured. It has not been extensively
tested, nor does it contain tests. It does not process subdomains correctly.

## Installation and Usage

You need to have Java version 1.6 or later installed (check with `java -version`). To build
or run the program you need [leiningen](https://leiningen.org/). Leiningen is a buildtool for 
clojure. Please follow the instructions at the leiningen homepage to install it. 

Use of this project requires that [Graphviz](http://www.graphviz.org) is installed, 
which can be checked by running `dot -V` at the command line.  If it's not installed, 
you can follow the instructions at http://www.graphviz.org/download/ .

You can then either run the program directly via leiningen, like so:
```
lein run -d https://example.com 
```
or build a standalone jar file for the JVM as follows:
```
lein uberjar 
java -jar target/uberjar/web-crawler-0.1.0-SNAPSHOT-standalone.jar -d https://example.com
```
