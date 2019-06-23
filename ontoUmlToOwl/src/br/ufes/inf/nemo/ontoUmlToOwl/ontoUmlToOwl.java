package br.ufes.inf.nemo.ontoUmlToOwl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

import RefOntoUML.parser.OntoUMLParser;
import net.menthor.ontouml2simpleowl.*;
import net.menthor.common.settings.owl.OwlOptions;
import net.menthor.common.settings.owl.OWL2Approach;
import net.menthor.common.settings.owl.OWL2Destination;
import net.menthor.ontouml2temporalowl.auxiliary.OWLMappingTypes;
import net.menthor.ontouml2temporalowl.auxiliary.OWLStructure;
import net.menthor.ontouml2temporalowl.tree.TreeProcessor;
import net.menthor.ontouml2temporalowl.verbose.FileManager;
import net.menthor.ootos.OntoUML2OWL;

public class ontoUmlToOwl {
	
	public static void main(String[] args) {
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(args[0])); 
			String line;
			Properties prop = new Properties();
			InputStream input = null;
			String oclRules = "";
			input = new FileInputStream("config.properties");
			
			// load a properties file
			prop.load(input);

			String model = prop.getProperty("model");
			String savePath = prop.getProperty("savepath");
			String iri = prop.getProperty("iri");
			String approach = prop.getProperty("approach").toUpperCase();
			String options = prop.getProperty("options").toUpperCase();
			String reasoner = prop.getProperty("reasoner").toUpperCase();
			
			while ((line = br.readLine()) != null) {
				 oclRules += line;
			}
			br.close();
			
			OntoUMLParser parser = new OntoUMLParser(model);
			RefOntoUML.Package parsedModel = parser.getModel();
			String owlOutput = "";
			
			if(approach.equals("SIMPLE")) 
			{
			    owlOutput = OntoUML2SimpleOWL.Transformation(parsedModel, iri);
			}else if(approach.equals("OOTOS")) 
			{
				OntoUML2OWL ontoUML2OWL = new OntoUML2OWL();
				OwlSettingsMap map = new OwlSettingsMap();
				OwlOptions trOpt = map.getOwlOptions(parser,OWL2Approach.OOTOS, OWL2Destination.FILE,savePath,options,iri,reasoner);
				owlOutput = ontoUML2OWL.Transformation(parser, oclRules, trOpt, iri);
		          }else if(approach.equals("REIFICATION") || approach.equals("WORM_VIEW_A0") ||
		        		  approach.equals("WORM_VIEW_A1") ||approach.equals("WORM_VIEW_A2")) 
			      {
		        	    OWLMappingTypes mtypes = OWLMappingTypes.REIFICATION;
		    			if(approach.equals("WORM_VIEW_A0")) mtypes = OWLMappingTypes.WORM_VIEW_A0; 
		    			if(approach.equals("WORM_VIEW_A1")) mtypes = OWLMappingTypes.WORM_VIEW_A1;
		    			if(approach.equals("WORM_VIEW_A2")) mtypes = OWLMappingTypes.WORM_VIEW_A2;
		    			TreeProcessor tp = new TreeProcessor(parsedModel);
		    			OWLStructure owl = new OWLStructure(mtypes, tp);
		    			owl.map(tp);
		    			owlOutput = owl.verbose(iri);       
			            }else 
			            {
				         System.out.println("Approach not found");
				         return;
			            }
			
			if(!owlOutput.isEmpty()) 
			{
			   String owlFileName = savePath;							
			   FileManager fileManager = new FileManager(owlFileName);
			   fileManager.write(owlOutput);
			   fileManager.done();
			   System.out.println("Arquivo criado com sucesso");
			}else 
			{
				System.out.println("Arquivo não pode ser vazio");
			}
		
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
			
			
	}
	

}
