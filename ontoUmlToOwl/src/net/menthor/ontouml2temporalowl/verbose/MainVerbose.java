package net.menthor.ontouml2temporalowl.verbose;

import java.util.Calendar;

import org.semanticweb.owlapi.model.IRI;

public class MainVerbose
{
	private static String modelId;
	private static String modelName;
	
	public static String getModelId ()
	{
		return modelId;
	}
	
	public static void setModelId (String name)
	{
		//code fixed by Freddy Brasileiro (freddybrasileiro@gmail.com)
		//since the Editor Application call this the temporal transformation with full IRI in the name variable,
		//this transformation was resulting some mistakes
		//the modelName variable was resulting in something like "http://www.semanticweb.org/ontologies/2014/6/ontology"
		//and the modelId was resulting in something like "http://www.semanticweb.org/ontologies/2014/6/ontology/http://www.semanticweb.org/ontologies/2014/6/ontology.owl"
		//then, the resulting model was not parsing in Protege
		name = name.replace(" ", "_");
		
		IRI iri = IRI.create(name);
		modelName = iri.getFragment();
		
		int lastDot = modelName.lastIndexOf(".");
		if(lastDot >= 0){
			modelName = modelName.substring(0, lastDot);
		}		
		
		modelId = name;
		// FIXME: What if the model name has spaces in it? Replace " " by "_"?
		//modelName = name.replace(".owl", "");
		
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH) + 1;
//		modelId = "http://www.semanticweb.org/ontologies/" + year + "/" + month + "/" + name;
	}
	
	public static String header (String hashTagId)
	{
		return "    <!-- " + modelId + "#" + hashTagId + " -->\n\n"; 
	}
	
	public static String sectionBreak()
	{
		return "\n\n\n";
	}
	
	public static String commentBlock (String title)
	{
		return
	    "<!-- \n" +
	    "///////////////////////////////////////////////////////////////////////////////////////\n" +
	    "//\n" +
	    "// " + title + "\n" +
	    "//\n" +
	    "///////////////////////////////////////////////////////////////////////////////////////\n" +
	    "-->\n";
	}
	
	public static String initialVerbose()
	{
		return
			"<?xml version=\"1.0\"?>\n" +
			"\n" +
			"\n" +
			"<!DOCTYPE rdf:RDF [\n" +
			"    <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n" +
			"    <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n" +
			"    <!ENTITY owl2xml \"http://www.w3.org/2006/12/owl2-xml#\" >\n" +
			"    <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n" +
			"    <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n" +
			"    <!ENTITY " +  modelName + " \"" + modelId + "#\" >\n" +
			"]>\n" +
			"\n" +
			"\n" +
			"<rdf:RDF xmlns=\"" + modelId + "#\"\n" +
			"     xml:base=\"" + modelId + "\"\n" +
			"     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" +
			"     xmlns:owl2xml=\"http://www.w3.org/2006/12/owl2-xml#\"\n" +
			"     xmlns:" + modelName + "=\"" + modelId + "#\"\n" +
			"     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n" +
			"     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" +
			"     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n" +
			"    <owl:Ontology rdf:about=\"\"/>\n" +
			"\n" +
			"\n" +
			"\n";
	}
	
	public static String finalVerbose()
	{
		return
			"</rdf:RDF>\n\n" +
			"<!-- Generated by OntoUML2OWL Eclipse Plugin by NEMO (http://nemo.inf.ufes.br/) -->\n\n";
	}
}
