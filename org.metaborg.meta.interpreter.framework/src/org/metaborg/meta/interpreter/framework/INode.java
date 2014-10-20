/**
 * 
 */
package org.metaborg.meta.interpreter.framework;


/**
 * @author vladvergu
 * 
 */
public interface INode {

	public void setSourceInfo(INodeSource src);

	public INodeSource getSourceInfo();

	public <T extends INode> INode setParent(T parent);

	public <T extends INode> T adoptChild(T child);
	
	public <K extends INode> INodeList<K> adoptChildren(INodeList<K> children);
	
	public <T extends INode> T replace(T newNode);

	public <T extends INode> T replaceChild(INode oldChild, T newChild);

	public void setReplacedBy(INode newNode);

	public INode replacement();

	public boolean replaced();

	public boolean isRoot();

	public INode getParent();

}