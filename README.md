# akka-http-restdoc

This is a proof-of-concept for producing snippets from akka-http tests. The idea is to provide similar functionality as [Spring Restdocs](https://github.com/spring-projects/spring-restdocs). 
In contrast to tools such as [Swagger](http://swagger.io/), the aim is to use the tests to generate documentation snippets that show the requests and response parts, and to use a tool such as AsciiDoctor to
integrate thos snippets into the bulk of the documentation, written in a language made for docs. The basic ideas are very well explained in this Spring Restdocs 
[video](https://www.youtube.com/watch?v=knH5ihPNiUs&feature=youtu.be).

This proof-of-concept is in many ways incomplete. Paths are hard-coded, it is not made available as a library, it does not format requests and responses properly, the curl calls are probably incomplete, and 
the integration with the akka-http-testkit is probably suboptimal as well. However, it shows how a spring-retdocs like functionality can be added on top of akka-http.

# How To Run The Example

First of all you will need `sbt-site` build from `master`. Therefore, checkout sbt-site and publish it local:

    git clone git@github.com:sbt/sbt-site.git
    cd sbt-site
    sbt publishLocal
    
If you have another version of `sbt-site` published locally already, the dependency declaration in the akka-http-restdoc example might not be working properly. 
In that case replace the `1.0+` with the exact version published in the step before.

Now, check out the example and run the tests and generate the docs

    git clone git@github.com:kodemaniak/akka-http-restdoc.git
    cd akka-http-restdoc
    sbt clean test make-site
    
The generated documentation can now be found in the file `target/asciidoctor/main.html`.
