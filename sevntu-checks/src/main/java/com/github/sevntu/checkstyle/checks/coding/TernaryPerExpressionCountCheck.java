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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Restricts the number of ternary operators in expression to a specified limit.<br>
 * Limit is a Check's property (<b>maxTernaryPerExpressionCount)</b><br>
 * <b>Rationale:</b> this Check is needed to help user avoid unobvious behavior
 * of the code block.<br>
 * because of misunderstanding of ternary operators priority<br>
 * to help user follow any code convention if it is prohibited to use specified
 * number of ternary<br>
 * operators per expression<br>
 * <br>
 * Cases, which have unobvious results:<br>
 * 1. String concatenation with calling more than X ternary calculations<br>
 * Example:
 * <p>
 * <code>
 * String str = null;<br>
 * String x = str != null ? "A" : "B" + str == null ? "C" : "D";<br>
 * System.out.println(x);
 * </code>
 * </p>
 * Output is "D", but expected was "BC".<br>
 * 2. Nested mathematical calculations based on more than one ternary operator
 * (based on X ternary operators)<br>
 * Example:
 * <p>
 * <code>final int d = (a == b) ? (a == b) ? 5 : 6 : 6;</code>
 * </p>
 * Also there're some cases, when check highlights strange part of expression,
 * you can see the example in the bugreport <a
 * href="https://github.com/sevntu-checkstyle/sevntu.checkstyle/issues/184"
 * >issue</a><br>
 * 
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */

public class TernaryPerExpressionCountCheck extends Check {

    public static final String MSG_KEY = "expression.ternary";

    private static final int DEFAULT_MAX_TERNARY_PER_EXPRESSION_COUNT = 1;

    private int maxTernaryPerExpressionCount = DEFAULT_MAX_TERNARY_PER_EXPRESSION_COUNT;

    private boolean ternaryInBraces = true;

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.EXPR };
    }

    /**
     * Sets the maximum number of ternary operators, default value = 1
     * 
     * @param maxTernaryPerExpressionCount
     *            number of ternary operators per expression
     */
    public void setMaxTernaryPerExpressionCount(int maxTernaryPerExpressionCount) {
        this.maxTernaryPerExpressionCount = maxTernaryPerExpressionCount;
    }

    public void setTernaryInBraces(boolean ternaryInBraces) {
        this.ternaryInBraces = ternaryInBraces;
    }

    @Override
    public void visitToken(DetailAST expressionNode) {
        if (countQuestionTokens(expressionNode) > this.maxTernaryPerExpressionCount) {
            log(expressionNode, MSG_KEY, this.maxTernaryPerExpressionCount);
        }
    }

    /**
     * Gets the number of question tokens per expression node
     * 
     * @param expressionNode
     *            expression node
     * @return number of question tokens
     */
    private int countQuestionTokens(DetailAST expressionNode) {
        DetailAST currentNode = expressionNode;
        int questionTokensCount = 0;
        while (currentNode != null) {
            currentNode = getNextNode(expressionNode, currentNode);
            if (currentNode == null || currentNode.getType() == TokenTypes.EXPR
                    && questionTokensCount == 0) {
                break;
            }
            if (currentNode.getType() == TokenTypes.QUESTION) {
                if (checkBraces(currentNode)) {
                    questionTokensCount = 0;
                } else
                    questionTokensCount++;
            }
        }
        return questionTokensCount;
    }

    
    /**
     * Checks ternary operator for framing braces, which are explicitly setting priority level
     * @param currentNode
     * @return true or false
     */
    private boolean checkBraces(DetailAST currentNode) {
        if (ternaryInBraces) {
            return currentNode.getPreviousSibling() != null
                    && currentNode.getPreviousSibling().getType() == TokenTypes.LPAREN
                    && currentNode.getNextSibling() != null
                    && currentNode.getNextSibling().getType() == TokenTypes.RPAREN;
        } else {
            return false;
        }
    }

    /**
     * Gets the next node to consider
     * 
     * @param expressionNode
     *            globally considering expression node
     * @param currentNode
     *            current node from method countQuestionTokens
     * @return current node after bypassing
     */
    private DetailAST getNextNode(DetailAST expressionNode,
            DetailAST currentNode) {
        DetailAST toVisit = currentNode.getFirstChild();
        while (currentNode != null && toVisit == null
                && currentNode != expressionNode) {
            toVisit = currentNode.getNextSibling();
            if (toVisit == null) {
                currentNode = currentNode.getParent();
            }
        }
        currentNode = toVisit;
        return currentNode;
    }
}
