package persistance;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.reflection.ClassModifier;
import utils.string.StringUtils;
import entity.Entity;

public class XMLComposer<T> {
	/**
	 * The attribute name of the component's type.
	 */
	public static final String ATTR_TYPE = "type";
	/**
	 * The attribute name of the component's name.
	 */
	public static final String ATTR_NAME = "name";
	/**
	 * The delimiter for the values.
	 */
	public static final String VALUES_DELIMITER = ",";
	
	/**
	 * The file that was read.
	 */
	protected File file;
	
	/**
	 * The XML document that was created by parsing the file.
	 */
	protected Document document;
	
	/**
	 * The nodes that were loaded after calling {@link loadNodes}.
	 */
	protected NodeList nodes;
	
	/**
	 * The loaded data.
	 */
	protected List<T> data;
	
	/**
	 * The generic class type.
	 */
	protected Class<T> genericType;
	
	/**
	 * Default constructor.
	 */
	public XMLComposer(Class<T> genericType) {
		this.genericType = genericType;
		data = new ArrayList<>();
	}
	
	/**
	 * Constructor.
	 * @param fileName The file name of the file to read
	 */
	public XMLComposer(String fileName, Class<T> genericType) {
		this(genericType);
		readFile(fileName);
	}
	
	/**
	 * Reads the file with the given name.
	 * @param fileName The file name (include path, if necessary)
	 * @return The composer, for chaining.
	 */
	public XMLComposer<T> readFile(String fileName){
		this.file = new File(fileName);
		parse();
		return this;
	}
	
	/**
	 * @return The root of the document
	 */
	public Element getRoot(){
		return document.getDocumentElement();
	}
	
	/**
	 * Composes the file according to the implementation of {@link compose}.
	 * @param fileName The file name of the file to read
	 * @return The composer, for chaining.
	 */
	public XMLComposer<T> compose(String fileName){
		readFile(fileName);
		return compose();
	}
	
	/**
	 * @return The {@see Document} that is used for reading the XML.
	 */
	public Document getDocument(){
		return document;
	}
	
	/**
	 * Parses the XML file that was set before.
	 * @return The composer, for chaining.
	 */
	private XMLComposer<T> parse(){
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
			document.getDocumentElement().normalize();
			loadNodes();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println("Some parsing error occured while parsing the XML file '" + file + "'.");
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * Loads the nodes with the composers prefix. For example: MyNodeNameComposer will load the nodes named "mynodename".
	 * Note the lower case!
	 * @return The {@see XMLComposer} itself, for chaining.
	 */
	public XMLComposer<T> loadNodes(){
		nodes = document.getElementsByTagName(genericType.getSimpleName().toLowerCase());
		return this;
	}
	
	/**
	 * @param nodes The nodes to get the element from
	 * @param index The index of the node
	 * @return The node, if the index was in bounds, NULL otherwise.
	 */
	public Node getNode(NodeList nodes, int index){
		if(index > -1 && index < nodes.getLength()) return nodes.item(index);
		return null;
	}

	/**
	 * @return The loaded data.
	 */
	public List<T> getData(){
		return this.data;
	}
	
	/**
	 * Clears the data that was loaded.
	 * @return The {@see XMLComposer} itself, for chaining.
	 */
	public XMLComposer<T> clear(){
		if(data != null) data.clear();
		return this;
	}
	
	/**
	 * The object that were created.
	 */
	private Map<T, String> createdObjects;
	
	/**
	 * Composes the read file.
	 */
	public XMLComposer<T> compose() {
		this.clear();
		createdObjects = loopOverNodes(nodes);
		for(Entry<T, String> entry : createdObjects.entrySet()){
			data.add((T) entry.getKey());
		}
		return this;
	}
	
	/**
	 * Loops over the given nodes. This function is recursive, thus it also creates all child elements.
	 * @param nodes The nodes to loop over
	 * @return The list of objects that was created.
	 */
	public Map<T, String> loopOverNodes(NodeList nodes){
		Map<T, String> createdObjects = new HashMap<>();
		Node currentNode = null, currentAttributeNode = null;
		NamedNodeMap currentAttributeMap = null;
		Map<T, String> createdChildren = new HashMap<>();
		T lastCreatedObject = null;
		String type = "";
		for(int i = 0; i < nodes.getLength(); i++){
			// Get current entity node and check for validity
			currentNode = getNode(nodes, i);
			if(!isValidNode(currentNode, Node.ELEMENT_NODE)) continue;
			
			// Create object
			type = getTypeFromNode(currentNode);
			if(type == null || type.isEmpty()) type = currentNode.getNodeName();
			lastCreatedObject = ClassModifier.createDefault(ClassModifier.getClassFromName(type));
			
			// Retrieve methods of current object
			ClassModifier.retrieveMethods();
			
			// Get attributes and check for validity
			if(!currentNode.hasAttributes()) continue;
			currentAttributeMap = currentNode.getAttributes();
						
			// Update values of the created object
			for (int k = 0; k < currentAttributeMap.getLength(); k++) {
				currentAttributeNode = currentAttributeMap.item(k);
				if(!isValidNode(currentAttributeNode, Node.ATTRIBUTE_NODE)) continue;
				
				// A list was found, retrieve this list
				if(currentAttributeNode.getNodeName().endsWith("-list")){
					List<Entity> entities = new XMLComposer<Entity>(Entity.class).compose(currentAttributeNode.getNodeValue()).getData();
					ClassModifier.setCurrentObject(lastCreatedObject);
					ClassModifier.retrieveMethods(ClassModifier.getClassFromName(type));
					
					for(Entity entity : entities){
						ClassModifier.updateComponentValues(entity, currentAttributeNode.getNodeName().replace("-list", ""));
					}
				}else{
					// Update values
					ClassModifier.updateComponentValues(currentAttributeNode.getNodeValue().split(VALUES_DELIMITER), StringUtils.capitalize(currentAttributeNode.getNodeName()));
				}
			}
			
			if(currentNode.hasChildNodes()){
				// Loop over all children
				createdChildren = loopOverNodes(currentNode.getChildNodes());
				ClassModifier.setCurrentObject(lastCreatedObject);
				ClassModifier.retrieveMethods(ClassModifier.getClassFromName(type));
				
				// Add children to parent
				for(Entry<T, String> entry : createdChildren.entrySet()){
					ClassModifier.updateComponentValues(entry.getKey(), entry.getValue());
				}
			}
			
			// Add to list
			createdObjects.put(lastCreatedObject, currentNode.getNodeName());
		}
		return createdObjects;
	}
	
	/**
	 * @param node The node to get the type attribute from
	 * @return The value of the type attribute.
	 */
	public String getTypeFromNode(Node node){
		return (((Element)node).getAttribute(ATTR_TYPE));
	}
	
	/**
	 * @param node The {@see Node} to check
	 * @return Whether or not the given {@see Node} is valid.
	 */
	public boolean isValidNode(Node node, short type){
		return node != null && node.getNodeType() == type;
	}
}