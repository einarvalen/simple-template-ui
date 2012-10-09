<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Content-Style-Type" content="text/css">
		<title>Index</title>
		
<style type="text/css">
BODY {
  color: #000066;
  background-color: #dddddd;
  font-family: Helvetica, Verdana, Arial, sans-serif; 
}
h1 {
	text-align: center;
	color: #3e099d;
	font-size:150%;
	line-height:normal;
	margin-bottom: 5px;
	padding-top: 3px;
	padding-right: 20px;
	padding-bottom: 3px;
	padding-left: 20px;
}
</style>
</head>
<body>
  <div style="height:100px;background-color:#dddddd";>
	<h1><a href="service/rest/simple/form">Fyfa Samples</a></h1>
	<h2>Why bother</h2>
<p>	
I was making JAX-RS restful service operations that returned lists of java objects (DTOs), optionally returned as json, xml or html.
Trying to avoid code bloat got me started creating a little template based rendering engine.
Satisfied with it's usefulness, made me add form and input support, to ease transformations in and out of java classes. 
From pure enjoyment of my work, I added more features, like NLS, drop down lists, table row buttons etc.
</p>
<p>
Fyfa is designed to give a kick start from defaults,
then offer you the option to enhance your product by annotations, spring configuration and custom code.
It is extendible and possible to tailor to your own preference.
</p>
<p>
It is not restricted to use in the restful services realm, and it is not HTML only. 
The intrinsic set of templates may be replaces in part or as a whole.
</p>
<p>
It can be used with JSP, also, but no JSP is needed, you don't have to use Spring either, in fact, it has no dependencies.
You don't have to buy into the concept of a template based page processing to use this API.
You may use only parts of it. Try using Form only, to parse user input into java objects.
</p>
<h2>Getting started</h2>
<p>
Follow the progression in this show-case application; <a href="service/rest/simple/form">Fyfa Samples</a>.
At your leisure, inspect the code in fyfSamples and fyfa itself to uncover how it all works.
</p>
<h2>Core classes</h2>
<p>
Context.java contains a number of repositories where all knowledge about data types, fields text and templates are kept.
Among the repositories are:
<ul>
	<li>Html-templates used to form parts of pages. Default values are loaded from IntrinsicVaues.java 
	  The templates can be totally or selectively replaced.</li>
	<li>Fields contain properties related to a java class (DTO) like label, type and max length. 
		This repository is populated Component.java using the associated DTO, including FieldDef annotations or not.
		It may be manually supplemented from Spring configuration for instance.
		Fields are constructed by FieldFactory.java.</li>
	<li>Texts are stowed away into a repository as an abstraction to ease translation and support for several languages.
		The texts populated from fields, code or from file. </li>
	<li>Selectors are used in drop down lists. A Selector is just a code snippet with a lookup key.</li>
	<li>Validators are used for extended field validation - more than data type validation.</li>
</ul>	
</p>
<p>
Component.java of which there are several related classes in the components package.
Component maintains a repository of items. Item is a Field in a setting - for example in a modification form.
Items are constructed by an ItemFactory, of which there are  several in the itemfactories package
A component knows how to render itself.
There are two main types of components: Form.java and Table.java.
<ul>
	<li>Table.java does rendering of a list of DTOs. Data aware action buttons may be added at each row.</li>
	<li>A form may take input from a user, and therefore knows how to validate and parse that input into the DTO.</li>
	<li>The behavior of a form is determined by FormParams upon construction.</li>
	<ul> 
	<li>Note the property Operation, to which you may associate ItemDefs.</li>
	<li>Note the property ActionUri, which would be the relative URL to the method that will receive the submitted data.</li>
	</ul>
</ul>	
</p>
  </div>
</body>
</html>
