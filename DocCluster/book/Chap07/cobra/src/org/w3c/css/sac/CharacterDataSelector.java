/*
 * (c) COPYRIGHT 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * $Id: CharacterDataSelector.java,v 1.3 2006/06/18 04:15:16 xamjadmin Exp $
 */
package org.w3c.css.sac;

/**
 * @version $Revision: 1.3 $
 * @author  Philippe Le Hegaret
 * @see Selector#SAC_TEXT_NODE_SELECTOR
 * @see Selector#SAC_CDATA_SECTION_NODE_SELECTOR
 * @see Selector#SAC_COMMENT_NODE_SELECTOR
 */
public interface CharacterDataSelector extends SimpleSelector {

    /**
     * Returns the character data.
     */    
    public String getData();
}
