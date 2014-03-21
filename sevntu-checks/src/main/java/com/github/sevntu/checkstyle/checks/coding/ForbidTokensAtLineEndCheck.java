////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.github.sevntu.checkstyle.checks.coding;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Forbids specified tokens at line end.<br>
 * <b>Rationale:</b> this Check is needed to help user make code more readable<br>
 * Specified tokens can be set through the Check's property
 * <b>(forbidTokensAtLineEndRegex)</b><br>
 * Property is a regular expression, default value:
 * "([.,|+*&#8260;\-=]|(&&|\|\||=\=))"<br>
 * Forbidden tokens by default are: ".", ",", "|", "+", "*", "&",
 * "\", "-", "=", "&&", "||", "=="<br>
 * User also can forbid specified end of line by the addition of default regular
 * expression<br>
 * <b>Example:</b><br>
 * ([.,|+*&#8260;\-=]|(&&|\|\||=\=)|(SOME_SPECIFIED_SEQUENCE_AT_LINE_END))
 * <br>
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public class ForbidTokensAtLineEndCheck extends Check {

    private static final String DEFAULT_REGEX = "([.,|+*/\\-=]|(&&|\\|\\||=\\=))";
    public static final String MSG_KEY = "forbid.tokens.at.line.end";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {};
    }

    private Pattern pattern = Pattern.compile(DEFAULT_REGEX);

    /**
     * Sets the regular expression to forbid specified tokens at line end
     * 
     * @param forbidTokensAtLineEndRegex
     *            Regular expression
     */
    public void setForbidTokensAtLineEndRegex(String forbidTokensAtLineEndRegex) {
        this.pattern = Pattern.compile(forbidTokensAtLineEndRegex);
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        Map<Integer, DetailAST> lastTokensOfLineMap = fillLastTokensOfLineMap(rootAST);
        
        for (DetailAST mapValue : lastTokensOfLineMap.values()) {
            int lastTokenOfLineNo = mapValue.getLineNo();
            String lastTokenOfLine = mapValue.getText();
            if (pattern.matcher(lastTokenOfLine).matches()) {
                log(lastTokenOfLineNo, MSG_KEY, lastTokenOfLine);
            }
        }
    }

    /**
     * Puts the current node of a syntactical tree to the map of last tokens of
     * line if current node is a last token of a current line
     * 
     * @param rootAST
     *            Root node of a syntactical tree
     * @return Last Tokens Of Line Map
     */
    private Map<Integer, DetailAST> fillLastTokensOfLineMap(DetailAST rootAST) {
        DetailAST currentNode = rootAST;
        Map<Integer, DetailAST> lastTokensOfLineMap = new HashMap<Integer, DetailAST>();
        
        lastTokensOfLineMap.put(rootAST.getLineNo(), rootAST);
        
        while (currentNode != null) {
            int lineNo = currentNode.getLineNo();
            DetailAST lastTokenOfLine = lastTokensOfLineMap.get(lineNo);
            if (lastTokenOfLine == null
                    || lastTokenOfLine.getColumnNo() <= currentNode
                            .getColumnNo()) {
                lastTokensOfLineMap.put(lineNo, currentNode);
            }
            currentNode = getNextNode(currentNode);
        }
        
        return lastTokensOfLineMap;
    }

    /**
     * Gets the next node of a syntactical tree (child of a current node or
     * sibling of a current node, or sibling of a parent of a current node)
     * @param currentNode
     *            Current node in considering
     * @return Current node after bypassing
     */
    private DetailAST getNextNode(DetailAST currentNode) {
        DetailAST toVisit = currentNode.getFirstChild();
        while (currentNode != null && toVisit == null) {
            toVisit = currentNode.getNextSibling();
            if (toVisit == null) {
                currentNode = currentNode.getParent();
            }
        }
        currentNode = toVisit;
        return currentNode;
    }
}
