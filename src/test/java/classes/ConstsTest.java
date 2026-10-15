package classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/** Keeps the User-Agent in step with the version in pom.xml. */
class ConstsTest {

    @Test
    void userAgentMatchesThePomVersion() throws Exception {
        File pom = new File(System.getProperty("basedir", "."), "pom.xml");
        Element project = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pom).getDocumentElement();
        String version = null;
        for (Node n = project.getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n.getNodeType() == Node.ELEMENT_NODE && "version".equals(n.getNodeName())) {
                version = n.getTextContent().trim();
            }
        }
        assertEquals("java-sdk/" + version, Consts.USER_AGENT);
    }
}
