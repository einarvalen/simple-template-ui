===What is this?
It is a simple Java API that can parse html forms into Java objects and produce simple html response snippets to be included in web pages.Primarily it is meant to help quickly build administrative user interfaces to support back-end applications - like web-services - when JMX is not an option.

===Why bother?
I was making JAX-RS restful service operations that returned lists of Java objects, primarily html for human consumption, but also computer readable formats of -- json and XML.

Trying to avoid code bloat, I started creating a little template based rendering engine.
Satisfied with it's usefulness, I added form and input support.
For fun I kept adding more features, -- like National Language Support and support for drop down lists and a host of action-buttons etc.

It promotes a kick start from defaults, but offers ways to refine your product, either by annotations, spring configuration, custom code, or a combination.

It is not restricted to restful services and it may produce more than just HTML output
The intrinsic set of templates may be replaced in part or as a whole.

It can be used with JSP, also, but no JSP is needed, you don't have to use Spring either, in fact, it has no dependencies.
You don't have to buy into the concept of a template based page processing to use this API.
You may use only parts of it. For instance, try using Form only, -- to parse user input into Java objects.

===Getting started
Look at some code in fyfaSamples.
Build fyfa and fyfaSamples with Maven.
fyfaSamples may be started with mvn jetty:run and reached by http://localhost:8080/fyfaSamples

===Core classes
Context.java contains a number of repositories where all knowledge about data types, fields text and templates are kept.
Among the repositories are:
    * Html-templates used to form parts of pages. Default values are loaded from IntrinsicVaues.java
      The templates can be totally or selectively replaced.
    * Fields contain properties related to a java class (DTO) like label, type and max length.
        This repository is populated by Component.java using the associated DTO, including FieldDef annotations or not.
        It may be manually supplemented from Spring configuration for instance.
        Fields are constructed by FieldFactory.java.
    * Texts are stowed away into a repository as an abstraction to ease translation and support for several languages.
        The texts populated from fields, code or from file.
    * Selections are used in drop down lists. A Selection is just a code snippet with a lookup key.
    * Validators are used for extended field validation - more than data type validation.

Component.java of which there are several related classes in the components package.
Component maintains a repository of items. Item is a Field in a setting - for example in a modification form.
Items are constructed by an ItemFactory, of which there are  several in the itemfactories package
A component knows how to render itself.
There are two main types of components: Form.java and Table.java.
    * Table.java does rendering of a list of DTOs. Data aware action buttons may be added at each row.
    * A form may take input from a user, and therefore knows how to validate and parse that input into the DTO.
    * The behavior of a form is determined by FormParams upon construction.
    ** Note the property Operation, to which you may associate ItemDefs.
    ** Note the property ActionUri, which would be the relative URL to the method that will receive the submitted data.
    

