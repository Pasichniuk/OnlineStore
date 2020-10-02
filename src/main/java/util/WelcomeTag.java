package util;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

/**
 * Custom tag which prints text from it's body
 *
 * @author Vlad Pasichniuk
 *
 */

public class WelcomeTag extends SimpleTagSupport {

    private final StringWriter stringWriter = new StringWriter();

    public void doTag() throws JspException, IOException {
        getJspBody().invoke(stringWriter);
        getJspContext().getOut().println(stringWriter.toString());
    }
}
