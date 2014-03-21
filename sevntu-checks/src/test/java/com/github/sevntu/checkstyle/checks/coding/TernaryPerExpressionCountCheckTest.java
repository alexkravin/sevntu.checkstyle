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

import java.text.MessageFormat;

import org.junit.Test;

import com.github.sevntu.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

/**
 * @author <a href="mailto:nesterenko-aleksey@list.ru"> Aleksey Nesterenko</a>
 */
public class TernaryPerExpressionCountCheckTest extends BaseCheckTestSupport {

    private final DefaultConfiguration checkConfig = createCheckConfig(TernaryPerExpressionCountCheck.class);

    private final String msg = getCheckMessage(TernaryPerExpressionCountCheck.MSG_KEY);

    public String getMsg(int maxTernaryOperatorsCount) {
        return MessageFormat.format(msg, maxTernaryOperatorsCount);
    }

    @Test
    public void testWithDefaultTernaryPerExperssionCountValue()
            throws Exception {
        int maxTernaryOperatorsCount = 1;
        checkConfig.addAttribute("maxTernaryPerExpressionCount",
                Integer.toString(maxTernaryOperatorsCount));
        String[] expected = { "12:26: " + getMsg(maxTernaryOperatorsCount),
                "16:32: " + getMsg(maxTernaryOperatorsCount),
                "18:32: " + getMsg(maxTernaryOperatorsCount),
                "19:26: " + getMsg(maxTernaryOperatorsCount),
                "23:56: " + getMsg(maxTernaryOperatorsCount),
                "24:50: " + getMsg(maxTernaryOperatorsCount),
                "28:47: " + getMsg(maxTernaryOperatorsCount),
                "45:11: " + getMsg(maxTernaryOperatorsCount),
                "54:31: " + getMsg(maxTernaryOperatorsCount) };
        verify(checkConfig,
                getPath("InputTernaryPerExpressionCountCheck.java"), expected);
    }
    
    @Test
    public void testWithDifferentTernaryPerExperssionCountValue()
            throws Exception {

        int maxTernaryOperatorsCount = 2;

        checkConfig.addAttribute("maxTernaryPerExpressionCount",
                Integer.toString(maxTernaryOperatorsCount));

        String[] expected = { "12:26: " + getMsg(maxTernaryOperatorsCount) };

        verify(checkConfig,
                getPath("InputTernaryPerExpressionCountCheck.java"), expected);
    }

    @Test
    public void testWithBraces() throws Exception {
        boolean ternaryInBraces = false;
        int maxTernaryOperatorsCount = 1;
        checkConfig.addAttribute("ternaryInBraces",
                Boolean.toString(ternaryInBraces));

        String[] expected = { "12:26: " + getMsg(maxTernaryOperatorsCount),
                "16:32: " + getMsg(maxTernaryOperatorsCount),
                "18:32: " + getMsg(maxTernaryOperatorsCount),
                "19:26: " + getMsg(maxTernaryOperatorsCount),
                "23:56: " + getMsg(maxTernaryOperatorsCount),
                "24:50: " + getMsg(maxTernaryOperatorsCount),
                "28:47: " + getMsg(maxTernaryOperatorsCount),
                "29:18: " + getMsg(maxTernaryOperatorsCount),
                "33:26: " + getMsg(maxTernaryOperatorsCount),
                "34:26: " + getMsg(maxTernaryOperatorsCount),
                "35:44: " + getMsg(maxTernaryOperatorsCount),
                "36:11: " + getMsg(maxTernaryOperatorsCount),
                "45:11: " + getMsg(maxTernaryOperatorsCount),
                "47:11: " + getMsg(maxTernaryOperatorsCount),
                "54:31: " + getMsg(maxTernaryOperatorsCount),
                "68:79: " + getMsg(maxTernaryOperatorsCount),
                "72:19: " + getMsg(maxTernaryOperatorsCount) };

        verify(checkConfig,
                getPath("InputTernaryPerExpressionCountCheck.java"), expected);
    }

}
