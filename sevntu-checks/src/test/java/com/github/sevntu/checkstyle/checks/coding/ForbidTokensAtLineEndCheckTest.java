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

import static com.github.sevntu.checkstyle.checks.coding.ForbidTokensAtLineEndCheck.MSG_KEY;

import org.junit.Test;

import com.github.sevntu.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

/**
 * @author <a href="mailto:nesterenko-aleksey@list.ru"> Aleksey Nesterenko</a>
 */
public class ForbidTokensAtLineEndCheckTest extends BaseCheckTestSupport {
    private final DefaultConfiguration checkConfig = createCheckConfig(ForbidTokensAtLineEndCheck.class);

    @Test
    public void testWithDefaultInput() throws Exception {
        String[] expected = { 
                "6: " + getCheckMessage(MSG_KEY, "."),
                "12: " + getCheckMessage(MSG_KEY, "||"),
                "15: " + getCheckMessage(MSG_KEY, "&&"),
                "18: " + getCheckMessage(MSG_KEY, ","),
                "19: " + getCheckMessage(MSG_KEY, ","),
                "20: " + getCheckMessage(MSG_KEY, ","),
                "24: " + getCheckMessage(MSG_KEY, "."),
                "25: " + getCheckMessage(MSG_KEY, "|"),
                "26: " + getCheckMessage(MSG_KEY, "+"),
                "27: " + getCheckMessage(MSG_KEY, "==")
                };
        verify(checkConfig, getPath("InputForbidTokensAtLineEndCheck.java"),
                expected);
    }

    @Test
    public void testWithDifferentInputRegex() throws Exception {
        String regex = "([.,|+*/\\-=]|(&&|\\|\\||=\\=)|(ARRAY_DECLARATOR))";
        checkConfig.addAttribute("forbidTokensAtLineEndRegex", regex);
        String[] expected = { 
                "6: " + getCheckMessage(MSG_KEY, "."),
                "12: " + getCheckMessage(MSG_KEY, "||"),
                "15: " + getCheckMessage(MSG_KEY, "&&"),
                "18: " + getCheckMessage(MSG_KEY, ","),
                "19: " + getCheckMessage(MSG_KEY, ","),
                "20: " + getCheckMessage(MSG_KEY, ","),
                "24: " + getCheckMessage(MSG_KEY, "."),
                "25: " + getCheckMessage(MSG_KEY, "|"),
                "26: " + getCheckMessage(MSG_KEY, "+"),
                "27: " + getCheckMessage(MSG_KEY, "=="),
                "32: " + getCheckMessage(MSG_KEY, "ARRAY_DECLARATOR") 
                };
        verify(checkConfig, getPath("InputForbidTokensAtLineEndCheck.java"),
                expected);
    }

}
